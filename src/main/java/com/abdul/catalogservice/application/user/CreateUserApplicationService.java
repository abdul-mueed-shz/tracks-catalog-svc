package com.abdul.catalogservice.application.user;

import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.CreateUserUseCase;
import com.abdul.catalogservice.domain.user.usecase.CreateUserUseCaseImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@RequiredArgsConstructor
public class CreateUserApplicationService implements CreateUserUseCase {
    private final CreateUserUseCaseImpl createUserUseCase;

    @Override
    @Transactional
    public UserInfo execute(UserInfo userInfo) {
        return createUserUseCase.execute(userInfo);
    }
}
