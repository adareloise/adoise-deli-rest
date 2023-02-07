package com.adoise.core.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adoise.library.constant.Messages;
import com.adoise.library.core.rest.patch.Patch;
import com.adoise.library.core.rest.patch.PatchEnum;
import com.adoise.library.entity.Privilege;
import com.adoise.library.entity.Role;
import com.adoise.library.entity.UserInfo;
import com.adoise.library.enums.PrivilegeEnum;
import com.adoise.library.exception.global.EmailExistsException;
import com.adoise.library.exception.global.EmailNotFoundException;
import com.adoise.library.exception.global.PatchOperationNotSupported;
import com.adoise.library.exception.global.UserNotFoundException;
import com.adoise.library.model.Mail;
import com.adoise.library.model.dto.RoleDto;
import com.adoise.library.model.dto.UserDto;

import com.adoise.library.repository.UserRepository;
import com.adoise.library.service.MailService;
import com.adoise.library.service.UserService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.mail.MessagingException;

@Service
@Transactional
public class UserServiceImpl implements UserDetailsService, UserService {
    
	private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
	private final MailService mailService;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        UserInfo user = this.userRepository.findByEmail(email);
        // Throw exception if user not found.
        if (user == null) {
            throw new EmailNotFoundException(Messages.User.EMAIL_NOT_FOUND);
        }
        return new User(user.getEmail(), user.getPassword(), user.isEnabled(),
                true, true, true, getAuthorities(user.getRoles()));
    }

    /**
     * Get user privileges and roles.
     *
     * @param roles
     * @return
     */
    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getType().name()));
            role.getPrivileges()
                    .stream()
                    .map(p -> new SimpleGrantedAuthority(p.getType().name()))
                    .forEach(authorities::add);
        }
        return authorities;
    }

    @Override
    public UserInfo saveUser(UserInfo user) {
        return this.userRepository.save(user);
    }

    @Override
    public UserInfo saveUserAndFlush(UserInfo user) {
        return this.userRepository.saveAndFlush(user);
    }


    @Override
    public UserInfo saveDto(UserDto userDto) {
    	
        boolean isEmailExist = this.isEmailAlreadyExist(userDto.getEmail());
        // Check if email exist.
        if (isEmailExist) {
            throw new EmailExistsException(Messages.User.EMAIL_FOUND);
        }
        
        UserInfo user = new UserInfo();
        Mail contact = new Mail();
        
        contact.setEmail(userDto.getEmail());
        contact.setName(userDto.getFirstName());
        contact.setEmailCc("dutreras369@gmail.com");
                   
        if (userDto.getId() != null) {
            user = this.getUserById(userDto.getId());
        }
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPassword(this.createPasswordAndSend(contact));
        
        user = this.userRepository.save(user);
        
        user = this.addUserRole(user.getId(), userDto.getRoleDto());
        
        return user;
    }
    
    @Override
    public UserInfo saveDtoClient(UserDto userDto) {
    	
        boolean isEmailExist = this.isEmailAlreadyExist(userDto.getEmail());
        
        UserInfo user = new UserInfo();

        // Check if email exist.
        if (isEmailExist) {
            user = this.getUserByEmail(userDto.getEmail());
        }
        
        Mail contact = new Mail();
        
        contact.setEmail(userDto.getEmail());
        contact.setName(userDto.getFirstName());
        contact.setEmailCc("dutreras369@gmail.com");
                   
        if (userDto.getId() != null) {
            user = this.getUserById(userDto.getId());
        }
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        
        if(user.getPassword().isEmpty()) {
        	user.setPassword(this.createPasswordAndSend(contact));
        }
        		
        user.setPassword(userDto.getPassword());
        
        user = this.userRepository.save(user);
        
        user = this.addUserRole(user.getId(), userDto.getRoleDto());
        
        return user;
    }

    @Override
    public void removeUserById(Long id) throws UserNotFoundException {
        UserInfo user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(Messages.User.USER_NOT_FOUND);
        }
        this.userRepository.deleteById(id);
    }

    @Override
    public List<UserInfo> getAllUser() {
        return this.userRepository.findAll();
    }

    @Override
    public UserInfo getUserByEmail(String email) throws UserNotFoundException {
        UserInfo user = this.userRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return user;
    }

    @Override
    public UserInfo getUserById(Long id) throws UserNotFoundException {
        UserInfo user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(Messages.User.USER_NOT_FOUND);
        }
        return user;
    }

    @Override
    public UserInfo patchUser(Long id, Patch patch) throws PatchOperationNotSupported, UserNotFoundException {
        if (!patch.getPatchEnum().equals(PatchEnum.REPLACE)) {
            throw new PatchOperationNotSupported("Patch operation not supported");
        }
        UserInfo user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(Messages.User.USER_NOT_FOUND);
        }
        if (patch.getField().equals("email")) {
            user.setEmail(patch.getValue());
        } else if (patch.getField().equals("password")) {
            user.setPassword(patch.getValue());
        } else if (patch.getField().equals("firstName")) {
            user.setLastName(patch.getValue());
        } else if (patch.getField().equals("lastName")) {
            user.setLastName(patch.getValue());
        } else if (patch.getField().equals("enabled")) {
            user.setEnabled(Boolean.parseBoolean(patch.getValue()));
        }
        return this.userRepository.saveAndFlush(user);
    }

    @Override
    public boolean isEmailAlreadyExist(String email) {
        UserInfo user = this.userRepository.findByEmail(email);
        if (user == null) {
            return false;
        }
        return true;
    }

    @Override
    public UserInfo addUserRole(Long id, RoleDto dto) throws UserNotFoundException {
        UserInfo user = this.userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException(Messages.User.USER_NOT_FOUND);
        }
        // Add privileges.
        List<Privilege> privileges = new ArrayList<>();
        for (PrivilegeEnum p : dto.getPrivilegeEnums()) {
            privileges.add(new Privilege(p));
        }
        // Set role.
        Role role = new Role();
        role.setType(dto.getRoleEnum());
        role.setPrivileges(privileges);
        // Update user role.
        user.setRole(role);
        // Update user, cascade is added in role and user to affect child entity.
        return this.userRepository.saveAndFlush(user);
    }
    
	@Override
	public String createPasswordAndSend(Mail contact) {
		
		String password = "";
		
		password  = getRandomPassword();
		
		String passwordEncoded =  this.passwordEncoder.encode(password);
		
		try {
			this.mailService.sendMail("adareloise.io@gmail.com", contact.getEmail(), contact.getEmailCc(), "Registro en Delicias la Doña.", 
					"<h3> Bienvenido, " + contact.getName() + "</h3>" + "<p> Hemos recibido tu registro <br><br>"
					+ "Activa tu cuenta en el siguien link con tu email y este password temporal. <br><br>" 
					+ "<span style=\"font-family: monospace; \" >" + "Password: "+ password +"</span> <br><br>" 
					+ " Ingresa en el <a href=\"deliciasladoña.cl/ingresar\">"+"Link </a>"
					+ " <br><br> Gracias por registrarte </p> <br>");
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		return passwordEncoded;
	}
	
	public String getRandomPassword() {
		final String NUMEROS = "0123456789";
		final String MINUSCULAS = "abcdefghijklmnopqrstuvwxyz";
		
		String pswd = "";
		String key = NUMEROS + MINUSCULAS;

		for (int i = 0; i < 8; i++) {
			pswd += (key.charAt((int) (Math.random() * key.length())));
		}
		return pswd;
	}
	
	@Override
	public void ChangeAvailability(UserDto dto) {
		UserInfo user = this.getUserByEmail(dto.getEmail());
		
		if(user.isEnabled() == true) {
			user.setEnabled(false);
		}
		else {
			user.setEnabled(true);
		}
	}
}