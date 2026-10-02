package com.abdul.catalogservice.domain.artistofday.port.in;

import com.abdul.catalogservice.domain.user.model.UserInfo;

public interface GetArtistOfTheDayUseCase {
    UserInfo execute();
}
