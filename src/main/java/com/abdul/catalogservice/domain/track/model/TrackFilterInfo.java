package com.abdul.catalogservice.domain.track.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TrackFilterInfo {
    private UUID userId;
    private String userName;
}
