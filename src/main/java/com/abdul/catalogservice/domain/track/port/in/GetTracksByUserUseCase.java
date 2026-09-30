package com.abdul.catalogservice.domain.track.port.in;

import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.track.model.TrackInfo;

import java.util.List;

public interface GetTracksByUserUseCase {
    List<TrackInfo> execute(Long userId);

    PageInfo<TrackInfo> execute(Long userId, String cursor, int size);
}
