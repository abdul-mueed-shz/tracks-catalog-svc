package com.abdul.catalogservice.domain.user.model;

import com.abdul.catalogservice.domain.common.model.BaseInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class UserAliasInfo extends BaseInfo {
    private UserInfo user;
    private String aliasName;
    private String normalizedName;
}
