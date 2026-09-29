package com.abdul.catalogservice.domain.user.port.in;

import com.abdul.catalogservice.domain.user.model.UserInfo;

public interface CreateUserUseCase {
    UserInfo execute(UserInfo userInfo);
}
