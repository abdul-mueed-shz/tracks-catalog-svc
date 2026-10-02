package com.abdul.catalogservice.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserRequest(
        @NotBlank(message = "Name is required")
        String name
) {
}
