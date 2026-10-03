package com.abdul.catalogservice.adapter.in.web.dto;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record UserResponse(
        UUID id,
        UUID uuid,
        String name,
        Boolean isArtist,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
