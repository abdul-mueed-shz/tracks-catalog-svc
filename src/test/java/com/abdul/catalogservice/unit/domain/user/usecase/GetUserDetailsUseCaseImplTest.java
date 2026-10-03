package com.abdul.catalogservice.unit.domain.user.usecase;

import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import com.abdul.catalogservice.domain.user.usecase.GetUserDetailsUseCaseImpl;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class GetUserDetailsUseCaseImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserValidator userValidator = mock(UserValidator.class);
    private final GetUserDetailsUseCaseImpl useCase =
            new GetUserDetailsUseCaseImpl(userRepository, userValidator);

    @Test
    void returnsUserDetailsForUserId() {
        UUID userId = UUID.randomUUID();
        UserInfo userInfo = UserInfo.builder()
                .id(1L)
                .uuid(userId)
                .name("User")
                .build();
        when(userRepository.getUserByUuid(userId)).thenReturn(userInfo);

        UserInfo result = useCase.execute(userId);

        assertThat(result).isSameAs(userInfo);
        verify(userRepository).getUserByUuid(userId);
    }

    @Test
    void rejectsMissingUser() {
        UUID userId = UUID.randomUUID();
        when(userRepository.getUserByUuid(userId)).thenReturn(null);
        UserValidator validator = new UserValidator();
        GetUserDetailsUseCaseImpl validatingUseCase =
                new GetUserDetailsUseCaseImpl(userRepository, validator);

        assertThatThrownBy(() -> validatingUseCase.execute(userId))
                .isInstanceOf(com.abdul.catalogservice.domain.common.exception.NotFoundException.class)
                .hasMessage("User not found.");
    }
}
