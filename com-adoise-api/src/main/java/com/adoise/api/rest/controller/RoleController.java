package com.adoise.api.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adoise.api.rest.model.ApiResponse;
import com.adoise.library.entity.UserInfo;
import com.adoise.library.model.dto.RoleDto;
import com.adoise.library.service.UserService;

import java.util.Arrays;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class RoleController {

    private final UserService userService;

    @Autowired
    public RoleController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Add user role.
     *
     * @param userId
     * @param roleDto
     * @return
     */
    @PostMapping(
            value = "/users/{userId}/roles",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ApiResponse<UserInfo> addUserRole(@PathVariable(name = "userId") Long userId,
                                             @RequestBody RoleDto roleDto) {
        UserInfo info = this.userService.addUserRole(userId, roleDto);
        return new ApiResponse<>(HttpStatus.CREATED.value(),
                HttpStatus.CREATED,
                Arrays.asList(info));
    }
}