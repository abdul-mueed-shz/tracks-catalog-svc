package com.abdul.catalogservice.domain.track.port.in;

import com.abdul.catalogservice.domain.track.model.TrackInfo;

import java.util.List;

public interface GetTracksByArtistUseCase {
    List<TrackInfo> execute(Long artistId);
}
