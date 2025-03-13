package com.foodics.automation.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class CreateUserTest {

    @Test
    public void testCreateUser() {
        RestAssured.baseURI = "https://reqres.in";

        String requestBody = "{ \"name\": \"John Doe\", \"job\": \"QA Engineer\" }";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/api/users")
                .then()
                .extract().response();

        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201 Created");
        Assert.assertNotNull(response.jsonPath().getString("id"), "User ID should not be null");
    }
}
