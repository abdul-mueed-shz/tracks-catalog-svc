package com.abdul.catalogservice.domain.track.port.in;

import com.abdul.catalogservice.domain.track.model.TrackInfo;

import java.util.UUID;

public interface AddTrackUseCase {
    TrackInfo execute(UUID userId, TrackInfo trackInfo);
}
