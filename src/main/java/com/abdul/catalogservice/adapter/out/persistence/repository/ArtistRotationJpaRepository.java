package com.abdul.catalogservice.adapter.out.persistence.repository;

import com.abdul.catalogservice.adapter.out.persistence.entity.ArtistRotation;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ArtistRotationJpaRepository extends JpaRepository<ArtistRotation, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select rotation from ArtistRotation rotation where rotation.id = 1")
    Optional<ArtistRotation> findLocked();
}
