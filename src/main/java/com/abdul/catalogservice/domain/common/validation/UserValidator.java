package com.abdul.catalogservice.domain.common.validation;

import com.abdul.catalogservice.domain.common.exception.DomainValidationException;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserValidator {

    public void isArtist(UserInfo userInfo) {
        if (userInfo == null) {
            throw new DomainValidationException("User not found.");
        }
        if (Boolean.FALSE.equals(userInfo.getIsArtist())) {
            throw new DomainValidationException("User is not an artist.");
        }
    }
}
