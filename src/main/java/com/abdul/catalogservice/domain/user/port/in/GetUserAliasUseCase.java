package com.abdul.catalogservice.domain.user.port.in;

import com.abdul.catalogservice.domain.user.model.UserAliasInfo;

import java.util.List;

public interface GetUserAliasUseCase {
    List<UserAliasInfo> getAll(Long userId);
}
