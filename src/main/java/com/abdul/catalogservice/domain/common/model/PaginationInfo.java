package com.abdul.catalogservice.domain.common.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginationInfo {
    private String cursor;
    private int size = 20;
}
