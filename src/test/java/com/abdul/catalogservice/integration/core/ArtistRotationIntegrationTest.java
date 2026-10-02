package com.abdul.catalogservice.integration.core;

import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.adapter.out.persistence.repository.*;
import com.abdul.catalogservice.domain.artistofday.port.out.ArtistOfTheDayRepository;
import com.abdul.catalogservice.domain.artistofday.usecase.GetArtistOfTheDayUseCaseImpl;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import com.abdul.catalogservice.integration.config.AbstractIntegrationTest;
import com.abdul.catalogservice.integration.config.MutableClock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

class ArtistRotationIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistOfTheDayRepository artistOfTheDayRepository;

    @Autowired
    private ArtistOfTheDayJpaRepository artistOfTheDayJpaRepository;

    @Autowired
    private ArtistRotationJpaRepository artistRotationJpaRepository;

    @Autowired
    private TrackJpaRepository trackJpaRepository;

    @Autowired
    private UserAliasJpaRepository userAliasJpaRepository;

    @Autowired
    private MutableClock clock;

    @BeforeEach
    void cleanDatabase() {
        artistOfTheDayJpaRepository.deleteAll();
        artistRotationJpaRepository.deleteAll();
        trackJpaRepository.deleteAll();
        userAliasJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
        clock.setInstant("2026-10-01T00:00:00Z");
    }

    @Test
    @Transactional
    void rotatesThroughThreeArtistsAndStartsAgain() {
        User first = userJpaRepository.save(User.builder().name("First").isArtist(true).build());
        User second = userJpaRepository.save(User.builder().name("Second").isArtist(true).build());
        User third = userJpaRepository.save(User.builder().name("Third").isArtist(true).build());

        GetArtistOfTheDayUseCaseImpl useCase =
                new GetArtistOfTheDayUseCaseImpl(artistOfTheDayRepository, userRepository, clock);

        assertThat(useCase.execute().getId()).isEqualTo(first.getId());
        clock.advanceTo("2026-10-02T00:00:00Z");
        assertThat(useCase.execute().getId()).isEqualTo(second.getId());
        clock.advanceTo("2026-10-03T00:00:00Z");
        assertThat(useCase.execute().getId()).isEqualTo(third.getId());
        clock.advanceTo("2026-10-04T00:00:00Z");
        assertThat(useCase.execute().getId()).isEqualTo(first.getId());
    }

}
