package com.abdul.catalogservice.adapter.out.persistence.adapter;

import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.out.TrackRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrackRepositoryAdapter implements TrackRepository {
    @Override
    public List<TrackInfo> getAllByUserId(Long userId) {
        // TODO: Implement this method
        return List.of();
    }
}
