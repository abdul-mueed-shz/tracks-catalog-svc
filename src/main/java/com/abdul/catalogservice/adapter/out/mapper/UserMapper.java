package com.abdul.catalogservice.adapter.out.mapper;

import com.abdul.catalogservice.adapter.utils.mapper.IgnoreIdAndAuditInfoMappings;
import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UserAliasMapper.class)
public interface UserMapper {
    @Mapping(source = "isArtist", target = "artist")
    User toEntity(UserInfo dto);

    @Mapping(source = "artist", target = "isArtist")
    @Mapping(source = "aliases", target = "aliases")
    UserInfo toDto(User entity);

    @IgnoreIdAndAuditInfoMappings
    void updateEntity(UserInfo source, @MappingTarget User target);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserInfo toUpdatedDto(@MappingTarget UserInfo existingDto, UserInfo updatedDto);


    @AfterMapping
    default void assignUserToAliases(@MappingTarget User user) {
        user.getAliases().forEach(alias -> alias.assignUser(user));
    }
}
