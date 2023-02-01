package com.adoise.library.entity;

import com.adoise.library.base.AbstractData;
import com.adoise.library.base.ORMEntity;
import com.adoise.library.model.dto.RoleDto;
import com.adoise.library.model.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_info")
@JsonIgnoreProperties({"password"}) // Ignore password field.
public class UserInfo implements ORMEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Email(message = "Email must be valid")
    private String email;

    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private boolean enabled;
    
    @OneToOne(fetch = FetchType.LAZY,
            cascade =  CascadeType.ALL)
	@JoinColumn(name = "user_id")
    private Client client;

    @ManyToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "user_roles",
            // The owning entity.
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"
            ),
            // Non owning entity.
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"
            )
    )
    
    @JsonIgnoreProperties({"users", "id"}) // This ignore the fields in Role entity.
    private List<Role> roles = new ArrayList<>();

    public UserInfo() {
    }

    public UserInfo(String email, String password, String firstName, String lastName, boolean enabled) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
    }

    public UserInfo(String email, String password, String firstName, String lastName, boolean enabled, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.roles = roles;
    }
    
    public UserInfo(String email, String password, String firstName, String lastName, boolean enabled, Client client, List<Role> roles) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.enabled = enabled;
        this.client = client;
        this.roles = roles;
    }


    public void setRoles(List<Role> roles) {
        for (Role role : roles) {
            setRole(role);
        }
    }

    public void removeRoles(List<Role> roles) {
        for (Role role : roles) {
            removeRole(role);
        }
    }

    /**
     * Add user and also to child entity role.
     *
     * @param role
     */
    public void setRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    /**
     * Remove user and also to child entity role.
     *
     * @param role
     */
    @SuppressWarnings("unlikely-arg-type")
	public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(role);
    }
    
 
	@Override
	public UserDto toDto() { 
		UserDto dto = new UserDto();
		
		dto.setId(this.getId());
		dto.setEmail(this.getEmail());
		dto.setFirstName(this.getFirstName());
		dto.setLastName(this.getLastName());
		dto.setRoleDto((RoleDto) this.toDtoList (RoleDto.class, this.getRoles()));
		
		return dto;
	}

	public static UserInfo fromDto(UserDto dto) {
	    
    	UserInfo entity = new UserInfo();	
    	entity.setEmail(dto.getEmail());
    	entity.setFirstName(dto.getFirstName());
    	entity.setLastName(dto.getLastName());
    
    	return entity;
    }
    
}
