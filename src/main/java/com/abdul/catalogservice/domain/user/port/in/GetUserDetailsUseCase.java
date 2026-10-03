package com.abdul.catalogservice.domain.user.port.in;

import com.abdul.catalogservice.domain.user.model.UserInfo;

import java.util.UUID;

public interface GetUserDetailsUseCase {
    UserInfo execute(UUID userId);
}
