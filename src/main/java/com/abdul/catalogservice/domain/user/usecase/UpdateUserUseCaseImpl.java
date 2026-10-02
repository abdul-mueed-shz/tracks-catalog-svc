package com.abdul.catalogservice.domain.user.usecase;

import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.user.mapper.UserInfoMapper;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.in.UpdateUserUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.text.Normalizer;
import java.util.Locale;

@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final UserInfoMapper userInfoMapper;

    @Override
    @Transactional
    public UserInfo execute(Long userId, UserInfo updatedUserInfo) {
        UserInfo existingUserInfo = userRepository.getUserById(userId);
        userValidator.userExists(existingUserInfo);
        UserInfo userInfo = userInfoMapper.update(updatedUserInfo, existingUserInfo);
        if (Boolean.TRUE.equals(userInfo.getIsArtist())) {
            UserAliasInfo userAlias = UserAliasInfo.builder()
                    .aliasName(existingUserInfo.getName())
                    .normalizedName(normalize(existingUserInfo.getName()))
                    .isPrimary(Boolean.FALSE)
                    .user(userInfo)
                    .build();
            userInfo.getAliases().add(userAlias);
        }
        return userRepository.updateUser(userInfo);
    }

    private String normalize(String value) {
        return Normalizer
                .normalize(value.trim()
                        .toLowerCase(Locale.ROOT), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
    }
}
