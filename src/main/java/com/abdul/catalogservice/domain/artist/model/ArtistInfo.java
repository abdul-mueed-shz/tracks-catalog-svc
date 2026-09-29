package com.abdul.catalogservice.domain.artist.model;

import com.abdul.catalogservice.domain.artist.common.model.BaseInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ArtistInfo extends BaseInfo {
    private String name;
}
