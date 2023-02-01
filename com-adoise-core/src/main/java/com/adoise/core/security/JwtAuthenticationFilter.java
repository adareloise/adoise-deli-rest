package com.adoise.core.security;

import com.adoise.core.jwt.JwtHelper;
import com.adoise.core.jwt.util.JwtUtil;
import com.adoise.library.constant.JwtConstant;
import com.adoise.library.dto.AuthRequest;
import com.adoise.library.model.JwtModel;
import com.adoise.library.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

/**
 * Customize authentication filter so we don't need to add intercept url for login in http security.
 * We can implement this by adding it in {@link WebSecurityConfig} http security filter.
 */

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(getClass());

    private AuthenticationManager authManager;
    private TokenService tokenService;
    private JwtHelper jwtHelper;
    private JwtUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authManager, TokenService tokenService,
                                   JwtHelper jwtHelper, JwtUtil jwtUtil) {
        this.authManager = authManager;
        this.tokenService = tokenService;
        this.jwtHelper = jwtHelper;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Map dto value.
            AuthRequest req = this.getCredentials(request);
            // Authenticate user.
            return this.authManager.authenticate(new UsernamePasswordAuthenticationToken(
                    req.getEmail(),
                    req.getPassword()
            ));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication auth) {
        try {
            SecurityContextHolder.getContext().setAuthentication(auth);
            // Generate secret key.
            SecretKey secretKey = this.jwtUtil.generateKey();
            // Create token.
            JwtModel model = this.jwtHelper.generateAccessToken(((User) auth.getPrincipal()).getUsername(), secretKey);
            // Set token.
            this.tokenService.setSecretKey(model.getToken(), model);
            // Set key expiration on redis.
            this.tokenService.setKeyExpiration(model.getToken(), model.getExpDate());
            // Add token to authorization header.
            response.addHeader(JwtConstant.AUTHORIZATION_HEADER_STRING, JwtConstant.TOKEN_BEARER_PREFIX + model.getToken());
            // Add body authorization response.
    		Map<String, Object> body = new HashMap<String, Object>();
    		body.put("token", model.getToken());
    		body.put("user", (User) auth.getPrincipal());
    		body.put("mensaje", String.format("Hola %s, has iniciado sesión con éxito!", ((User)auth.getPrincipal()).getUsername()) );

    		response.getWriter().write(new ObjectMapper().writeValueAsString(body));
    		response.setStatus(200);
    		response.setContentType("application/json");
    		
    		
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AuthRequest getCredentials(HttpServletRequest request) {
        // Map dto value.
        AuthRequest auth = null;
        try {
            auth = new ObjectMapper().readValue(request.getInputStream(), AuthRequest.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return auth;
    }
}
