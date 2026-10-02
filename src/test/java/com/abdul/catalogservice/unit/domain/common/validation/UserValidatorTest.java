package com.abdul.catalogservice.unit.domain.common.validation;

import com.abdul.catalogservice.domain.common.exception.DomainValidationException;
import com.abdul.catalogservice.domain.common.exception.NotFoundException;
import com.abdul.catalogservice.domain.common.validation.UserValidator;
import com.abdul.catalogservice.domain.user.model.UserInfo;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserValidatorTest {
    private final UserValidator validator = new UserValidator();

    @Test
    void acceptsExistingUser() {
        validator.userExists(UserInfo.builder().id(1L).build());
    }

    @Test
    void rejectsMissingUser() {
        assertThatThrownBy(() -> validator.userExists(null))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found.");
    }

    @Test
    void acceptsArtist() {
        validator.isArtist(UserInfo.builder().isArtist(true).build());
    }

    @Test
    void rejectsNonArtist() {
        assertThatThrownBy(() -> validator.isArtist(UserInfo.builder().isArtist(false).build()))
                .isInstanceOf(DomainValidationException.class)
                .hasMessage("User is not an artist.");
    }

    @Test
    void rejectsMissingUserBeforeArtistCheck() {
        assertThatThrownBy(() -> validator.isArtist(null))
                .isInstanceOf(NotFoundException.class);
    }
}
