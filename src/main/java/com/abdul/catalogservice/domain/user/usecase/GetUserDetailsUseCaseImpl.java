package com.abdul.catalogservice.domain.user.usecase;

import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.GetUserDetailsUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserDetailsUseCaseImpl implements GetUserDetailsUseCase {
    private final UserRepository userRepository;

    @Override
    public UserInfo execute(Long userId) {
        return userRepository.getUserById(userId);
    }
}
