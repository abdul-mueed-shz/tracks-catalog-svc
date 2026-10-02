package com.abdul.catalogservice.domain.user.usecase;

import com.abdul.catalogservice.domain.common.exception.NotFoundException;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.GetUserDetailsUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserDetailsUseCaseImpl implements GetUserDetailsUseCase {
    private final UserRepository userRepository;

    @Override
    public UserInfo execute(Long userId) {
        UserInfo userInfo = userRepository.getUserById(userId);
        if (userInfo == null) {
            throw new NotFoundException("User not found with id: " + userId);
        }
        return userInfo;
    }
}
