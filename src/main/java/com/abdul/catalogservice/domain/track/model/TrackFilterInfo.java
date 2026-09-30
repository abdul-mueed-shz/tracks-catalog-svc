package com.abdul.catalogservice.domain.track.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TrackFilterInfo {
    private Long userId;
    private String userName;
}
