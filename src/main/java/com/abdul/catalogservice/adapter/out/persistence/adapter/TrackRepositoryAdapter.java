package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.adapter.out.mapper.TrackMapper;
import com.abdul.catalogservice.adapter.out.persistence.entity.Track;
import com.abdul.catalogservice.adapter.out.persistence.repository.TrackJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.utils.CursorCodec;
import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Window;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TrackRepositoryAdapter implements TrackRepository {
    private final TrackJpaRepository trackJpaRepository;
    private final TrackMapper trackMapper;
    private final CursorCodec cursorCodec;

    @Override
    public List<TrackInfo> getAllByUserId(Long userId) {
        return trackMapper.toDtoList(trackJpaRepository.findAllByUserId(userId));
    }

    @Override
    public PageInfo<TrackInfo> getAllByUserId(Long userId, String cursor, int size) {
        Window<Track> window =
                trackJpaRepository.findByUserIdOrderByIdDesc(userId, cursorCodec.decodeCursor(cursor), Limit.of(size));
        String nextCursor = window.hasNext()
                ? cursorCodec.encodeCursor(window.positionAt(window.size() - 1))
                : null;
        return PageInfo.<TrackInfo>builder()
                .cursor(nextCursor)
                .size(size)
                .data(trackMapper.toDtoList(window.getContent()))
                .build();
    }

    @Override
    public TrackInfo save(TrackInfo trackInfo) {
        var entity = trackMapper.toEntity(trackInfo);
        var savedEntity = trackJpaRepository.save(entity);
        return trackMapper.toDto(savedEntity);
    }
}
