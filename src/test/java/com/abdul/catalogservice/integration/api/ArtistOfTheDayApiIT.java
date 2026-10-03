package com.abdul.catalogservice.integration.api;

import com.abdul.catalogservice.integration.api.config.AbstractCatalogApiIT;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class ArtistOfTheDayApiIT extends AbstractCatalogApiIT {
    @Test
    void rotatesArtistOfTheDayThroughTheApi() {
        UUID firstArtistId = registerUser("First Artist", true);
        UUID secondArtistId = registerUser("Second Artist", true);
        UUID thirdArtistId = registerUser("Third Artist", true);

        assertArtistOfTheDay(firstArtistId);
        clock.advanceTo("2026-10-02T00:00:00Z");
        assertArtistOfTheDay(secondArtistId);
        clock.advanceTo("2026-10-03T00:00:00Z");
        assertArtistOfTheDay(thirdArtistId);
        clock.advanceTo("2026-10-04T00:00:00Z");
        assertArtistOfTheDay(firstArtistId);
    }

    private void assertArtistOfTheDay(UUID expectedArtistId) {
        given().spec(request())
                .when()
                .get("/artist-of-the-day")
                .then()
                .statusCode(200)
                .body("id", equalTo(expectedArtistId.toString()))
                .body("uuid", equalTo(expectedArtistId.toString()));
    }
}
