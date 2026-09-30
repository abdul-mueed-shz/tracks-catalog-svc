package com.abdul.catalogservice.adapter.out.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.UserAlias;
import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserAliasMapper {
    @Mapping(target = "user", ignore = true)
    UserAlias toEntity(UserAliasInfo dto);

    @Mapping(target = "user", ignore = true)
    UserAlias toEntity(UserAliasInfo dto, @Context User user);

    @Mapping(target = "user", ignore = true)
    UserAliasInfo toDto(UserAlias entity);


    @AfterMapping
    default void assignUser(@MappingTarget UserAlias alias, @Context User user) {
        alias.assignUser(user);
    }
}
