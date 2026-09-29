package com.abdul.catalogservice.domain.track.usecase;

import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.in.GetTracksByArtistUseCase;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetTracksByArtistUseCaseImpl implements GetTracksByArtistUseCase {
    private final TrackRepository trackRepository;

    @Override
    public List<TrackInfo> execute(Long artistId) {
        return List.of();
    }
}
