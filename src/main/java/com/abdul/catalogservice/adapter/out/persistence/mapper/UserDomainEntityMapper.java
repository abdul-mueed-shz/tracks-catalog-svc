package com.abdul.catalogservice.adapter.out.persistence.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = UserAliasDomainEntityMapper.class)
public interface UserDomainEntityMapper {
    @Named("userToEntity")
    User toEntity(UserInfo dto);

    @Mapping(source = "aliases", target = "aliases")
    UserInfo toDto(User entity);

    @Named("userToUpdatedEntity")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toUpdatedEntity(UserInfo source);

}
