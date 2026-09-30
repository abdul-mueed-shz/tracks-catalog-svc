package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.adapter.in.web.dto.AddTrackDto;
import com.abdul.catalogservice.adapter.in.web.mapper.TrackDtoToDomainMapper;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.in.AddTrackUseCase;
import com.abdul.catalogservice.domain.track.port.in.GetTracksByUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {
    private final TrackDtoToDomainMapper trackDtoToDomainMapper;
    private final GetTracksByUserUseCase getTracksByUserUseCase;
    private final AddTrackUseCase addTrackUseCase;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrackInfo>> getTracksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(getTracksByUserUseCase.execute(userId));
    }

    @PostMapping("/user/{userId}/add")
    public ResponseEntity<TrackInfo> addTrack(@PathVariable Long userId, @Valid @RequestBody AddTrackDto addTrackDto) {
        TrackInfo trackInfo = trackDtoToDomainMapper.trackDtoToTrackInfo(addTrackDto);
        return ResponseEntity.ok(addTrackUseCase.execute(userId, trackInfo));
    }

}
