package com.abdul.catalogservice.domain.artistofday.model;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ArtistOfTheDayInfo {
    private LocalDate day;
    private ArtistInfo artist;

    public static ArtistOfTheDayInfo create(LocalDate day, ArtistInfo artist) {
        return ArtistOfTheDayInfo.builder()
                .day(day)
                .artist(artist)
                .build();
    }
}
