package com.abdul.catalogservice.domain.track.port.in;

import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.model.TrackFilterInfo;

import java.util.List;

public interface GetTracksByUserUseCase {
    List<TrackInfo> execute(Long userId);

    PageInfo<TrackInfo> execute(TrackFilterInfo filterInfo, PaginationInfo paginationInfo, SortInfo sortInfo);
}
