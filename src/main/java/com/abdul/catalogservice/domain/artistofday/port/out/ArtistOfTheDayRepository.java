package com.abdul.catalogservice.domain.artistofday.port.out;

import com.abdul.catalogservice.domain.artistofday.model.ArtistOfTheDayInfo;
import com.abdul.catalogservice.domain.artistofday.model.ArtistRotationInfo;

public interface ArtistOfTheDayRepository {
    ArtistRotationInfo initializeRotation();

    ArtistRotationInfo getArtistRotationInfo();

    void updateArtistRotation(ArtistRotationInfo artistRotationInfo);

    ArtistOfTheDayInfo saveArtistOfTheDay(ArtistOfTheDayInfo artistOfTheDayInfo);

}
