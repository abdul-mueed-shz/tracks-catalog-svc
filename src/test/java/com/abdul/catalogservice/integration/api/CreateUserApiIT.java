package com.abdul.catalogservice.integration.api;

import com.abdul.catalogservice.integration.api.config.AbstractCatalogApiIT;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

class CreateUserApiIT extends AbstractCatalogApiIT {
    @Test
    void registersUsersAndFiltersArtists() {
        UUID artistId = registerUser("Artist", true);
        registerUser("Listener", false);

        given().spec(request())
                .queryParam("isArtist", true)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("data", hasSize(1))
                .body("data[0].id", equalTo(artistId.toString()))
                .body("data[0].uuid", equalTo(artistId.toString()))
                .body("data[0].name", equalTo("Artist"))
                .body("data[0].isArtist", equalTo(true));
    }
}
