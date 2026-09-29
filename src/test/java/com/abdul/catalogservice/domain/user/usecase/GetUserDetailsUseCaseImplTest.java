package com.abdul.catalogservice.domain.user.usecase;

import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class GetUserDetailsUseCaseImplTest {

    private final UserRepository userRepository = mock(UserRepository.class);
    private final GetUserDetailsUseCaseImpl useCase =
            new GetUserDetailsUseCaseImpl(userRepository);

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
