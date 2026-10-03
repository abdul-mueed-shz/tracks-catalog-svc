package com.abdul.catalogservice.domain.user.port.in;

import com.abdul.catalogservice.domain.user.model.UserAliasInfo;

import java.util.List;
import java.util.UUID;

public interface GetUserAliasUseCase {
    List<UserAliasInfo> getAll(UUID userId);
}
