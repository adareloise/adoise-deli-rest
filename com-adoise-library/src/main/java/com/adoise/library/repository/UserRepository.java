package com.adoise.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adoise.library.entity.UserInfo;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    UserInfo findByEmail(String email);
}
