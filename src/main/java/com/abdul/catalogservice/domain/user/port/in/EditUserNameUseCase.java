package com.abdul.catalogservice.domain.user.port.in;

import com.abdul.catalogservice.domain.user.model.UserInfo;

public interface EditUserNameUseCase {
    UserInfo execute(Long userId, String newName);
}
