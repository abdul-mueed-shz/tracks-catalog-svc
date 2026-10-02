package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.adapter.out.persistence.entity.ArtistRotation;
import com.abdul.catalogservice.adapter.out.persistence.mapper.ArtistOfTheDayDomainEntityMapper;
import com.abdul.catalogservice.adapter.out.persistence.repository.ArtistOfTheDayJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.repository.ArtistRotationJpaRepository;
import com.abdul.catalogservice.domain.artistofday.model.ArtistOfTheDayInfo;
import com.abdul.catalogservice.domain.artistofday.model.ArtistRotationInfo;
import com.abdul.catalogservice.domain.artistofday.port.out.ArtistOfTheDayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ArtistOfTheDayRepositoryAdapter implements ArtistOfTheDayRepository {
    private static final long ROTATION_ID = 1L;

    private final ArtistOfTheDayJpaRepository artistOfTheDayJpaRepository;
    private final ArtistRotationJpaRepository artistRotationJpaRepository;
    private final ArtistOfTheDayDomainEntityMapper artistOfTheDayDomainEntityMapper;


    @Override
    public ArtistRotationInfo initializeRotation() {
        if (!artistRotationJpaRepository.existsById(ROTATION_ID)) {
            ArtistRotation artistRotation = artistRotationJpaRepository.save(ArtistRotation.builder()
                    .id(ROTATION_ID)
                    .build());
            return artistOfTheDayDomainEntityMapper.toArtistRotationDomain(artistRotation);
        }
        return getArtistRotationInfo();
    }

    @Override
    public ArtistRotationInfo getArtistRotationInfo() {
        Optional<ArtistRotation> artistOptional = artistRotationJpaRepository.findLocked();
        return artistOptional.map(artistOfTheDayDomainEntityMapper::toArtistRotationDomain).orElse(null);
    }

    @Override
    public void updateArtistRotation(ArtistRotationInfo artistRotationInfo) {
        artistRotationJpaRepository.save(
                artistOfTheDayDomainEntityMapper.toArtistRotationEntity(artistRotationInfo)
        );
    }

    @Override
    public ArtistOfTheDayInfo saveArtistOfTheDay(ArtistOfTheDayInfo artistOfTheDayInfo) {
        return artistOfTheDayDomainEntityMapper.toArtistOfTheDayDomain(
                artistOfTheDayJpaRepository.save(
                        artistOfTheDayDomainEntityMapper.toArtistOfTheDayEntity(artistOfTheDayInfo)
                )
        );
    }
}
