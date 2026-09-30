package com.abdul.catalogservice.domain.common.model;

import com.abdul.catalogservice.domain.common.enums.SortDirection;
import com.abdul.catalogservice.domain.common.enums.SortProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
public class SortInfo {
    @Builder.Default
    private SortDirection direction = SortDirection.DESC;

    @Builder.Default
    private String property = SortProperty.UPDATED_AT.getProperty();

    public SortInfo() {
        this.direction = SortDirection.DESC;
        this.property = SortProperty.UPDATED_AT.getProperty();
    }

    public void setProperty(String property) {
        this.property = SortProperty.fromValue(property).getProperty();
    }
}
