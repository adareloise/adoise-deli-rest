package com.adoise.library.service;

import java.util.Date;

import com.adoise.library.model.JwtModel;

public interface TokenService {

    /**
     * Save token key.
     *
     * @param key
     * @param model
     */
    void setSecretKey(String key, JwtModel model);

    /**
     * Set key expiration.
     *
     * @param key
     * @param exp
     */
    Boolean setKeyExpiration(String key, Date exp);

    /**
     * Remove token.
     *
     * @param key
     */
    void deleteToken(String key);

    /**
     * Get token secret key.
     *
     * @param key
     * @return
     */
    Object getSecretKey(String key);
}
