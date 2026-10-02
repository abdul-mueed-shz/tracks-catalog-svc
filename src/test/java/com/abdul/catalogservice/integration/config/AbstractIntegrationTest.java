package com.abdul.catalogservice.integration.config;

import com.abdul.catalogservice.adapter.out.persistence.adapter.UserRepositoryAdapter;
import com.abdul.catalogservice.adapter.out.persistence.repository.ArtistOfTheDayJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.repository.ArtistRotationJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.repository.UserJpaRepository;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest
@ActiveProfiles("integration")
@EnableAutoConfiguration
@EnableJpaRepositories(basePackageClasses = {
        UserJpaRepository.class,
        ArtistOfTheDayJpaRepository.class,
        ArtistRotationJpaRepository.class
})
@Import(UserRepositoryAdapter.class)
public abstract class AbstractIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES =
            new PostgreSQLContainer<>("postgres:16-alpine");

    @DynamicPropertySource
    static void configureDatabase(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }
}
