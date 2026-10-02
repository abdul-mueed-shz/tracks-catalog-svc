package com.abdul.catalogservice.adapter.out.persistence.repository;

import com.abdul.catalogservice.adapter.out.persistence.entity.UserAlias;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAliasJpaRepository extends JpaRepository<UserAlias, Long> {
    boolean existsByUser_IdAndNormalizedName(Long userId, String normalizedName);
}
