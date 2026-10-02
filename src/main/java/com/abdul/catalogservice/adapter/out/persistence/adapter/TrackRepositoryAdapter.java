package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import com.abdul.catalogservice.adapter.out.persistence.mapper.TrackDomainEntityMapper;
import com.abdul.catalogservice.adapter.out.persistence.repository.TrackJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.specification.TrackSpecification;
import com.abdul.catalogservice.adapter.out.persistence.utils.pagination.CursorPaginationSupport;
import com.abdul.catalogservice.domain.common.enums.SortProperty;
import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.track.model.TrackFilterInfo;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TrackRepositoryAdapter implements TrackRepository {
    private final TrackJpaRepository trackJpaRepository;
    private final TrackDomainEntityMapper trackDomainEntityMapper;
    private final TrackSpecification trackSpecification;
    private final CursorPaginationSupport cursorPaginationSupport;

    @Override
    public PageInfo<TrackInfo> getAll(TrackFilterInfo filterInfo, PaginationInfo paginationInfo, SortInfo trackSortInfo) {
        return cursorPaginationSupport.execute(
                paginationInfo,
                trackSortInfo,
                (position, query) -> {
                    Specification<Track> specification = trackSpecification.filterBy(
                            filterInfo, position, query.sort()
                    );
                    return trackJpaRepository.findAll(specification, query.sort(), query.limit().max());
                },
                trackDomainEntityMapper::toDto,
                track -> SortProperty.CREATED_AT.getProperty().equals(trackSortInfo.getProperty())
                        ? track.getCreatedAt()
                        : track.getUpdatedAt(),
                Track::getId
        );
    }

    @Override
    public TrackInfo save(TrackInfo trackInfo) {
        var entity = trackDomainEntityMapper.toEntity(trackInfo);
        var savedEntity = trackJpaRepository.save(entity);
        return trackDomainEntityMapper.toDto(savedEntity);
    }
}
