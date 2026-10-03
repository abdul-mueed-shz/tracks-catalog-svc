package com.abdul.catalogservice.integration.api;

import com.abdul.catalogservice.integration.api.config.AbstractCatalogApiIT;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

class UpdateUserApiIT extends AbstractCatalogApiIT {
    @Test
    void editsArtistNameAndReturnsUpdatedArtist() {
        Long artistId = registerUser("Original Name", true);

        given().spec(request())
                .contentType("application/json")
                .body("""
                        {"name":"Updated Name"}
                        """)
                .pathParam("userId", artistId)
                .when()
                .patch("/users/{userId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(artistId.intValue()))
                .body("name", equalTo("Updated Name"))
                .body("isArtist", equalTo(true));

        given().spec(request())
                .pathParam("userId", artistId)
                .when()
                .get("/users/{userId}/aliases")
                .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .body("[0].aliasName", equalTo("Original Name"));

        given().spec(request())
                .pathParam("userId", artistId)
                .when()
                .get("/users/{userId}")
                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Name"));
    }
}
