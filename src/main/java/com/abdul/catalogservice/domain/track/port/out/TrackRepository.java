package com.abdul.catalogservice.domain.track.port.out;

import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.model.TrackFilterInfo;

public interface TrackRepository {
    PageInfo<TrackInfo> getAll(TrackFilterInfo filterInfo, PaginationInfo paginationInfo, SortInfo sortInfo);
    TrackInfo save(TrackInfo trackInfo);
}
