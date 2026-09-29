package com.abdul.catalogservice.domain.artist.usecase;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artist.port.in.CreateArtistUseCase;
import com.abdul.catalogservice.domain.artist.port.out.ArtistRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateArtistUseCaseImpl implements CreateArtistUseCase {
    private final ArtistRepository artistRepository;

    @Override
    public ArtistInfo execute(ArtistInfo artistInfo) {
        return artistRepository.upsertArtist(artistInfo);
    }
}
