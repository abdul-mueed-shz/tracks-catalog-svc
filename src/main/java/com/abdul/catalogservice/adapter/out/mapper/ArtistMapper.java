package com.abdul.catalogservice.adapter.out.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.Artist;
import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
    Artist toEntity(ArtistInfo dto);

    ArtistInfo toDto(Artist entity);
}
