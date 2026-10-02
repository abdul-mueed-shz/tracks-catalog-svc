package com.abdul.catalogservice.adapter.in.web.mapper;

import com.abdul.catalogservice.adapter.in.web.dto.RegisterUserRequest;
import com.abdul.catalogservice.adapter.in.web.dto.UpdateUserRequest;
import com.abdul.catalogservice.adapter.utils.IgnoreIdAndAuditInfoMappings;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface UserDtoToDomainMapper {
    @IgnoreIdAndAuditInfoMappings
    @Mapping(target = "name", source = "name", qualifiedByName = "trimName")
    UserInfo registerUserRequestToUserInfo(RegisterUserRequest registerUserRequest);

    @IgnoreIdAndAuditInfoMappings
    @Mapping(target = "isArtist", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "trimName")
    UserInfo updateUserRequestToUserInfo(UpdateUserRequest updateUserRequest);

    @Named("trimName")
    default String trimName(String name) {
        return name == null ? null : name.trim();
    }
}
