package com.abdul.catalogservice.domain.artist.model;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record ArtistInfo(
        Long id,
        String name
) implements Serializable {
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
