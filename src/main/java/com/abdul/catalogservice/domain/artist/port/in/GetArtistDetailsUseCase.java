package com.abdul.catalogservice.domain.artist.port.in;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;

public interface GetArtistDetailsUseCase {
    ArtistInfo execute(Long artistId);
}
