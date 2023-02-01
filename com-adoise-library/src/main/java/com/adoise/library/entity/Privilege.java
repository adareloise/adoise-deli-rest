package com.adoise.library.entity;


import lombok.Getter;
import lombok.Setter;

import com.adoise.library.enums.PrivilegeEnum;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Privilege {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(value = EnumType.STRING)
    private PrivilegeEnum type;

    @ManyToMany(mappedBy = "privileges")
    @JsonIgnoreProperties("privileges") // Ignore the fields in Role entity.
    private List<Role> roles = new ArrayList<>();
    
    public Privilege() {
    }

    public Privilege(PrivilegeEnum type) {
        this.type = type;
    }

    public Privilege(PrivilegeEnum type, List<Role> roles) {
        this.type = type;
        this.roles = roles;
    }
}