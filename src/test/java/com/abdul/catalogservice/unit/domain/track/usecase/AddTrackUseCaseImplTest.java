package com.abdul.catalogservice.unit.domain.track.usecase;

import com.abdul.catalogservice.domain.common.exception.DomainValidationException;
import com.abdul.catalogservice.domain.common.exception.NotFoundException;
import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import com.abdul.catalogservice.domain.track.usecase.AddTrackUseCaseImpl;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class AddTrackUseCaseImplTest {
    private final TrackRepository trackRepository = mock(TrackRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserValidator userValidator = new UserValidator();
    private final AddTrackUseCaseImpl useCase =
            new AddTrackUseCaseImpl(trackRepository, userRepository, userValidator);

    @Test
    void addsTrackWithArtistAsOwner() {
        UserInfo artist = UserInfo.builder().id(1L).isArtist(true).build();
        TrackInfo request = TrackInfo.builder().title("Track").build();
        TrackInfo saved = request.toBuilder().user(artist).build();
        when(userRepository.getUserById(1L)).thenReturn(artist);
        when(trackRepository.save(any(TrackInfo.class))).thenReturn(saved);

        TrackInfo result = useCase.execute(1L, request);

        assertThat(result).isSameAs(saved);
        verify(trackRepository).save(argThat(track -> track.getUser() == artist
                && track.getTitle().equals("Track")));
    }

    @Test
    void rejectsTrackForMissingUser() {
        when(userRepository.getUserById(1L)).thenReturn(null);
        TrackInfo trackInfo = TrackInfo.builder().build();

        assertThatThrownBy(() -> useCase.execute(1L, trackInfo))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found.");
        verifyNoInteractions(trackRepository);
    }

    @Test
    void rejectsTrackForNonArtistUser() {
        when(userRepository.getUserById(1L))
                .thenReturn(UserInfo.builder().id(1L).isArtist(false).build());
        TrackInfo trackInfo = TrackInfo.builder().build();

        assertThatThrownBy(() -> useCase.execute(1L, trackInfo))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("User is not an artist.");
        verifyNoInteractions(trackRepository);
    }
}
