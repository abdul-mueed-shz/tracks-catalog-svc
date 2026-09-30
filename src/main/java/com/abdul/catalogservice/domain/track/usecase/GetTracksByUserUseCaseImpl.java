package com.abdul.catalogservice.domain.track.usecase;

import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.in.GetTracksByUserUseCase;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Window;

import java.util.List;

@RequiredArgsConstructor
public class GetTracksByUserUseCaseImpl implements GetTracksByUserUseCase {
    private final TrackRepository trackRepository;
    private final UserRepository userRepository;
    private final UserValidator userValidator;

    @Override
    public List<TrackInfo> execute(Long userId) {
        UserInfo userInfo = userRepository.getUserById(userId);
        userValidator.isArtist(userInfo);
        return trackRepository.getAllByUserId(userId);
    }

    @Override
    public PageInfo<TrackInfo> execute(Long userId, String cursor, int size) {
        UserInfo userInfo = userRepository.getUserById(userId);
        userValidator.isArtist(userInfo);
        return trackRepository.getAllByUserId(userId, cursor, size);
    }
}
