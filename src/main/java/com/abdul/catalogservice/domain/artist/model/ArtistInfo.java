package com.abdul.catalogservice.domain.artist.model;

import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Builder
public record ArtistInfo(
        Long id,
        UUID uuid,
        String name
) implements Serializable {
    public ArtistInfo(Long id, String name) {
        this(id, UUID.randomUUID(), name);
    }

    public Long getId() {
        return id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }
}
