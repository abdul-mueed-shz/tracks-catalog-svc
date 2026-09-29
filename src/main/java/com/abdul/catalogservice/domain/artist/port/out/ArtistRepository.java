package com.abdul.catalogservice.domain.artist.port.out;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;

public interface ArtistRepository {
    ArtistInfo getArtistById(Long id);

    ArtistInfo upsertArtist(ArtistInfo artistInfo);
}
