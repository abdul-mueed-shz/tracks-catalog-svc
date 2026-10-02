package com.abdul.catalogservice.adapter.out.persistence.utils.pagination;

import com.abdul.catalogservice.adapter.out.persistence.utils.codec.CursorCodec;
import com.abdul.catalogservice.domain.common.enums.SortDirection;
import com.abdul.catalogservice.domain.common.enums.SortProperty;
import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class CursorPaginationSupport {
    private final CursorCodec cursorCodec;

    public <E, D> PageInfo<D> execute(
            PaginationInfo paginationInfo,
            SortInfo sortInfo,
            BiFunction<KeysetScrollPosition, Query, List<E>> query,
            Function<E, D> mapper,
            Function<E, Object> sortValue,
            Function<E, Object> idValue
    ) {
        int size = paginationInfo.getSize();
        Sort.Direction direction = sortInfo.getDirection() == SortDirection.ASC
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        String property = sortInfo.getProperty() == null
                ? SortProperty.UPDATED_AT.getProperty()
                : sortInfo.getProperty();
        Query queryOptions = new Query(
                Limit.of(size + 1),
                Sort.by(direction, property),
                property
        );
        List<E> results = query.apply(
                cursorCodec.decodeCursor(paginationInfo.getCursor()),
                queryOptions
        );
        boolean hasMore = results.size() > size;
        List<E> content = hasMore ? results.subList(0, size) : results;
        String nextCursor = null;
        if (hasMore) {
            E last = content.get(content.size() - 1);
            nextCursor = cursorCodec.encodeCursor(ScrollPosition.forward(Map.of(
                    property,
                    sortValue.apply(last),
                    "id",
                    idValue.apply(last)
            )));
        }
        return PageInfo.<D>builder()
                .cursor(nextCursor)
                .size(size)
                .data(content.stream().map(mapper).toList())
                .build();
    }

    public record Query(Limit limit, Sort sort, String property) {
    }
}
