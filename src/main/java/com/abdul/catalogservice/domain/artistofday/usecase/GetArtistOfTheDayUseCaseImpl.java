package com.abdul.catalogservice.domain.artistofday.usecase;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artistofday.model.ArtistOfTheDayInfo;
import com.abdul.catalogservice.domain.artistofday.model.ArtistRotationInfo;
import com.abdul.catalogservice.domain.artistofday.port.in.GetArtistOfTheDayUseCase;
import com.abdul.catalogservice.domain.artistofday.port.out.ArtistCatalog;
import com.abdul.catalogservice.domain.artistofday.port.out.ArtistOfTheDayRepository;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.LocalDate;

@RequiredArgsConstructor
public class GetArtistOfTheDayUseCaseImpl implements GetArtistOfTheDayUseCase {
    private final ArtistOfTheDayRepository artistOfTheDayRepository;
    private final ArtistCatalog artistCatalog;
    private final Clock clock;

    @Override
    public ArtistInfo execute() {
        LocalDate today = LocalDate.now(clock);

        ArtistOfTheDayInfo existingAssignment = artistOfTheDayRepository.getArtistOfTheDay(today);
        if (existingAssignment != null) {
            return existingAssignment.getArtist();
        }

        ArtistRotationInfo rotation = artistOfTheDayRepository.getArtistRotationInfo();
        if (rotation == null) {
            rotation = artistOfTheDayRepository.initializeRotation();
        }

        // Re-check after lock is acquired to avoid duplicate assignment
        existingAssignment = artistOfTheDayRepository.getArtistOfTheDay(today);
        if (existingAssignment != null) {
            return existingAssignment.getArtist();
        }

        ArtistInfo artist = rotation.selectNextArtist(artistCatalog);

        artistOfTheDayRepository.updateArtistRotation(rotation);
        ArtistOfTheDayInfo savedAssignment = artistOfTheDayRepository.saveArtistOfTheDay(
                ArtistOfTheDayInfo.create(today, artist)
        );

        return savedAssignment.getArtist();
    }
}
