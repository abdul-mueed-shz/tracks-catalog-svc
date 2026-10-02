package com.abdul.catalogservice.adapter.out.persistence.repository;

import com.abdul.catalogservice.adapter.out.persistence.entity.ArtistOfTheDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface ArtistOfTheDayJpaRepository extends JpaRepository<ArtistOfTheDay, LocalDate> {
}
