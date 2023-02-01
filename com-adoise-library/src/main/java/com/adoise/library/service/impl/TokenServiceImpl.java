package com.adoise.library.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.adoise.library.exception.global.TokenNotFoundException;
import com.adoise.library.model.JwtModel;
import com.adoise.library.repository.TokenRepository;
import com.adoise.library.service.TokenService;

import java.util.Date;

@Service
@Transactional
public class TokenServiceImpl implements TokenService {

    private final TokenRepository tokenRepository;

    @Autowired
    public TokenServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public void setSecretKey(String key, JwtModel model) {
        this.tokenRepository.setSecretKey(key, model);
    }

    @Override
    public Boolean setKeyExpiration(String key, Date exp) {
        return this.tokenRepository.setKeyExpiration(key, exp);
    }

    @Override
    public void deleteToken(String key) {
        this.tokenRepository.delete(key);
    }

    @Override
    public Object getSecretKey(String key) {
        Object obj = this.tokenRepository.get(key);
        if (obj == null) {
            throw new TokenNotFoundException("Token not found");
        }
        return obj;
    }
}