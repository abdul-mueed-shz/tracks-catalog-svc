package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.in.GetTracksByUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tracks")
@RequiredArgsConstructor
public class TrackController {
    private final GetTracksByUserUseCase getTracksByUserUseCase;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TrackInfo>> getTracksByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(getTracksByUserUseCase.execute(userId));
    }
}
