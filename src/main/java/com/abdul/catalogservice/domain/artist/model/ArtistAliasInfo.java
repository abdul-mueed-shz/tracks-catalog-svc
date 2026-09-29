package com.abdul.catalogservice.domain.artist.model;

import com.abdul.catalogservice.domain.artist.common.model.BaseInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ArtistAliasInfo extends BaseInfo {
    private ArtistInfo artist;
    private String aliasName;
    private String normalizedName;
    private Boolean isPrimary;
}
