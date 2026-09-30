package com.abdul.catalogservice.adapter.out.persistence.specification;

import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import com.abdul.catalogservice.adapter.out.persistence.utils.CursorCodec;
import com.abdul.catalogservice.domain.track.model.TrackFilterInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TrackSpecification {
    private final CursorCodec cursorCodec;

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

            return criteriaBuilder.and(
                    predicate,
                    criteriaBuilder.or(
                            afterProperty,
                            criteriaBuilder.and(criteriaBuilder.equal(propertyPath, value), afterId)
                    )
            );
        };
    }
}
