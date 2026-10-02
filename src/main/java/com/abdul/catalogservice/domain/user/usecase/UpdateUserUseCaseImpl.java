package com.abdul.catalogservice.domain.user.usecase;

import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.user.mapper.UserInfoMapper;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.UpdateUserUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import com.abdul.catalogservice.domain.user.port.out.UserAliasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final UserAliasRepository userAliasRepository;
    private final UserValidator userValidator;
    private final UserInfoMapper userInfoMapper;

    @Override
    @Transactional
    public UserInfo execute(Long userId, UserInfo updatedUserInfo) {
        UserInfo existingUserInfo = userRepository.getUserById(userId);
        userValidator.userExists(existingUserInfo);
        UserInfo userInfo = userInfoMapper.update(updatedUserInfo, existingUserInfo);
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
