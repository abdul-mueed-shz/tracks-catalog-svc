package com.abdul.catalogservice.adapter.in.web.controller;

import com.abdul.catalogservice.adapter.in.web.dto.ArtistResponse;
import com.abdul.catalogservice.adapter.in.web.mapper.UserDtoToDomainMapper;
import com.abdul.catalogservice.domain.artistofday.port.in.GetArtistOfTheDayUseCase;
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
    private final UserDtoToDomainMapper userDtoToDomainMapper;

    @GetMapping
    public ResponseEntity<ArtistResponse> getArtistOfTheDay() {
        return ResponseEntity.ok(userDtoToDomainMapper.toArtistResponse(getArtistOfTheDayUseCase.execute()));
    }
}
