package com.abdul.catalogservice.domain.track.port.out;

import com.abdul.catalogservice.domain.track.model.TrackInfo;

import java.util.List;

public interface TrackRepository {
    List<TrackInfo> getAllByArtistId(Long artistId);
}
