package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.domain.artistofday.port.in.GetArtistOfTheDayUseCase;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/artist-of-the-day")
@RequiredArgsConstructor
public class ArtistOfTheDayController {
    private final GetArtistOfTheDayUseCase getArtistOfTheDayUseCase;

    @GetMapping
    public ResponseEntity<UserInfo> getArtistOfTheDay() {
        return ResponseEntity.ok(getArtistOfTheDayUseCase.execute());
    }
}
