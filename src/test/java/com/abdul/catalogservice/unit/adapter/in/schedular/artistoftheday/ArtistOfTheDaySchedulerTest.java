package com.abdul.catalogservice.unit.adapter.in.schedular.artistoftheday;

import com.abdul.catalogservice.adapter.in.schedular.artistoftheday.ArtistOfTheDayScheduler;
import com.abdul.catalogservice.domain.artistofday.port.in.GetArtistOfTheDayUseCase;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ArtistOfTheDaySchedulerTest {
    private final GetArtistOfTheDayUseCase useCase = mock(GetArtistOfTheDayUseCase.class);
    private final ArtistOfTheDayScheduler scheduler = new ArtistOfTheDayScheduler(useCase);

    @Test
    void assignsArtistOfTheDay() {
        scheduler.assignArtistOfTheDay();

        verify(useCase).execute();
    }
}
