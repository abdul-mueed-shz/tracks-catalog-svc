package com.abdul.catalogservice.domain.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PageInfo <T> {
    private String cursor;
    private int size;
    private List<T> data;
}
