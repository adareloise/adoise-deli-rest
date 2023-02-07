package com.adoise.library.service;

import java.util.List;

import com.adoise.library.core.rest.patch.Patch;
import com.adoise.library.entity.UserInfo;
import com.adoise.library.model.Mail;
import com.adoise.library.model.dto.RoleDto;
import com.adoise.library.model.dto.UserDto;

public interface UserService {

    UserInfo saveUser(UserInfo user);

    UserInfo saveUserAndFlush(UserInfo user);
    
    UserInfo saveDto(UserDto dto);

    UserInfo saveDtoClient(UserDto dto);

    void removeUserById(Long id);

    List<UserInfo> getAllUser();

    UserInfo getUserByEmail(String email);

    UserInfo getUserById(Long id);

    UserInfo patchUser(Long id, Patch patch);

    boolean isEmailAlreadyExist(String email);

    UserInfo addUserRole(Long id, RoleDto dto);
    
	public String createPasswordAndSend(Mail contact);
	
	public void ChangeAvailability(UserDto dto);

}