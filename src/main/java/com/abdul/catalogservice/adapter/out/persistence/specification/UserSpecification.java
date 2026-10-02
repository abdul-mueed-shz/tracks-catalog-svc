package com.abdul.catalogservice.adapter.out.persistence.specification;

import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.adapter.out.persistence.utils.specification.CursorPredicateSupport;
import com.abdul.catalogservice.domain.user.model.UserFilterInfo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserSpecification {
    private final CursorPredicateSupport cursorPredicateSupport;

    public Specification<User> filterBy(
            UserFilterInfo filterInfo,
            KeysetScrollPosition position,
            Sort sort
    ) {
        return (root, query, criteriaBuilder) -> {
            Predicate predicate = criteriaBuilder.conjunction();
            if (filterInfo.getIsArtist() != null) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.equal(root.get("isArtist"), filterInfo.getIsArtist())
                );
            }
            Predicate cursorPredicate =
                    cursorPredicateSupport.addCursorPredicate(root, criteriaBuilder, predicate, position, sort);
            return criteriaBuilder.and(predicate, cursorPredicate);
        };
    }
}
