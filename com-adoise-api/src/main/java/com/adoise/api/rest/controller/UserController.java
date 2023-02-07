package com.adoise.api.rest.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.adoise.api.rest.model.ApiResponse;
import com.adoise.library.core.rest.patch.Patch;
import com.adoise.library.entity.UserInfo;
import com.adoise.library.exception.global.DataException;
import com.adoise.library.exception.global.EmailExistsException;
import com.adoise.library.model.dto.UserDto;
import com.adoise.library.service.UserService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

@RestController
public class UserController {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserService userService;

    @Autowired
    public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Get list of user.
     *
     * @return
     */
    @GetMapping(
            value = "/users",
            produces = APPLICATION_JSON_VALUE
    )
    public List<UserInfo> getAllUsers() {
        return this.userService.getAllUser();
    }

    /**
     * Add new user.
     *
     * @param dto
     * @return
     */
    @PostMapping(
            value = "/users",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    public ApiResponse<UserInfo> addUser(@RequestBody UserDto dto) throws EmailExistsException {
        boolean isEmailExist = this.userService.isEmailAlreadyExist(dto.getEmail());
        // Check if email exist.
        if (isEmailExist) {
            throw new EmailExistsException("Email " + dto.getEmail() + " already exists");
        }
        UserInfo user = new UserInfo();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPassword(this.passwordEncoder.encode(dto.getPassword()));
        user.setEnabled(true);
        user.setEmail(dto.getEmail());
        // Save user.
        UserInfo res = this.userService.saveUserAndFlush(user);
        return new ApiResponse<>(HttpStatus.CREATED.value(),
                HttpStatus.CREATED,
                Arrays.asList(res));
    }

    /**
     * Get user by id.
     *
     * @param id
     * @return
     */
    @GetMapping(
            value = "/users/{id}",
            produces = APPLICATION_JSON_VALUE
    )
    @PreAuthorize(
            "hasAuthority('READ')"
    )
    public UserInfo getUserById(@PathVariable int id) {
        return this.userService.getUserById(Long.valueOf(id));
    }

    /**
     * Delete user by id.
     *
     * @param id
     * @return
     */
    @DeleteMapping(
            value = "/users/{id}",
            produces = TEXT_PLAIN_VALUE
    )
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') AND hasAuthority('WRITE')"
    )
    public ResponseEntity<?> deleteUserById(@PathVariable int id) {
        this.userService.removeUserById((long) id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Update user by id.
     *
     * @param id
     * @param dto
     * @return
     */
    @PutMapping(
            value = "/users/{id}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') AND hasAuthority('WRITE')"
    )
    public ApiResponse<UserInfo> updateUserById(@RequestBody UserDto dto) {
        UserInfo res = this.userService.saveDto(dto);
        return new ApiResponse<>(HttpStatus.CREATED.value(),
                HttpStatus.CREATED,
                Arrays.asList(res));
    }

    /**
     * Patch user.
     *
     * @param id
     * @param patch
     * @return
     */
    @PatchMapping(
            value = "/users/{id}",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE
    )
    @PreAuthorize(
            "hasRole('ROLE_ADMIN') AND hasAuthority('WRITE')"
    )
    public ApiResponse<UserInfo> patchUser(@PathVariable int id, @RequestBody Patch patch) {
        UserInfo res = this.userService.patchUser(Long.valueOf(id), patch);
        return new ApiResponse<>(HttpStatus.CREATED.value(),
                HttpStatus.CREATED,
                Arrays.asList(res));
    }

    /**
     * Get current logged user.
     *
     * @return
     */
    @GetMapping(
            value = "/users/current",
            produces = APPLICATION_JSON_VALUE
    )
    public User getCurrentLoggedUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }  
    
    /**
     * Update availability plate by id.
     * @param id
     * @return
     */
	@PostMapping("/users/state")
	public ResponseEntity<?> updateState(@RequestBody UserDto dto) throws DataException {
	
		Map<String, Object> response = new HashMap<>();
		
		try {
			this.userService.ChangeAvailability(dto);
			
			response.put("msg", "Estatus cambiado");
			
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
			
		} catch (DataException e) {
			response.put("msg", "Error al camnbiar el estatus");
			response.put("error", e);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}