package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.adapter.out.mapper.TrackMapper;
import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import com.abdul.catalogservice.adapter.out.persistence.pagination.CursorPaginationSupport;
import com.abdul.catalogservice.adapter.out.persistence.repository.TrackJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.specification.TrackSpecification;
import com.abdul.catalogservice.domain.common.enums.SortProperty;
import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.model.TrackFilterInfo;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrackRepositoryAdapter implements TrackRepository {
    private final TrackJpaRepository trackJpaRepository;
    private final TrackMapper trackMapper;
    private final TrackSpecification trackSpecification;
    private final CursorPaginationSupport cursorPaginationSupport;

    @Override
    public List<TrackInfo> getAllByUserId(Long userId) {
        return trackMapper.toDtoList(trackJpaRepository.findAllByUserId(userId));
    }

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
                trackMapper::toDto,
                track -> SortProperty.CREATED_AT.getProperty().equals(trackSortInfo.getProperty())
                        ? track.getCreatedAt()
                        : track.getUpdatedAt(),
                Track::getId
        );
    }

    @Override
    public TrackInfo save(TrackInfo trackInfo) {
        var entity = trackMapper.toEntity(trackInfo);
        var savedEntity = trackJpaRepository.save(entity);
        return trackMapper.toDto(savedEntity);
    }
}
