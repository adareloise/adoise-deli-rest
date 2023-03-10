package com.adoise.core.jwt.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.adoise.library.model.JwtModel;
import com.adoise.library.repository.TokenRepository;

import java.util.Date;

@Repository
public class TokenRepositoryImpl implements TokenRepository {

    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public TokenRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void setSecretKey(String key, JwtModel model) {
        this.redisTemplate.opsForValue().set(key, model);
    }

    @Override
    public Boolean setKeyExpiration(String key, Date exp) {
        return this.redisTemplate.expireAt(key, exp);
    }

    @Override
    public void delete(String key) {
        this.redisTemplate.delete(key);
    }

    @Override
    public Object get(String key) {
        return this.redisTemplate.opsForValue().get(key);
    }
}