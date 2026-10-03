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

import java.util.UUID;

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
        UUID userUuid = UUID.randomUUID();
        UserInfo artist = UserInfo.builder().id(1L).uuid(userUuid).isArtist(true).build();
        TrackInfo request = TrackInfo.builder().title("Track").build();
        TrackInfo saved = request.toBuilder().user(artist).build();
        when(userRepository.getUserByUuid(userUuid)).thenReturn(artist);
        when(trackRepository.save(any(TrackInfo.class))).thenReturn(saved);

        TrackInfo result = useCase.execute(userUuid, request);

        assertThat(result).isSameAs(saved);
        verify(trackRepository).save(argThat(track -> track.getUser() == artist
                && track.getTitle().equals("Track")));
    }

    @Test
    void rejectsTrackForMissingUser() {
        UUID userUuid = UUID.randomUUID();
        when(userRepository.getUserByUuid(userUuid)).thenReturn(null);
        TrackInfo trackInfo = TrackInfo.builder().build();

        assertThatThrownBy(() -> useCase.execute(userUuid, trackInfo))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found.");
        verifyNoInteractions(trackRepository);
    }

    @Test
    void rejectsTrackForNonArtistUser() {
        UUID userUuid = UUID.randomUUID();
        when(userRepository.getUserByUuid(userUuid))
                .thenReturn(UserInfo.builder().id(1L).uuid(userUuid).isArtist(false).build());
        TrackInfo trackInfo = TrackInfo.builder().build();

        assertThatThrownBy(() -> useCase.execute(userUuid, trackInfo))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("User is not an artist.");
        verifyNoInteractions(trackRepository);
    }
}
