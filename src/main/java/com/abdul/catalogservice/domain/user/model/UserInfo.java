package com.abdul.catalogservice.domain.user.model;

import com.abdul.catalogservice.domain.common.model.BaseInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.ArrayList;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class UserInfo extends BaseInfo {
    private String name;
    @Builder.Default
    private Boolean isArtist = false;
    @Builder.Default
    private List<UserAliasInfo> aliases = new ArrayList<>();
}
