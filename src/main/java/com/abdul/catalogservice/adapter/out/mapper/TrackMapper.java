package com.abdul.catalogservice.adapter.out.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ArtistMapper.class)
public interface TrackMapper {
    Track toEntity(TrackInfo dto);

    TrackInfo toDto(Track entity);
}
