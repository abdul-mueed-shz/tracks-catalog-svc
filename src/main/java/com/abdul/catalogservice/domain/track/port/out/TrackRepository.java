package com.abdul.catalogservice.domain.track.port.out;

import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.track.model.TrackInfo;

import java.util.List;

public interface TrackRepository {
    List<TrackInfo> getAllByUserId(Long userId);
    PageInfo<TrackInfo> getAllByUserId(Long userId, String cursor, int size);
    TrackInfo save(TrackInfo trackInfo);
}
