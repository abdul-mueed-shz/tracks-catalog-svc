package com.abdul.catalogservice.adapter.in.web.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record TrackResponse(
        UUID id,
        UUID uuid,
        String title,
        String genre,
        Integer durationMs,
        LocalDate releaseDate,
        UserSummaryResponse user,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
