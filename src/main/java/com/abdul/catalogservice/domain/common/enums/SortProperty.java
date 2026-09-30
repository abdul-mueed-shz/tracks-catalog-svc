package com.abdul.catalogservice.domain.common.enums;

import lombok.Getter;

@Getter
public enum SortProperty {
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt");

    private final String property;

    SortProperty(String property) {
        this.property = property;
    }

    public static SortProperty fromValue(String value) {
        if (value == null || value.isBlank()) {
            return UPDATED_AT;
        }
        return switch (value.trim().toLowerCase()) {
            case "created_at", "createdat" -> CREATED_AT;
            case "updated_at", "updatedat" -> UPDATED_AT;
            default -> throw new IllegalArgumentException("Unsupported sort property: " + value);
        };
    }
}
