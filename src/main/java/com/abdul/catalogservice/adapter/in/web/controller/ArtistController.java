package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.domain.artist.model.ArtistInfo;
import com.abdul.catalogservice.domain.artist.port.in.CreateArtistUseCase;
import com.abdul.catalogservice.domain.artist.port.in.GetArtistDetailsUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/artists")
@RequiredArgsConstructor
public class ArtistController {
    private final GetArtistDetailsUseCase artistDetailsUseCase;
    private final CreateArtistUseCase createArtistUseCase;

    @PostMapping
    public ResponseEntity<ArtistInfo> createArtist(
            @RequestBody ArtistInfo artistInfo
    ) {
        ArtistInfo createdArtist = createArtistUseCase.execute(artistInfo);
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{artistId}")
                        .buildAndExpand(createdArtist.getId())
                        .toUri()
        ).body(createdArtist);
    }

    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistInfo> getArtistDetails(
            @PathVariable Long artistId
    ) {
        ArtistInfo artistInfo = artistDetailsUseCase.execute(artistId);
        return ResponseEntity.ok(artistInfo);
    }
}
