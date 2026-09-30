package com.abdul.catalogservice.adapter.out.persistence.repository;

import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackJpaRepository extends JpaRepository<Track, Long> {
    List<Track> findAllByUserId(Long userId);
}