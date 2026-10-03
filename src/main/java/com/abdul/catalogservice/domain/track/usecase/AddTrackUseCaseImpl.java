package com.abdul.catalogservice.domain.track.usecase;

import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.in.AddTrackUseCase;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class AddTrackUseCaseImpl implements AddTrackUseCase {
    private final TrackRepository trackRepository;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Override
    public TrackInfo execute(UUID userId, TrackInfo trackInfo) {
        UserInfo userInfo = userRepository.getUserByUuid(userId);
        userValidator.userExists(userInfo);
        userValidator.isArtist(userInfo);
        TrackInfo trackWithUser = trackInfo.assignTo(userInfo);
        return trackRepository.save(trackWithUser);
    }
}
