package com.abdul.catalogservice.adapter.out.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.UserAlias;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface UserAliasMapper {
    UserAlias toEntity(UserAliasInfo dto);

    UserAliasInfo toDto(UserAlias entity);
}
