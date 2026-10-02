package com.abdul.catalogservice.adapter.out.persistence.utils.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CursorPredicateSupport {
    public <E> Predicate addCursorPredicate(Root<E> root, CriteriaBuilder criteriaBuilder, Predicate predicate, KeysetScrollPosition position, Sort sort) {
        if (position.isInitial()) {
            return predicate;
        }

        String property = sort.iterator().next().getProperty();
        LocalDateTime value = (LocalDateTime) position.getKeys().get(property);
        Long id = (Long) position.getKeys().get("id");
        var propertyPath = root.<LocalDateTime>get(property);
        var idPath = root.<Long>get("id");
        boolean ascending = sort.iterator().next().isAscending();
        var afterProperty = ascending
                ? criteriaBuilder.greaterThan(propertyPath, value)
                : criteriaBuilder.lessThan(propertyPath, value);
        var afterId = ascending
                ? criteriaBuilder.greaterThan(idPath, id)
                : criteriaBuilder.lessThan(idPath, id);
        return criteriaBuilder.or(
                afterProperty,
                criteriaBuilder.and(criteriaBuilder.equal(propertyPath, value), afterId)
        );
    }
}
