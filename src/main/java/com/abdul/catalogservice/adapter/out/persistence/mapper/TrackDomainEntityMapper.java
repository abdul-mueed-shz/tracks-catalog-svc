package com.abdul.catalogservice.adapter.out.persistence.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserDomainEntityMapper.class)
public interface TrackDomainEntityMapper {
    @Mapping(target = "user", qualifiedByName = "userToEntity")
    Track toEntity(TrackInfo dto);

    TrackInfo toDto(Track entity);

    List<TrackInfo> toDtoList(List<Track> entity);
}
