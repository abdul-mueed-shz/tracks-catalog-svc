package com.abdul.catalogservice.adapter.in.web.mapper;

import com.abdul.catalogservice.adapter.in.web.dto.*;
import com.abdul.catalogservice.adapter.utils.IgnoreIdAndAuditInfoMappings;
import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.user.model.UserAliasInfo;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoToDomainMapper {
    @IgnoreIdAndAuditInfoMappings
    @Mapping(target = "name", source = "name", qualifiedByName = "trimName")
    UserInfo registerUserRequestToUserInfo(RegisterUserRequest registerUserRequest);

    @IgnoreIdAndAuditInfoMappings
    @Mapping(target = "isArtist", ignore = true)
    @Mapping(target = "name", source = "name", qualifiedByName = "trimName")
    UserInfo updateUserRequestToUserInfo(UpdateUserRequest updateUserRequest);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "uuid", source = "uuid")
    UserResponse toUserResponse(UserInfo userInfo);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "uuid", source = "uuid")
    UserSummaryResponse toUserSummaryResponse(UserInfo userInfo);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "uuid", source = "uuid")
    UserAliasResponse toUserAliasResponse(UserAliasInfo userAliasInfo);

    List<UserAliasResponse> toUserAliasResponseList(List<UserAliasInfo> userAliasInfoList);

    @Mapping(target = "id", source = "uuid")
    @Mapping(target = "uuid", source = "uuid")
    ArtistResponse toArtistResponse(ArtistInfo artistInfo);

    @Named("trimName")
    default String trimName(String name) {
        return name == null ? null : name.trim();
    }
}
