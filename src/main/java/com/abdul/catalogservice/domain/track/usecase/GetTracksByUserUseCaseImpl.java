package com.abdul.catalogservice.domain.track.usecase;

import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.model.TrackFilterInfo;
import com.abdul.catalogservice.domain.track.port.in.GetTracksByUserUseCase;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import lombok.RequiredArgsConstructor;

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
    public PageInfo<TrackInfo> execute(TrackFilterInfo filterInfo, PaginationInfo paginationInfo, SortInfo trackSortInfo) {
        return trackRepository.getAll(filterInfo, paginationInfo, trackSortInfo);
    }
}
