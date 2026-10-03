package com.abdul.catalogservice.domain.user.usecase;

import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.UpdateUserUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import com.abdul.catalogservice.domain.user.port.out.UserAliasRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final UserAliasRepository userAliasRepository;
    private final UserValidator userValidator;

    @Override
    public UserInfo execute(UUID userId, UserInfo updatedUserInfo) {
        UserInfo existingUserInfo = userRepository.getUserByUuid(userId);
        userValidator.userExists(existingUserInfo);
        UserInfo userInfo = updatedUserInfo.getName() == null
                ? existingUserInfo
                : existingUserInfo.toBuilder().name(updatedUserInfo.getName()).build();
        if (Boolean.TRUE.equals(userInfo.getIsArtist())) {
            UserAliasInfo alias = UserAliasInfo.create(userInfo.getId(), existingUserInfo.getName());
            if (!userAliasRepository.existsByUserIdAndNormalizedName(
                    alias.getUserId(), alias.getNormalizedName())) {
                userAliasRepository.create(alias);
            }
        }
        return userRepository.updateUser(userInfo);
    }
}
