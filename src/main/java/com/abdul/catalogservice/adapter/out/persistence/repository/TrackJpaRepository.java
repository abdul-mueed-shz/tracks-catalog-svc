package com.abdul.catalogservice.adapter.out.persistence.repository;

import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;

import java.util.List;

public interface TrackJpaRepository extends JpaRepository<Track, Long> {
    List<Track> findAllByUserId(Long userId);

    Window<Track> findByUserId(Long userId, KeysetScrollPosition position, Limit limit, Sort sort);
}