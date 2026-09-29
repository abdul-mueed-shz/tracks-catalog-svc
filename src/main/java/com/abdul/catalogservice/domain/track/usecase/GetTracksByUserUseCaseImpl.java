package com.abdul.catalogservice.domain.track.usecase;

import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.in.GetTracksByUserUseCase;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetTracksByUserUseCaseImpl implements GetTracksByUserUseCase {
    private final TrackRepository trackRepository;

    @Override
    public List<TrackInfo> execute(Long userId) {
        return List.of();
    }
}
