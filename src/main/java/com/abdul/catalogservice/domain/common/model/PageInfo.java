package com.abdul.catalogservice.domain.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PageInfo <T> {
    private String cursor;
    private int size;
    private List<T> data;

    public <R> PageInfo<R> map(Function<? super T, R> mapper) {
        List<R> mappedData = data == null ? Collections.emptyList() : data.stream().map(mapper).toList();
        return PageInfo.<R>builder()
                .cursor(this.cursor)
                .size(this.size)
                .data(mappedData)
                .build();
    }
}
