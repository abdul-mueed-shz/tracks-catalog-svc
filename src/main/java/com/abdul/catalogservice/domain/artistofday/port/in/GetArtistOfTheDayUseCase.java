package com.abdul.catalogservice.domain.artistofday.port.in;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;

public interface GetArtistOfTheDayUseCase {
    ArtistInfo execute();
}
