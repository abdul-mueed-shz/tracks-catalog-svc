package com.abdul.catalogservice.unit.domain.user.usecase;

import com.abdul.catalogservice.domain.common.exception.NotFoundException;
import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.user.mapper.UserInfoMapper;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import com.abdul.catalogservice.domain.user.usecase.UpdateUserUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdateUserUseCaseImplTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserInfoMapper userInfoMapper = mock(UserInfoMapper.class);
    private final UpdateUserUseCaseImpl useCase =
            new UpdateUserUseCaseImpl(userRepository, new UserValidator(), userInfoMapper);

    @Test
    void updatesArtistAndAddsNormalizedExistingNameAlias() {
        UserInfo existing = UserInfo.builder().id(1L).name(" Beyoncé ").isArtist(false).build();
        UserInfo update = UserInfo.builder().isArtist(true).build();
        UserInfo mapped = UserInfo.builder().id(1L).name(" Beyoncé ").isArtist(true).build();
        UserInfo saved = mapped;
        when(userRepository.getUserById(1L)).thenReturn(existing);
        when(userInfoMapper.update(update, existing)).thenReturn(mapped);
        when(userRepository.updateUser(any(UserInfo.class))).thenReturn(saved);

        assertThat(useCase.execute(1L, update)).isSameAs(saved);

        verify(userRepository).updateUser(argThat(user -> {
            UserAliasInfo alias = user.getAliases().get(0);
            return alias.getAliasName().equals(" Beyoncé ")
                    && alias.getNormalizedName().equals("beyonce")
                    && alias.getUser() == mapped;
        }));
    }

    @Test
    void updatesNonArtistWithoutAddingAlias() {
        UserAliasInfo existingAlias = UserAliasInfo.builder()
                .aliasName("existing")
                .normalizedName("existing")
                .build();
        UserInfo existing = UserInfo.builder()
                .id(1L)
                .name("User")
                .aliases(List.of(existingAlias))
                .build();
        UserInfo update = UserInfo.builder().isArtist(false).build();
        UserInfo mapped = UserInfo.builder().id(1L).name("User").isArtist(false).build();
        when(userRepository.getUserById(1L)).thenReturn(existing);
        when(userInfoMapper.update(update, existing)).thenReturn(mapped);

        useCase.execute(1L, update);

        verify(userRepository).updateUser(argThat(user -> user.getAliases().isEmpty()));
    }

    @Test
    void rejectsUpdateForMissingUser() {
        when(userRepository.getUserById(1L)).thenReturn(null);

        assertThatThrownBy(() -> useCase.execute(1L, UserInfo.builder().build()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found.");
        verifyNoInteractions(userInfoMapper);
        verify(userRepository, never()).updateUser(any());
    }
}
