package com.abdul.catalogservice.unit.domain.user.usecase;

import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserAliasRepository;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import com.abdul.catalogservice.domain.user.usecase.CreateUserUseCaseImpl;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class CreateUserUseCaseImplTest {
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserAliasRepository userAliasRepository = mock(UserAliasRepository.class);
    private final CreateUserUseCaseImpl useCase = new CreateUserUseCaseImpl(userRepository, userAliasRepository);

    @Test
    void createsUserThroughRepository() {
        UserInfo request = UserInfo.builder().name("User").build();
        UserInfo created = UserInfo.builder().id(1L).name("User").build();
        when(userRepository.createUser(request)).thenReturn(created);

        assertThat(useCase.execute(request)).isSameAs(created);
        verify(userRepository).createUser(request);
    }
}
