package com.adoise.library.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import com.adoise.library.enums.PrivilegeEnum;
import com.adoise.library.enums.RoleEnum;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RoleDto {

    private RoleEnum roleEnum;
    private List<PrivilegeEnum> privilegeEnums;

}
