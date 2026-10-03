package com.abdul.catalogservice.domain.user.usecase;

import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.GetUserAliasUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserAliasRepository;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetUserAliasUseCaseImpl implements GetUserAliasUseCase {
    private final UserRepository userRepository;
    private final UserAliasRepository userAliasRepository;
    private final UserValidator userValidator;

    @Override
    public List<UserAliasInfo> getAll(Long userId) {
        UserInfo userInfo = userRepository.getUserById(userId);
        userValidator.userExists(userInfo);
        if (!Boolean.TRUE.equals(userInfo.getIsArtist())) {
            return List.of();
        }
        return userAliasRepository.getAllByUserId(userId);
    }
}
