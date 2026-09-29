package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.adapter.out.mapper.ArtistMapper;
import com.abdul.catalogservice.adapter.out.persistence.entity.Artist;
import com.abdul.catalogservice.adapter.out.persistence.repository.ArtistJpaRepository;
import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artist.port.out.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArtistRepositoryAdapter implements ArtistRepository {
    private final ArtistJpaRepository artistJpaRepository;
    private final ArtistMapper artistMapper;

    @Override
    public ArtistInfo getArtistById(Long id) {
        Optional<Artist> timeSheetOptional =
                artistJpaRepository.findById(id);
        return timeSheetOptional.map(artistMapper::toDto).orElse(null);
    }

    @Override
    public ArtistInfo upsertArtist(ArtistInfo artistInfo) {
        return artistMapper.toDto(artistJpaRepository.save(artistMapper.toEntity(artistInfo)));
    }
}
