package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.adapter.in.web.dto.AddTrackDto;
import com.abdul.catalogservice.adapter.in.web.dto.TrackResponse;
import com.abdul.catalogservice.adapter.in.web.mapper.TrackDtoToDomainMapper;
import com.abdul.catalogservice.domain.common.model.PageInfo;
import com.abdul.catalogservice.domain.common.model.PaginationInfo;
import com.abdul.catalogservice.domain.common.model.SortInfo;
import com.abdul.catalogservice.domain.track.model.TrackFilterInfo;
import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.in.AddTrackUseCase;
import com.abdul.catalogservice.domain.track.port.in.GetTracksUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {
    private final TrackDtoToDomainMapper trackDtoToDomainMapper;
    private final GetTracksUseCase getTracksUseCase;
    private final AddTrackUseCase addTrackUseCase;

    @PostMapping("/user/{userId}/add")
    public ResponseEntity<TrackResponse> addTrack(@PathVariable UUID userId, @Valid @RequestBody AddTrackDto addTrackDto) {
        TrackInfo trackInfo = trackDtoToDomainMapper.trackDtoToTrackInfo(addTrackDto);
        TrackInfo createdTrack = addTrackUseCase.execute(userId, trackInfo);
        return ResponseEntity.ok(trackDtoToDomainMapper.toTrackResponse(createdTrack));
    }

    @GetMapping
    public ResponseEntity<PageInfo<TrackResponse>> getTracks(
            @ModelAttribute TrackFilterInfo filterInfo,
            @ModelAttribute PaginationInfo paginationInfo,
            @ModelAttribute SortInfo trackSortInfo
    ) {
        PageInfo<TrackInfo> trackPage = getTracksUseCase.execute(
                filterInfo,
                paginationInfo,
                trackSortInfo
        );
        return ResponseEntity.ok(trackPage.map(trackDtoToDomainMapper::toTrackResponse));
    }
}
