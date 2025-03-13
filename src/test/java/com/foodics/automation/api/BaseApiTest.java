package com.foodics.automation.api;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    @BeforeClass
    public void setup() {
        // Set Base URL for Reqres API
        RestAssured.baseURI = "https://reqres.in";
    }
}
