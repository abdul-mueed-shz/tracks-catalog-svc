package com.abdul.catalogservice.adapter.out.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "isArtist", target = "artist")
    User toEntity(UserInfo dto);

    @Mapping(source = "artist", target = "isArtist")
    UserInfo toDto(User entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserInfo toUpdatedDto(@MappingTarget UserInfo existingDto, UserInfo updatedDto);
}
