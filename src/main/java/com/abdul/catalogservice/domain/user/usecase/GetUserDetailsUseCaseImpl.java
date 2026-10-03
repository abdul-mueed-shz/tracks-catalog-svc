package com.abdul.catalogservice.domain.user.usecase;

import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.GetUserDetailsUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class GetUserDetailsUseCaseImpl implements GetUserDetailsUseCase {
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Override
    public UserInfo execute(UUID userId) {
        UserInfo userInfo = userRepository.getUserByUuid(userId);
        userValidator.userExists(userInfo);
        return userInfo;
    }
}
