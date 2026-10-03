package com.abdul.catalogservice.adapter.in.web.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserAliasResponse(
        UUID id,
        UUID uuid,
        String aliasName,
        String normalizedName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
