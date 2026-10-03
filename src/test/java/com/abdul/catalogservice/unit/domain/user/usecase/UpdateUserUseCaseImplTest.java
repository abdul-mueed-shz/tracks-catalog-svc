package com.abdul.catalogservice.unit.domain.user.usecase;

import com.abdul.catalogservice.domain.common.exception.NotFoundException;
import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import com.abdul.catalogservice.domain.user.usecase.UpdateUserUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateUserUseCaseImplTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final com.abdul.catalogservice.domain.user.port.out.UserAliasRepository userAliasRepository =
            mock(com.abdul.catalogservice.domain.user.port.out.UserAliasRepository.class);
    private final UpdateUserUseCaseImpl useCase =
            new UpdateUserUseCaseImpl(userRepository, userAliasRepository, new UserValidator());

    @Test
    void updatesArtistAndAddsNormalizedExistingNameAlias() {
        UUID userId = UUID.randomUUID();
        UserInfo existing = UserInfo.builder().id(1L).uuid(userId).name(" Beyoncé ").isArtist(true).build();
        UserInfo update = UserInfo.builder().name("New Name").build();
        UserInfo saved = UserInfo.builder().id(1L).uuid(userId).name("New Name").isArtist(true).build();
        when(userRepository.getUserByUuid(userId)).thenReturn(existing);
        when(userAliasRepository.existsByUserIdAndNormalizedName(1L, "beyonce")).thenReturn(false);
        when(userRepository.updateUser(any(UserInfo.class))).thenReturn(saved);

        assertThat(useCase.execute(userId, update)).isSameAs(saved);

        verify(userAliasRepository).create(argThat(alias ->
                alias.getAliasName().equals("Beyoncé")
                        && alias.getNormalizedName().equals("beyonce")
                        && alias.getUserId().equals(1L)));
    }

    @Test
    void updatesNonArtistWithoutAddingAlias() {
        UUID userId = UUID.randomUUID();
        UserInfo existing = UserInfo.builder()
                .id(1L)
                .uuid(userId)
                .name("User")
                .build();
        UserInfo update = UserInfo.builder().isArtist(false).build();
        when(userRepository.getUserByUuid(userId)).thenReturn(existing);

        useCase.execute(userId, update);

        verify(userAliasRepository, never()).create(any());
    }

    @Test
    void rejectsUpdateForMissingUser() {
        UUID userId = UUID.randomUUID();
        when(userRepository.getUserByUuid(userId)).thenReturn(null);

        assertThatThrownBy(() -> useCase.execute(userId, UserInfo.builder().build()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found.");
        verify(userRepository, never()).updateUser(any());
    }
}
