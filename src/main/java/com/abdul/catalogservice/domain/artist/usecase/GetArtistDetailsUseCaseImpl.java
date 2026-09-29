package com.abdul.catalogservice.domain.artist.usecase;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artist.port.in.GetArtistDetailsUseCase;
import com.abdul.catalogservice.domain.artist.port.out.ArtistRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetArtistDetailsUseCaseImpl implements GetArtistDetailsUseCase {
    private final ArtistRepository artistRepository;

    @Override
    public ArtistInfo execute(Long artistId) {
        return null;
    }
}
