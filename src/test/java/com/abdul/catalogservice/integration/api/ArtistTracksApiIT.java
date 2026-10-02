package com.abdul.catalogservice.integration.api;

import com.abdul.catalogservice.integration.api.config.AbstractCatalogApiIT;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

class ArtistTracksApiIT extends AbstractCatalogApiIT {
    @Test
    void addsTrackToArtistAndFetchesArtistTracks() {
        Long artistId = registerUser("Track Artist", true);

        given().spec(request())
                .contentType("application/json")
                .body("""
                        {
                          "title": "First Track",
                          "genre": "Rock",
                          "durationMs": 210000,
                          "releaseDate": "2026-01-15"
                        }
                        """)
                .pathParam("userId", artistId)
                .when()
                .post("/tracks/user/{userId}/add")
                .then()
                .statusCode(200)
                .body("title", equalTo("First Track"))
                .body("genre", equalTo("Rock"))
                .body("durationMs", equalTo(210000))
                .body("user.id", equalTo(artistId.intValue()));

        given().spec(request())
                .queryParam("userId", artistId)
                .when()
                .get("/tracks")
                .then()
                .statusCode(200)
                .body("data", hasSize(1))
                .body("data[0].title", equalTo("First Track"))
                .body("data[0].user.id", equalTo(artistId.intValue()));
    }
}
