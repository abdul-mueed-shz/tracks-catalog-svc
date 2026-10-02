package com.abdul.catalogservice.domain.user.mapper;

import com.abdul.catalogservice.domain.user.model.UserInfo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserInfoMapper {

    @BeanMapping(
            ignoreByDefault = true,
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "name", source = "name")
    UserInfo update(UserInfo updatedUserInfo, @Context UserInfo existingUserInfo);

    @ObjectFactory
    default UserInfo.UserInfoBuilder<?, ?> existingUserInfoBuilder(@Context UserInfo existingUserInfo) {
        return existingUserInfo.toBuilder();
    }
}
