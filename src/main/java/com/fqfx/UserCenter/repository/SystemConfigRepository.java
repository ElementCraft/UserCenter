package com.fqfx.UserCenter.repository;

import com.fqfx.UserCenter.domain.SystemConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SystemConfigRepository extends JpaRepository<SystemConfig, Long> {

    List<SystemConfig> getAllByDeleted(Boolean deleted);

    Optional<SystemConfig> findOneByKeyAndDeleted(String key, Boolean isDeleted);

    Page<SystemConfig> findAllByDeleted(Boolean isDeleted, Pageable pageable);
}
