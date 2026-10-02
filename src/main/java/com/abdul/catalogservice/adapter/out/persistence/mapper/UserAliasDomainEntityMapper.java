package com.abdul.catalogservice.adapter.out.persistence.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.adapter.out.persistence.entity.UserAlias;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserAliasDomainEntityMapper {
    @Named("aliasToEntity")
    @Mapping(target = "user", source = "user", qualifiedByName = "userReference")
    UserAlias toEntity(UserAliasInfo dto);

    @Mapping(target = "user", expression = "java(user)")
    UserAlias toEntity(UserAliasInfo dto, @Context User user);

    @IterableMapping(qualifiedByName = "aliasToEntity")
    List<UserAlias> toEntityList(List<UserAliasInfo> dtos);

    @Mapping(target = "user", ignore = true)
    UserAliasInfo toDto(UserAlias entity);

    @Named("userReference")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    User toUserReference(UserInfo userInfo);
}
