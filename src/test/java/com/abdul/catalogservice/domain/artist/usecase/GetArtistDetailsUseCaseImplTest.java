package com.abdul.catalogservice.domain.artist.usecase;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artist.port.out.ArtistRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetArtistDetailsUseCaseImplTest {

    private final ArtistRepository artistRepository = mock(ArtistRepository.class);
    private final GetArtistDetailsUseCaseImpl useCase =
            new GetArtistDetailsUseCaseImpl(artistRepository);

    @Test
    void returnsArtistDetailsForArtistId() {
        Long artistId = 1L;
        ArtistInfo artistInfo = ArtistInfo.builder()
                .id(artistId)
                .name("Artist")
                .build();
        when(artistRepository.getArtistById(artistId)).thenReturn(artistInfo);

        ArtistInfo result = useCase.execute(artistId);

        assertThat(result).isSameAs(artistInfo);
        verify(artistRepository).getArtistById(artistId);
    }
}
