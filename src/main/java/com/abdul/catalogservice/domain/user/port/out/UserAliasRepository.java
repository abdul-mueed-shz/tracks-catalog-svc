package com.abdul.catalogservice.domain.user.port.out;

import com.abdul.catalogservice.domain.user.model.UserAliasInfo;

import java.util.List;

public interface UserAliasRepository {
    boolean existsByUserIdAndNormalizedName(Long userId, String normalizedName);

    UserAliasInfo create(UserAliasInfo aliasInfo);

    List<UserAliasInfo> getAllByUserId(Long userId);
}
