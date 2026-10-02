package com.abdul.catalogservice.domain.artistofday.port.out;

import com.abdul.catalogservice.domain.artistofday.model.ArtistOfTheDayInfo;
import com.abdul.catalogservice.domain.artistofday.model.ArtistRotationInfo;

import java.time.LocalDate;

public interface ArtistOfTheDayRepository {
    ArtistOfTheDayInfo getArtistOfTheDay(LocalDate day);

    ArtistRotationInfo initializeRotation();

    ArtistRotationInfo getArtistRotationInfo();

    void updateArtistRotation(ArtistRotationInfo artistRotationInfo);

    ArtistOfTheDayInfo saveArtistOfTheDay(ArtistOfTheDayInfo artistOfTheDayInfo);

}
