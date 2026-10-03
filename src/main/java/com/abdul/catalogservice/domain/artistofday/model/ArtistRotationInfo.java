package com.abdul.catalogservice.domain.artistofday.model;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artistofday.port.out.ArtistCatalog;
import com.abdul.catalogservice.domain.common.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArtistRotationInfo {
    private Long id;
    private Long lastArtistId;

    public ArtistInfo selectNextArtist(ArtistCatalog artistCatalog) {
        ArtistInfo artist = lastArtistId == null
                ? artistCatalog.findFirstArtist()
                : findNextArtist(artistCatalog);

        if (artist == null) {
            throw new NotFoundException("No artist found for the day");
        }

        advanceTo(artist.id());
        return artist;
    }

    private ArtistInfo findNextArtist(ArtistCatalog artistCatalog) {
        ArtistInfo nextArtist = artistCatalog.findArtistAfterId(lastArtistId);
        return nextArtist != null
                ? nextArtist
                : artistCatalog.findFirstArtist();
    }

    public void advanceTo(Long artistId) {
        this.lastArtistId = artistId;
    }
}
