package com.abdul.catalogservice.domain.artist.model;

import lombok.Builder;

@Builder
public record ArtistInfo(
        Long id,
        String name
) {
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
