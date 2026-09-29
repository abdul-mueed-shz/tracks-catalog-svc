package com.abdul.catalogservice.domain.user.model;

import com.abdul.catalogservice.domain.user.common.model.BaseInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class UserInfo extends BaseInfo {
    private String name;
}
