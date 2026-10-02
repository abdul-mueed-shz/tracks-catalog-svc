package com.abdul.catalogservice.integration;

import com.abdul.catalogservice.adapter.out.persistence.adapter.ArtistOfTheDayRepositoryAdapter;
import com.abdul.catalogservice.adapter.out.persistence.entity.User;
import com.abdul.catalogservice.adapter.out.persistence.mapper.ArtistOfTheDayDomainEntityMapperImpl;
import com.abdul.catalogservice.adapter.out.persistence.repository.ArtistOfTheDayJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.repository.ArtistRotationJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.repository.UserJpaRepository;
import com.abdul.catalogservice.domain.artistofday.port.out.ArtistOfTheDayRepository;
import com.abdul.catalogservice.domain.artistofday.usecase.GetArtistOfTheDayUseCaseImpl;
import com.abdul.catalogservice.domain.user.port.out.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.abdul.catalogservice.integration.config.AbstractIntegrationTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;

class ArtistRotationIntegrationTest extends AbstractIntegrationTest {
    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ArtistOfTheDayJpaRepository artistOfTheDayJpaRepository;

    @Autowired
    private ArtistRotationJpaRepository artistRotationJpaRepository;

    @BeforeEach
    void cleanDatabase() {
        artistOfTheDayJpaRepository.deleteAll();
        artistRotationJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
    }

    @Test
    @Transactional
    void rotatesThroughThreeArtistsAndStartsAgain() {
        User first = userJpaRepository.save(User.builder().name("First").isArtist(true).build());
        User second = userJpaRepository.save(User.builder().name("Second").isArtist(true).build());
        User third = userJpaRepository.save(User.builder().name("Third").isArtist(true).build());

        ArtistOfTheDayRepository artistOfTheDayRepository = new ArtistOfTheDayRepositoryAdapter(
                artistOfTheDayJpaRepository,
                artistRotationJpaRepository,
                new ArtistOfTheDayDomainEntityMapperImpl()
        );
        MutableClock clock = new MutableClock(Instant.parse("2026-10-01T00:00:00Z"));
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

    private static final class MutableClock extends Clock {
        private Instant current;

        private MutableClock(Instant current) {
            this.current = current;
        }

        private void advanceTo(String instant) {
            current = Instant.parse(instant);
        }

        @Override
        public Instant instant() {
            return current;
        }

        @Override
        public ZoneOffset getZone() {
            return ZoneOffset.UTC;
        }

        @Override
        public Clock withZone(java.time.ZoneId zone) {
            return this;
        }
    }
}
