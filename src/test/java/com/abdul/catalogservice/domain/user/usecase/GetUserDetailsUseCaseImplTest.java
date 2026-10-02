package com.abdul.catalogservice.domain.user.usecase;

import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class GetUserDetailsUseCaseImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserValidator userValidator = mock(UserValidator.class);
    private final GetUserDetailsUseCaseImpl useCase =
            new GetUserDetailsUseCaseImpl(userRepository, userValidator);

    @Test
    void returnsUserDetailsForUserId() {
        Long userId = 1L;
        UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .name("User")
                .build();
        when(userRepository.getUserById(userId)).thenReturn(userInfo);

        UserInfo result = useCase.execute(userId);

        assertThat(result).isSameAs(userInfo);
        verify(userRepository).getUserById(userId);
    }
}
