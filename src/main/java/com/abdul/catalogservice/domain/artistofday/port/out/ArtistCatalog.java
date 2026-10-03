package com.abdul.catalogservice.domain.artistofday.port.out;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;

public interface ArtistCatalog {
    ArtistInfo findFirstArtist();

    ArtistInfo findArtistAfterId(Long artistId);
}
