package com.abdul.catalogservice.adapter.in.schedular.artistoftheday;

import com.abdul.catalogservice.domain.artistofday.port.in.GetArtistOfTheDayUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArtistOfTheDayScheduler {
    private final GetArtistOfTheDayUseCase getArtistOfTheDayUseCase;

    @Scheduled(cron = "0 0 0 * * *", zone = "UTC")
    public void assignArtistOfTheDay() {
        getArtistOfTheDayUseCase.execute();
    }
}
