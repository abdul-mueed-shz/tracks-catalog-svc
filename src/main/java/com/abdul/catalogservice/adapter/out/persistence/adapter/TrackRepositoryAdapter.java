package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.adapter.out.mapper.TrackMapper;
import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import com.abdul.catalogservice.adapter.out.persistence.pagination.KeysetPaginationSupport;
import com.abdul.catalogservice.adapter.out.persistence.repository.TrackJpaRepository;
import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrackRepositoryAdapter implements TrackRepository {
    private final TrackJpaRepository trackJpaRepository;
    private final TrackMapper trackMapper;
    private final KeysetPaginationSupport keysetPaginationSupport;

    @Override
    public List<TrackInfo> getAllByUserId(Long userId) {
        return trackMapper.toDtoList(trackJpaRepository.findAllByUserId(userId));
    }

    @Override
    public PageInfo<TrackInfo> getAllByUserId(Long userId, PaginationInfo paginationInfo, SortInfo trackSortInfo) {
        return keysetPaginationSupport.execute(
                paginationInfo,
                trackSortInfo,
                (position, limitAndSort) -> trackJpaRepository.findByUserId(
                        userId,
                        position,
                        limitAndSort.limit(),
                        limitAndSort.sort()
                ),
                trackMapper::toDto
        );
    }

    @Override
    public TrackInfo save(TrackInfo trackInfo) {
        var entity = trackMapper.toEntity(trackInfo);
        var savedEntity = trackJpaRepository.save(entity);
        return trackMapper.toDto(savedEntity);
    }
}
