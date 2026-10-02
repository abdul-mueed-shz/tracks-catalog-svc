package com.abdul.catalogservice.domain.track.usecase;

import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.model.TrackFilterInfo;
import com.abdul.catalogservice.domain.track.port.in.GetTracksUseCase;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetTracksUseCaseImpl implements GetTracksUseCase {
    private final TrackRepository trackRepository;

    @Override
    public PageInfo<TrackInfo> execute(TrackFilterInfo filterInfo, PaginationInfo paginationInfo, SortInfo trackSortInfo) {
        return trackRepository.getAll(filterInfo, paginationInfo, trackSortInfo);
    }
}
