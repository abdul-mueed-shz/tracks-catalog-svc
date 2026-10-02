package com.abdul.catalogservice.domain.common.validation;

import com.abdul.catalogservice.domain.common.exception.DomainValidationException;
import com.abdul.catalogservice.domain.common.exception.NotFoundException;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserValidator {

    public void isArtist(UserInfo userInfo) {
        userExists(userInfo);
        if (Boolean.FALSE.equals(userInfo.getIsArtist())) {
            throw new DomainValidationException("User is not an artist.");
        }
    }

    public void userExists(UserInfo userInfo) {
        if (userInfo == null) {
            throw new NotFoundException("User not found.");
        }
    }
}
