package com.abdul.catalogservice.domain.user.usecase;

import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.port.in.CreateUserUseCase;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import com.abdul.catalogservice.domain.user.port.out.UserAliasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {
    private final UserRepository userRepository;
    private final UserAliasRepository userAliasRepository;

    @Override
    @Transactional
    public UserInfo execute(UserInfo userInfo) {
        UserInfo created = userRepository.createUser(userInfo);
        if (Boolean.TRUE.equals(created.getIsArtist())) {
            userAliasRepository.create(UserAliasInfo.create(created.getId(), created.getName()));
        }
        return created;
    }
}
