package com.abdul.catalogservice.adapter.out.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapper.class)
public interface TrackMapper {
    Track toEntity(TrackInfo dto);

    TrackInfo toDto(Track entity);

    List<TrackInfo> toDtoList(List<Track> entity);
}
