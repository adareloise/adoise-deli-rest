package com.adoise.library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adoise.library.entity.Role;
import com.adoise.library.enums.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByType(RoleEnum type);
}
