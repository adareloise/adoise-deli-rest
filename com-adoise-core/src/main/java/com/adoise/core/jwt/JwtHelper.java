package com.adoise.core.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.adoise.library.constant.JwtConstant;
import com.adoise.library.model.JwtModel;

import javax.crypto.SecretKey;

import java.util.Calendar;
import java.util.Date;

@Component
public class JwtHelper {
	
    @Value("${Las-delicias-webapp}")
    private String appName;

    /**
     * Generate access token.
     *
     * @param username
     * @param secretKey
     * @return
     */
    public JwtModel generateAccessToken(String username, SecretKey secretKey) {
        if (username == null) {
            throw new NullPointerException("Error generating access token, username is null");
        }
        // Expiration.
        Date expiration = generateTokenExp(JwtConstant.ACCESS_TOKEN_EXPIRATION);
        // Issue date.
        Date current = new Date();
        // Generate token.
        String token = Jwts.builder()
                .setIssuer(appName)
                .setSubject(username)
                .setIssuedAt(current)
                .setExpiration(expiration)
                .signWith(secretKey)
                .compact();
        return new JwtModel(token, appName, username,
                secretKey, current, expiration);
    }

    /**
     * Validate token.
     *
     * @param key
     * @param model
     * @return
     */
    public Claims validateToken(SecretKey key, JwtModel model) {
        Claims claims = Jwts.parser()
                .requireIssuer(model.getIssuer())
                .requireSubject(model.getSubject())
                .requireIssuedAt(model.getIssueDate())
                .requireExpiration(model.getExpDate())
                .setSigningKey(key)
                .parseClaimsJws(model.getToken())
                .getBody();
        return claims;
    }

    /**
     * Expiration.
     *
     * @param timeExpired
     * @return
     */
    private Date generateTokenExp(Integer timeExpired) {
        Date current = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(current);
        calendar.add(Calendar.MINUTE, timeExpired);
        return calendar.getTime();
    }
}
