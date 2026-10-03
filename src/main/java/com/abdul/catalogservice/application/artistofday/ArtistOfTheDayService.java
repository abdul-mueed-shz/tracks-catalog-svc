package com.abdul.catalogservice.application.artistofday;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artistofday.port.in.GetArtistOfTheDayUseCase;
import com.abdul.catalogservice.domain.artistofday.usecase.GetArtistOfTheDayUseCaseImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@RequiredArgsConstructor
public class ArtistOfTheDayService implements GetArtistOfTheDayUseCase {
    private final GetArtistOfTheDayUseCaseImpl getArtistOfTheDayUseCase;

    @Override
    @Transactional
    @Cacheable(cacheNames = "artistOfTheDay",
            key = "T(java.time.LocalDate).now(T(java.time.ZoneOffset).UTC)")
    public ArtistInfo execute() {
        return getArtistOfTheDayUseCase.execute();
    }
}
