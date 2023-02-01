package com.adoise.library.repository;

import java.util.Date;

import com.adoise.library.model.JwtModel;

public interface TokenRepository {

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
    void delete(String key);

    /**
     * Get token.
     *
     * @param key
     * @return
     */
    Object get(String key);
}