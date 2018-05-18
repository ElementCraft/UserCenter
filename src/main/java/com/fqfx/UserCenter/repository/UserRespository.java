package com.fqfx.UserCenter.repository;

import com.fqfx.UserCenter.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRespository extends JpaRepository<User, Long> {
}
