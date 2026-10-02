package com.abdul.catalogservice.integration.api;

import com.abdul.catalogservice.integration.api.config.AbstractCatalogApiIT;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class ArtistOfTheDayApiIT extends AbstractCatalogApiIT {
    @Test
    void rotatesArtistOfTheDayThroughTheApi() {
        Long firstArtistId = registerUser("First Artist", true);
        Long secondArtistId = registerUser("Second Artist", true);
        Long thirdArtistId = registerUser("Third Artist", true);

        assertArtistOfTheDay(firstArtistId);
        clock.advanceTo("2026-10-02T00:00:00Z");
        assertArtistOfTheDay(secondArtistId);
        clock.advanceTo("2026-10-03T00:00:00Z");
        assertArtistOfTheDay(thirdArtistId);
        clock.advanceTo("2026-10-04T00:00:00Z");
        assertArtistOfTheDay(firstArtistId);
    }

    private void assertArtistOfTheDay(Long expectedArtistId) {
        given().spec(request())
                .when()
                .get("/artist-of-the-day")
                .then()
                .statusCode(200)
                .body("id", equalTo(expectedArtistId.intValue()));
    }
}
