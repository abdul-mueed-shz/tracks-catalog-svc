package com.abdul.catalogservice.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateUserNameRequest(
        @NotBlank(message = "Name is required")
        String name
) {
}
