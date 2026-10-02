package com.abdul.catalogservice.domain.artistofday.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ArtistRotationInfo {
    private Long id;
    private Long lastArtistId;

    public void advanceTo(Long artistId) {
        this.lastArtistId = artistId;
    }
}
