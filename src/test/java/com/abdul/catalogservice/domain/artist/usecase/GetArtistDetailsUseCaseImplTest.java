package com.abdul.catalogservice.domain.artist.usecase;

import com.abdul.catalogservice.domain.artist.port.out.ArtistRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class GetArtistDetailsUseCaseImplTest {

    private final ArtistRepository artistRepository = mock(ArtistRepository.class);
    private final GetArtistDetailsUseCaseImpl useCase =
            new GetArtistDetailsUseCaseImpl(artistRepository);

    @Test
    void returnsNoArtistDetails() {
        assertThat(useCase.execute(1L)).isNull();
    }
}
