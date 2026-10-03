package com.abdul.catalogservice.domain.user.port.in;

import com.abdul.catalogservice.domain.user.model.UserInfo;

import java.util.UUID;

public interface UpdateUserUseCase {
    UserInfo execute(UUID userId, UserInfo updatedUserInfo);
}
