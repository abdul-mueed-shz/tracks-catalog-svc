package com.abdul.catalogservice.domain.user.port.out;

import com.abdul.catalogservice.domain.user.model.UserAliasInfo;

public interface UserAliasRepository {
    boolean existsByUserIdAndNormalizedName(Long userId, String normalizedName);

    UserAliasInfo create(UserAliasInfo aliasInfo);
}
