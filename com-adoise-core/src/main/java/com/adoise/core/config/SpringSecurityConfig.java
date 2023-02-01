package com.adoise.core.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.adoise.core.handler.HttpLogoutHandler;
import com.adoise.core.jwt.JwtHelper;
import com.adoise.core.jwt.util.JwtUtil;
import com.adoise.core.security.JwtAuthenticationFilter;
import com.adoise.core.security.JwtAuthorizationFilter;
import com.adoise.library.service.TokenService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	

    private final UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder passwordEncoder;

    private final TokenService tokenService;

    private final JwtHelper jwtHelper;

    private final JwtUtil jwtUtil;

    private final HttpLogoutHandler httpLogoutHandler;

    @Autowired
    public SpringSecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder, TokenService tokenService,
                             JwtHelper jwtHelper, JwtUtil jwtUtil, HttpLogoutHandler httpLogoutHandler) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.jwtHelper = jwtHelper;
        this.jwtUtil = jwtUtil;
        this.httpLogoutHandler = httpLogoutHandler;
    }

	
	public static final String SIGN_UP_URL = "/api/auth/signin";
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
			
		http
			.cors().and().csrf().disable()
			.headers().frameOptions().disable()
	        .and()
			.authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers(HttpMethod.POST, "/users").permitAll()
            .antMatchers(HttpMethod.POST, "/users/{userId}/roles").permitAll()
			.antMatchers(HttpMethod.GET, "/", "/index", "/uploads/**").permitAll()
			.antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
			.anyRequest().authenticated()
			.and()
            .logout()
            .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            .logoutSuccessHandler(httpLogoutHandler)
            .and()
            // Authentication filter, this will intercept request path for login ("/login").
            .addFilter(new JwtAuthenticationFilter(authenticationManager(), tokenService, jwtHelper, jwtUtil))
            // Authorization filter to check jwt validity.
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), userDetailsService, tokenService, jwtHelper, jwtUtil))
            // This disables session creation on Spring Security
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	            .ignoring()
	            .antMatchers(
	            		"/resources/**", 
	            		"/static/**", 
	            		"/css/**", 
	            		"/js/**", 
	            		"/img/**", 
	            		"/icon/**", 
	            		"/swagger-ui/**", 
	            		"/v3/api-docs/**",
	            		"/swagger-resources/**",
	            		"/webjars/**");
	}
	
	/**
     * Use the {@link UserDetailsService} to verify user.
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }
	
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // Permit dto from different origin.
        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();
        source.registerCorsConfiguration("/**", corsConfiguration);
        
        return source;
    }
    
}
