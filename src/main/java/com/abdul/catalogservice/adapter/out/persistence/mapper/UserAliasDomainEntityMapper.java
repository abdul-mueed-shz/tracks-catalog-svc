package com.abdul.catalogservice.adapter.out.persistence.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.adapter.out.persistence.entity.UserAlias;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserAliasDomainEntityMapper {
    @Mapping(target = "user", expression = "java(user)")
    UserAlias toEntity(UserAliasInfo dto, @Context User user);

    @Mapping(target = "userId", source = "user.id")
    UserAliasInfo toDto(UserAlias entity);

}
