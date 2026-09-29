package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artist.port.out.ArtistRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ArtistRepositoryAdapter implements ArtistRepository {
    @Override
    public ArtistInfo getArtistById(Long id) {
        return null;
    }

    @Override
    public ArtistInfo upsertArtist(ArtistInfo artistInfo) {
        return null;
    }
}
