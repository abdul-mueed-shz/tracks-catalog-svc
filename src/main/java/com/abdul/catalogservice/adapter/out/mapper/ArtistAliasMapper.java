package com.abdul.catalogservice.adapter.out.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.ArtistAlias;
import com.abdul.catalogservice.domain.artist.model.ArtistAliasInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = ArtistMapper.class)
public interface ArtistAliasMapper {
    ArtistAlias toEntity(ArtistAliasInfo dto);

    ArtistAliasInfo toDto(ArtistAlias entity);
}
