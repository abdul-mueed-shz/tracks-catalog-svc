package com.abdul.catalogservice.adapter.out.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.Artist;
import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ArtistMapper {
    Artist toEntity(ArtistInfo dto);

    ArtistInfo toDto(Artist entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ArtistInfo toUpdatedDto(@MappingTarget ArtistInfo existingDto, ArtistInfo updatedDto);
}
