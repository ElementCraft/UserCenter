package com.fqfx.UserCenter.repository;

import com.fqfx.UserCenter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByAccountAndPasswordAndDeleted(String account, String password, Boolean deleted);
}
