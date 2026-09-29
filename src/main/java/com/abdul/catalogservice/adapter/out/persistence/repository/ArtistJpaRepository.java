package com.abdul.catalogservice.adapter.out.persistence.repository;

import com.abdul.catalogservice.adapter.out.persistence.entity.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistJpaRepository extends JpaRepository<Artist, Long> {
}
