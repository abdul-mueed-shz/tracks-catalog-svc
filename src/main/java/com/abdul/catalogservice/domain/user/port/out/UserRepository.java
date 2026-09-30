package com.abdul.catalogservice.domain.user.port.out;

import com.abdul.catalogservice.domain.user.model.UserInfo;

public interface UserRepository {
    UserInfo getUserById(Long id);

    UserInfo createUser(UserInfo userInfo);

    UserInfo updateUser(UserInfo userInfo);
}
