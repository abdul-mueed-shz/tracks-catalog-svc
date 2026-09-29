package com.abdul.catalogservice.domain.track.usecase;

import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class GetTracksByUserUseCaseImplTest {

    private final TrackRepository trackRepository = mock(TrackRepository.class);
    private final GetTracksByUserUseCaseImpl useCase =
            new GetTracksByUserUseCaseImpl(trackRepository);

    @Test
    void returnsNoTracksForAUser() {
        assertThat(useCase.execute(1L)).isEmpty();
    }
}
