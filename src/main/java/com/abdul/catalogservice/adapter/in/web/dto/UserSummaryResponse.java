package com.abdul.catalogservice.adapter.in.web.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserSummaryResponse(
        UUID id,
        UUID uuid,
        String name,
        Boolean isArtist
) {
}
