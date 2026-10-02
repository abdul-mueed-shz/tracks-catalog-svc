package com.abdul.catalogservice.adapter.out.persistence.specification;

import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import com.abdul.catalogservice.adapter.out.persistence.entity.UserAlias;
import com.abdul.catalogservice.adapter.out.persistence.utils.specification.CursorPredicateSupport;
import com.abdul.catalogservice.domain.track.model.TrackFilterInfo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TrackSpecification {
    private final CursorPredicateSupport cursorPredicateSupport;

    public Specification<Track> filterBy(
            TrackFilterInfo filterInfo,
            KeysetScrollPosition position,
            Sort sort
    ) {
        return (root, query, criteriaBuilder) -> {
            var predicate = criteriaBuilder.conjunction();
            if (filterInfo.getUserId() != null) {
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.equal(root.get("user").get("id"), filterInfo.getUserId())
                );
            }
            if (filterInfo.getUserName() != null && !filterInfo.getUserName().isBlank()) {
                String searchPattern = "%" + filterInfo.getUserName().trim().toLowerCase() + "%";
                var user = root.get("user");
                var nameMatch = criteriaBuilder.like(
                        criteriaBuilder.lower(user.get("name")),
                        searchPattern
                );
                var aliasQuery = query.subquery(Long.class);
                var alias = aliasQuery.from(UserAlias.class);
                aliasQuery.select(criteriaBuilder.literal(1L));
                aliasQuery.where(
                        criteriaBuilder.equal(alias.get("user"), user),
                        criteriaBuilder.like(
                                criteriaBuilder.lower(alias.get("normalizedName")),
                                searchPattern
                        )
                );
                predicate = criteriaBuilder.and(
                        predicate,
                        criteriaBuilder.or(nameMatch, criteriaBuilder.exists(aliasQuery))
                );
            }
            Predicate cursorPredicate =
                    cursorPredicateSupport.addCursorPredicate(root, criteriaBuilder, predicate, position, sort);
            return criteriaBuilder.and(
                    predicate,
                    cursorPredicate
            );
        };
    }
}
