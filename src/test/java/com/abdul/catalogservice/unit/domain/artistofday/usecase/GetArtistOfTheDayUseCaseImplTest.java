package com.abdul.catalogservice.unit.domain.artistofday.usecase;

import com.abdul.catalogservice.domain.artistofday.port.out.ArtistOfTheDayRepository;
import com.abdul.catalogservice.domain.artistofday.usecase.GetArtistOfTheDayUseCaseImpl;
import com.abdul.catalogservice.domain.artistofday.model.ArtistOfTheDayInfo;
import com.abdul.catalogservice.domain.artistofday.model.ArtistRotationInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetArtistOfTheDayUseCaseImplTest {
    private final ArtistOfTheDayRepository repository = mock(ArtistOfTheDayRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final GetArtistOfTheDayUseCaseImpl useCase =
            new GetArtistOfTheDayUseCaseImpl(repository, userRepository);

    @Test
    void assignsAndReturnsNextArtist() {
        ArtistRotationInfo rotation = ArtistRotationInfo.builder().id(1L).build();
        UserInfo artist = UserInfo.builder().id(1L).isArtist(true).build();
        when(repository.getArtistOfTheDay(any(LocalDate.class))).thenReturn(null);
        ArtistOfTheDayInfo assignment = ArtistOfTheDayInfo.builder()
                .day(LocalDate.now(ZoneOffset.UTC))
                .artist(artist)
                .build();
        when(repository.getArtistRotationInfo()).thenReturn(rotation);
        when(userRepository.findFirstArtistUser()).thenReturn(artist);
        when(repository.saveArtistOfTheDay(any(ArtistOfTheDayInfo.class))).thenReturn(assignment);

        assertThat(useCase.execute()).isSameAs(artist);
        verify(repository).updateArtistRotation(rotation);
        verify(repository).saveArtistOfTheDay(argThat(saved ->
                saved.getDay().equals(LocalDate.now(ZoneOffset.UTC))
                        && saved.getArtist().equals(artist)));
    }

    @Test
    void returnsExistingArtistWithoutAdvancingRotation() {
        UserInfo artist = UserInfo.builder().id(2L).isArtist(true).build();
        when(repository.getArtistOfTheDay(any(LocalDate.class)))
                .thenReturn(ArtistOfTheDayInfo.builder().artist(artist).build());

        assertThat(useCase.execute()).isSameAs(artist);

        verify(repository, never()).getArtistRotationInfo();
        verify(repository, never()).updateArtistRotation(any());
        verify(repository, never()).saveArtistOfTheDay(any());
        verifyNoInteractions(userRepository);
    }
}
