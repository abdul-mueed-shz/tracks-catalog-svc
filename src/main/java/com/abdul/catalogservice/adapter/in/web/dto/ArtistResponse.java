package com.abdul.catalogservice.adapter.in.web.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ArtistResponse(
        UUID id,
        UUID uuid,
        String name
) {
}
