package com.abdul.catalogservice.adapter.out.persistence.repository;

import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface TrackJpaRepository extends JpaRepository<Track, Long>, JpaSpecificationExecutor<Track> {
    List<Track> findAllByUserId(Long userId);

    default List<Track> findAll(Specification<Track> specification, Sort sort, int limit) {
        return findBy(specification, query -> query.sortBy(sort).limit(limit).all());
    }
}