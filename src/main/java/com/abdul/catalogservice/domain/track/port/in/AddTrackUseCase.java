package com.abdul.catalogservice.domain.track.port.in;

import com.abdul.catalogservice.domain.track.model.TrackInfo;

public interface AddTrackUseCase {
    TrackInfo execute(Long userId, TrackInfo trackInfo);
}
