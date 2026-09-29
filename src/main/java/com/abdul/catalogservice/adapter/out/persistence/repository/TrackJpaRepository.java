package com.abdul.catalogservice.adapter.out.persistence.repository;

import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackJpaRepository extends JpaRepository<Track, Long> {
}
