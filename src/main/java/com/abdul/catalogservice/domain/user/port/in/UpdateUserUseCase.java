package com.abdul.catalogservice.domain.user.port.in;

import com.abdul.catalogservice.domain.user.model.UserInfo;

public interface UpdateUserUseCase {
    UserInfo execute(Long userId, UserInfo updatedUserInfo);
}
