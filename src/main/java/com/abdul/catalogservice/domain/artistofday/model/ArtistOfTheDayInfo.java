package com.abdul.catalogservice.domain.artistofday.model;

import com.abdul.catalogservice.domain.user.model.UserInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public class ArtistOfTheDayInfo {
    private LocalDate day;
    private UserInfo artist;
}
