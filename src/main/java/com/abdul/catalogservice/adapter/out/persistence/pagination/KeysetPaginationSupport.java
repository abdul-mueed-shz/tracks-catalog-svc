package com.abdul.catalogservice.adapter.out.persistence.pagination;

import com.abdul.catalogservice.adapter.out.persistence.utils.CursorCodec;
import com.abdul.catalogservice.domain.common.enums.SortDirection;
import com.abdul.catalogservice.domain.common.enums.SortProperty;
import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Window;
import org.springframework.stereotype.Component;

import java.util.function.BiFunction;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class KeysetPaginationSupport {
    private final CursorCodec cursorCodec;

    public <E, D> PageInfo<D> execute(
            PaginationInfo paginationInfo,
            SortInfo sortInfo,
            BiFunction<KeysetScrollPosition, LimitAndSort, Window<E>> query,
            Function<E, D> mapper
    ) {
        int size = paginationInfo.getSize();
        Sort.Direction direction = sortInfo.getDirection() == SortDirection.ASC
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        String property = sortInfo.getProperty() == null
                ? SortProperty.UPDATED_AT.getProperty()
                : sortInfo.getProperty();
        LimitAndSort limitAndSort = new LimitAndSort(Limit.of(size), Sort.by(direction, property));
        Window<E> window = query.apply(
                cursorCodec.decodeCursor(paginationInfo.getCursor()),
                limitAndSort
        );
        String nextCursor = window.hasNext()
                ? cursorCodec.encodeCursor(window.positionAt(window.size() - 1))
                : null;

        return PageInfo.<D>builder()
                .cursor(nextCursor)
                .size(size)
                .data(window.getContent().stream().map(mapper).toList())
                .build();
    }

    public record LimitAndSort(Limit limit, Sort sort) {
    }
}
