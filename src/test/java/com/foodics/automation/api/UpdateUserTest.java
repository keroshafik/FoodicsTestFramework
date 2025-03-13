package com.foodics.automation.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class UpdateUserTest {

    private static final Logger logger = LogManager.getLogger(UpdateUserTest.class);
    private static final String BASE_URL = "https://reqres.in/api/users";
    private static final int USER_ID = 414; // Change this as needed

    @Test
    public void updateUserTest() {
        RestAssured.baseURI = BASE_URL;

        // Updated user details
        String requestBody = "{"
                + "\"name\": \"John Updated\","
                + "\"job\": \"Senior QA Engineer\""
                + "}";

        logger.info("Sending PUT request to update user with ID: {}", USER_ID);

        try {
            // Send PUT request
            Response response = given()
                    .contentType(ContentType.JSON)
                    .body(requestBody)
                    .when()
                    .put("/" + USER_ID);

            // Log response details
            logger.info("Response Code: {}", response.getStatusCode());
            logger.info("Response Body: {}", response.getBody().asString());

            // Assertions
            int statusCode = response.getStatusCode();
            Assert.assertEquals(statusCode, 200, "Expected status code to be 200 OK");

            String responseBody = response.getBody().asString();
            Assert.assertTrue(responseBody.contains("John Updated"), "Response should contain updated name");
            Assert.assertTrue(responseBody.contains("Senior QA Engineer"), "Response should contain updated job title");

        } catch (Exception e) {
            logger.error("Exception occurred while updating user: {}", e.getMessage(), e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }
    }
}
