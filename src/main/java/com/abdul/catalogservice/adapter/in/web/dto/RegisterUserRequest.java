package com.abdul.catalogservice.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record RegisterUserRequest(
        @NotBlank(message = "Name is required")
        String name,
        Boolean isArtist
) {
    public RegisterUserRequest {
        isArtist = isArtist != null && isArtist;
    }
}
