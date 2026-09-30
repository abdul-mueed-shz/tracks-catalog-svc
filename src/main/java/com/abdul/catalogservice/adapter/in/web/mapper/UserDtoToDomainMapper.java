package com.abdul.catalogservice.adapter.in.web.mapper;

import com.abdul.catalogservice.adapter.in.web.dto.RegisterUserRequest;
import com.abdul.catalogservice.adapter.utils.mapper.IgnoreIdAndAuditInfoMappings;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserDtoToDomainMapper {
    @IgnoreIdAndAuditInfoMappings
    @Mapping(target = "aliases", ignore = true)
    UserInfo registerUserRequestToUserInfo(RegisterUserRequest registerUserRequest);
}
