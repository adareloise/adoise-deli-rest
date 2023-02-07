package com.adoise.library.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.adoise.library.entity.Privilege;
import com.adoise.library.enums.PrivilegeEnum;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {
    Privilege findByType(PrivilegeEnum type);
}
