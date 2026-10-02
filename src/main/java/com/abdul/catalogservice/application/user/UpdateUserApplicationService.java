package com.abdul.catalogservice.application.user;

import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.UpdateUserUseCase;
import com.abdul.catalogservice.domain.user.usecase.UpdateUserUseCaseImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Primary
@RequiredArgsConstructor
public class UpdateUserApplicationService implements UpdateUserUseCase {
    private final UpdateUserUseCaseImpl updateUserUseCase;

    @Override
    @Transactional
    public UserInfo execute(Long userId, UserInfo updatedUserInfo) {
        return updateUserUseCase.execute(userId, updatedUserInfo);
    }
}
