package com.abdul.catalogservice.unit.domain.artistofday.usecase;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artistofday.model.ArtistOfTheDayInfo;
import com.abdul.catalogservice.domain.artistofday.model.ArtistRotationInfo;
import com.abdul.catalogservice.domain.artistofday.port.out.ArtistCatalog;
import com.abdul.catalogservice.domain.artistofday.port.out.ArtistOfTheDayRepository;
import com.abdul.catalogservice.domain.artistofday.usecase.GetArtistOfTheDayUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetArtistOfTheDayUseCaseImplTest {
    private final ArtistOfTheDayRepository repository = mock(ArtistOfTheDayRepository.class);
    private final ArtistCatalog artistCatalog = mock(ArtistCatalog.class);
    private final GetArtistOfTheDayUseCaseImpl useCase =
            new GetArtistOfTheDayUseCaseImpl(repository, artistCatalog, Clock.systemUTC());

    @Test
    void assignsAndReturnsNextArtist() {
        ArtistRotationInfo rotation = ArtistRotationInfo.builder().id(1L).build();
        ArtistInfo artist = new ArtistInfo(1L, "First Artist");
        when(repository.getArtistOfTheDay(any(LocalDate.class))).thenReturn(null);
        ArtistOfTheDayInfo assignment = ArtistOfTheDayInfo.builder()
                .day(LocalDate.now(ZoneOffset.UTC))
                .artist(artist)
                .build();
        when(repository.getArtistRotationInfo()).thenReturn(rotation);
        when(artistCatalog.findFirstArtist()).thenReturn(artist);
        when(repository.saveArtistOfTheDay(any(ArtistOfTheDayInfo.class))).thenReturn(assignment);

        assertThat(useCase.execute()).isEqualTo(artist);
        verify(repository).updateArtistRotation(rotation);
        verify(repository).saveArtistOfTheDay(argThat(saved ->
                saved.getDay().equals(LocalDate.now(ZoneOffset.UTC))
                        && saved.getArtist().equals(artist)));
    }

    @Test
    void returnsExistingArtistWithoutAdvancingRotation() {
        ArtistInfo artist = new ArtistInfo(2L, "Existing Artist");
        when(repository.getArtistOfTheDay(any(LocalDate.class)))
                .thenReturn(ArtistOfTheDayInfo.builder().artist(artist).build());

        assertThat(useCase.execute()).isEqualTo(artist);

        verify(repository, never()).getArtistRotationInfo();
        verify(repository, never()).updateArtistRotation(any());
        verify(repository, never()).saveArtistOfTheDay(any());
        verifyNoInteractions(artistCatalog);
    }
}
