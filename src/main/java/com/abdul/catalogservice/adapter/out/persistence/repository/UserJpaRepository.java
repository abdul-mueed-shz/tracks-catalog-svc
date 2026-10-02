package com.abdul.catalogservice.adapter.out.persistence.repository;

import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    Optional<User> findFirstByIsArtistTrueAndIdGreaterThanOrderByIdAsc(Long id);

    Optional<User> findFirstByIsArtistTrueOrderByIdAsc();

    default List<User> findAll(Specification<User> specification, Sort sort, int limit) {
        return findBy(specification, query -> query.sortBy(sort).limit(limit).all());
    }
}
