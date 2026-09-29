package com.abdul.catalogservice.domain.track.model;

import com.abdul.catalogservice.domain.artist.common.model.BaseInfo;
import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TrackInfo extends BaseInfo {
    private ArtistInfo artist;
    private String title;
    private String genre;
    private Integer durationMs;
    private LocalDate releaseDate;
}
