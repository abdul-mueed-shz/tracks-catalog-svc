package com.abdul.catalogservice.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AddTrackDto(
        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Genre is required")
        String genre,
        @NotNull(message = "Duration is required")
        Integer durationMs,
        @NotNull(message = "Release date is required")
        LocalDate releaseDate
) {
}
