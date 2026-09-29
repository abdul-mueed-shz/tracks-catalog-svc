package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.domain.track.model.TrackInfo;
import com.abdul.catalogservice.domain.track.port.in.GetTracksByArtistUseCase;
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
    private final GetTracksByArtistUseCase getTracksByArtistUseCase;

    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<TrackInfo>> getTracksByArtist(@PathVariable Long artistId) {
        return ResponseEntity.ok(getTracksByArtistUseCase.execute(artistId));
    }
}
