package com.abdul.catalogservice.domain.artistofday.usecase;

import com.abdul.catalogservice.domain.artistofday.model.ArtistOfTheDayInfo;
import com.abdul.catalogservice.domain.artistofday.model.ArtistRotationInfo;
import com.abdul.catalogservice.domain.artistofday.port.in.GetArtistOfTheDayUseCase;
import com.abdul.catalogservice.domain.artistofday.port.out.ArtistOfTheDayRepository;
import com.abdul.catalogservice.domain.common.exception.NotFoundException;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.ZoneOffset;

@RequiredArgsConstructor
public class GetArtistOfTheDayUseCaseImpl implements GetArtistOfTheDayUseCase {
    private final ArtistOfTheDayRepository artistOfTheDayRepository;
    private final UserRepository userRepository;

    @Override
    public UserInfo execute() {
        LocalDate currentDay = LocalDate.now(ZoneOffset.UTC);
        ArtistOfTheDayInfo existingAssignment = findAssignment(currentDay);
//        if (existingAssignment != null) {
//            return existingAssignment.getArtist();
//        }

        ArtistRotationInfo artistRotationInfo = artistOfTheDayRepository.getArtistRotationInfo();
        if (artistRotationInfo == null) {
            artistRotationInfo = artistOfTheDayRepository.initializeRotation();
        }
//        existingAssignment = findAssignment(currentDay);
//        if (existingAssignment != null) {
//            return existingAssignment.getArtist();
//        }

        Long artistId = artistRotationInfo.getLastArtistId();
        UserInfo artistOfTheDay = findArtist(artistId);
        ArtistOfTheDayInfo artistOfTheDayInfo = ArtistOfTheDayInfo.builder()
                .day(currentDay)
                .artist(artistOfTheDay)
                .build();
        artistRotationInfo.advanceTo(artistOfTheDay.getId());
        artistOfTheDayRepository.updateArtistRotation(artistRotationInfo);
        ArtistOfTheDayInfo saveArtistOfTheDay = artistOfTheDayRepository.saveArtistOfTheDay(artistOfTheDayInfo);
        return saveArtistOfTheDay.getArtist();
    }

    private ArtistOfTheDayInfo findAssignment(LocalDate day) {
        return artistOfTheDayRepository.getArtistOfTheDay(day);
    }

    private UserInfo findArtist(Long lastArtistId) {
        if (lastArtistId != null) {
            return findNextArtist(lastArtistId);
        }
        UserInfo userInfo = userRepository.findFirstArtistUser();
        checkArtistExist(userInfo);
        return userInfo;
    }

    private UserInfo findNextArtist(Long lastArtistId) {
        UserInfo userInfo = userRepository.findArtistUserAfterArtistId(lastArtistId);
        if (userInfo == null) {
            userInfo = userRepository.findFirstArtistUser();
        }
        checkArtistExist(userInfo);
        return userInfo;
    }

    private void checkArtistExist(UserInfo userInfo) {
        if (userInfo == null) {
            throw new NotFoundException("No artist found for the day");
        }
    }
}
