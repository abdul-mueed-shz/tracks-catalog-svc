package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.adapter.out.persistence.mapper.UserDomainEntityMapper;
import com.abdul.catalogservice.adapter.out.persistence.repository.UserJpaRepository;
import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artistofday.port.out.ArtistCatalog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ArtistCatalogAdapter implements ArtistCatalog {
    private final UserJpaRepository userJpaRepository;
    private final UserDomainEntityMapper mapper;

    @Override
    public ArtistInfo findFirstArtist() {
        return userJpaRepository.findFirstByIsArtistTrueOrderByIdAsc()
                .map(mapper::toArtistInfo)
                .orElse(null);
    }

    @Override
    public ArtistInfo findArtistAfterId(Long artistId) {
        return userJpaRepository
                .findFirstByIsArtistTrueAndIdGreaterThanOrderByIdAsc(artistId)
                .map(mapper::toArtistInfo)
                .orElse(null);
    }
}
