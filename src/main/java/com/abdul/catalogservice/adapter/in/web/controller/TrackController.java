package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.adapter.in.web.dto.AddTrackDto;
import com.abdul.catalogservice.adapter.in.web.mapper.TrackDtoToDomainMapper;
import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.in.AddTrackUseCase;
import com.abdul.catalogservice.domain.track.port.in.GetTracksByUserUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {
    private final TrackDtoToDomainMapper trackDtoToDomainMapper;
    private final GetTracksByUserUseCase getTracksByUserUseCase;
    private final AddTrackUseCase addTrackUseCase;

    @PostMapping("/user/{userId}/add")
    public ResponseEntity<TrackInfo> addTrack(@PathVariable Long userId, @Valid @RequestBody AddTrackDto addTrackDto) {
        TrackInfo trackInfo = trackDtoToDomainMapper.trackDtoToTrackInfo(addTrackDto);
        return ResponseEntity.ok(addTrackUseCase.execute(userId, trackInfo));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<PageInfo<TrackInfo>> getTracksByUser(
            @PathVariable Long userId,
            @ModelAttribute PaginationInfo paginationInfo,
            @ModelAttribute SortInfo trackSortInfo
    ) {
        return ResponseEntity.ok(getTracksByUserUseCase.execute(
                userId,
                paginationInfo,
                trackSortInfo
        ));
    }

}
