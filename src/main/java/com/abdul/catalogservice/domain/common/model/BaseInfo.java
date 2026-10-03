package com.abdul.catalogservice.domain.common.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class BaseInfo {
    private Long id;
    @Builder.Default
    private UUID uuid = UUID.randomUUID();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
