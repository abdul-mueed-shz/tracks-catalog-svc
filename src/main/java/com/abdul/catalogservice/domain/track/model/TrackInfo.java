package com.abdul.catalogservice.domain.track.model;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackInfo {
    private Long id;
    private ArtistInfo artist;
    private String title;
    private String genre;
    private Integer durationMs;
    private LocalDate releaseDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
