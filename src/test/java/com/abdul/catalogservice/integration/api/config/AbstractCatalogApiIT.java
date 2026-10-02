package com.abdul.catalogservice.integration.api.config;

import com.abdul.catalogservice.adapter.out.persistence.repository.ArtistOfTheDayJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.repository.ArtistRotationJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.repository.TrackJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.repository.UserAliasJpaRepository;
import com.abdul.catalogservice.adapter.out.persistence.repository.UserJpaRepository;
import com.abdul.catalogservice.integration.config.AbstractIntegrationTest;
import com.abdul.catalogservice.integration.config.MutableClock;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public abstract class AbstractCatalogApiIT extends AbstractIntegrationTest {
    @Autowired
    private ArtistOfTheDayJpaRepository artistOfTheDayJpaRepository;

    @Autowired
    private ArtistRotationJpaRepository artistRotationJpaRepository;

    @Autowired
    private TrackJpaRepository trackJpaRepository;

    @Autowired
    private UserAliasJpaRepository userAliasJpaRepository;

    @Autowired
    private UserJpaRepository userJpaRepository;

    @Autowired
    protected MutableClock clock;

    @LocalServerPort
    private int port;

    @BeforeEach
    void resetIntegrationState() {
        artistOfTheDayJpaRepository.deleteAll();
        artistRotationJpaRepository.deleteAll();
        trackJpaRepository.deleteAll();
        userAliasJpaRepository.deleteAll();
        userJpaRepository.deleteAll();
        clock.setInstant("2026-10-01T00:00:00Z");
    }

    protected RequestSpecification request() {
        RestAssured.reset();
        return new RequestSpecBuilder()
                .setBaseUri("http://localhost")
                .setPort(port)
                .setBasePath("/catalog-svc")
                .build();
    }

    protected Long registerUser(String name, boolean isArtist) {
        Number id = given().spec(request())
                .contentType("application/json")
                .body("""
                        {"name":"%s","isArtist":%s}
                        """.formatted(name, isArtist))
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .extract()
                .path("id");
        return id.longValue();
    }
}
