package com.abdul.catalogservice.adapter.out.persistence.mapper;

import com.abdul.catalogservice.adapter.out.persistence.entity.ArtistOfTheDay;
import com.abdul.catalogservice.adapter.out.persistence.entity.ArtistRotation;
import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artistofday.model.ArtistOfTheDayInfo;
import com.abdul.catalogservice.domain.artistofday.model.ArtistRotationInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtistOfTheDayDomainEntityMapper {
    ArtistOfTheDay toArtistOfTheDayEntity(ArtistOfTheDayInfo dto);

    ArtistOfTheDayInfo toArtistOfTheDayDomain(ArtistOfTheDay entity);

    ArtistRotationInfo toArtistRotationDomain(ArtistRotation entity);

    ArtistRotation toArtistRotationEntity(ArtistRotationInfo domain);

    default User toUser(ArtistInfo artistInfo) {
        if (artistInfo == null) {
            return null;
        }
        return User.builder()
                .id(artistInfo.id())
                .name(artistInfo.name())
                .isArtist(true)
                .build();
    }

    default ArtistInfo toArtistInfo(User user) {
        if (user == null) {
            return null;
        }
        return new ArtistInfo(user.getId(), user.getName());
    }
}
