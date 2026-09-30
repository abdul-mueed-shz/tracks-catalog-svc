package com.abdul.catalogservice.domain.track.model;

import com.abdul.catalogservice.domain.user.model.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class TrackFilterInfo {
    private Long id;
}
