package com.abdul.catalogservice.adapter.out.persistence.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.adapter.out.persistence.entity.UserAlias;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import org.mapstruct.*;

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

    @ObjectFactory
    default User.UserBuilder<?, ?> existingUserBuilder(@Context User existingUser) {
        return existingUser.toBuilder();
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    UserInfo toUpdatedDto(@MappingTarget UserInfo existingDto, UserInfo updatedDto);

    @AfterMapping
    default void assignUserToAliases(@MappingTarget User user) {
        user.getAliases().forEach(alias -> alias.assignUser(user));
    }

    @AfterMapping
    default void mergeAliases(
            UserAliasInfo newAlias,
            @MappingTarget User user
    ) {
        if (newAlias == null
                || newAlias.getNormalizedName() == null
                || newAlias.getNormalizedName().isBlank()) {
            assignUserToAliases(user);
            return;
        }

        boolean aliasExists = user.getAliases().stream()
                .anyMatch(alias -> newAlias.getNormalizedName().equals(alias.getNormalizedName()));
        if (!aliasExists) {
            user.getAliases().add(toEntity(newAlias, user));
        }
        assignUserToAliases(user);
    }

    @Mapping(target = "user", ignore = true)
    UserAlias toEntity(UserAliasInfo alias, @Context User user);
}
