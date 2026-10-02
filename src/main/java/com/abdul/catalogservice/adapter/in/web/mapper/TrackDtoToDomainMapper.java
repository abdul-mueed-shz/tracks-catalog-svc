package com.abdul.catalogservice.adapter.in.web.mapper;

import com.abdul.catalogservice.adapter.in.web.dto.AddTrackDto;
import com.abdul.catalogservice.adapter.utils.IgnoreIdAndAuditInfoMappings;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = UserDtoToDomainMapper.class)
public interface TrackDtoToDomainMapper {
    @IgnoreIdAndAuditInfoMappings
    @Mapping(target = "user", ignore = true)
    TrackInfo trackDtoToTrackInfo(AddTrackDto trackDto);
}
