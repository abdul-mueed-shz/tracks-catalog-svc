package com.abdul.catalogservice.domain.artist.port.in;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;

public interface CreateArtistUseCase {
    ArtistInfo execute(ArtistInfo artistInfo);
}
