package com.adoise.library.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import com.adoise.library.base.AbstractData;
import com.adoise.library.base.ORMEntity;
import com.adoise.library.enums.RoleEnum;
import com.adoise.library.model.dto.RoleDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Role implements ORMEntity {

	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private RoleEnum type;

    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties("roles") // Ignore the fields in UserInfo entity.
    private List<UserInfo> users = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "roles_privileges",
            // The owning entity.
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"
            ),
            // The non owning entity.
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"
            )
    )
    @JsonIgnoreProperties({"roles", "id"}) // This ignore the fields in the Privilege entity.
    private List<Privilege> privileges = new ArrayList<>();


    public void setPrivileges(List<Privilege> privileges) {
        for (Privilege privilege : privileges) {
            setPrivilege(privilege);
        }
    }

    public void removePrivileges(List<Privilege> privileges) {
        for (Privilege privilege : privileges) {
            removePrivilege(privilege);
        }
    }

    /**
     * Add role and also to child entity privilege.
     *
     * @param privilege
     */
    public void setPrivilege(Privilege privilege) {
        this.privileges.add(privilege);
        privilege.getRoles().add(this);
    }

    /**
     * Remove role and also to child entity privilege.
     *
     * @param privilege
     */
    public void removePrivilege(Privilege privilege) {
        this.privileges.remove(privilege);
        privilege.getRoles().remove(this);
    }

	@Override
	public RoleDto toDto() {
		// TODO Auto-generated method stub
		return null;
	}
}