package com.fqfx.UserCenter.repository;

import com.fqfx.UserCenter.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findOneByCodeAndDeleted(String code, Boolean deleted);
}
