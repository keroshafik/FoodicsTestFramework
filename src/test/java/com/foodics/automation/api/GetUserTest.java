package com.foodics.automation.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class GetUserTest {

    @Test
    public void testGetUser() {
        RestAssured.baseURI = "https://reqres.in";

        int existingUserId = 2; // Using a valid user ID

        Response response = given()
                .when()
                .get("/api/users/" + existingUserId)
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 200, "Status code should be 200 OK");
        Assert.assertNotNull(response.jsonPath().getString("data"), "User data should not be null");
    }
}
