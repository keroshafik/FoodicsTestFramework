package com.foodics.automation.tests;

import com.fasterxml.jackson.databind.JsonNode;
import com.foodics.automation.base.BaseTest;
import com.foodics.automation.pages.AllVideoGamesPage;
import com.foodics.automation.pages.CartPage;
import com.foodics.automation.pages.HomePage;
import com.foodics.automation.pages.LoginPage;
import com.foodics.automation.utils.JsonDataReader;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class AmazonEgTest extends BaseTest {

    @DataProvider(name = "testData")
    public Object[][] getTestData() {
        JsonNode jsonData = JsonDataReader.readJsonFile("src/main/resources/testData.json");

        JsonNode loginData = jsonData.get("loginData").get(0); // Single login entry
        JsonNode addressData = jsonData.get("addressData").get(0); // Single address entry

        return new Object[][] {
                {
                        loginData.get("email").asText(),
                        loginData.get("password").asText(),
                        addressData.get("fullName").asText(),
                        addressData.get("mobile").asText(),
                        addressData.get("street").asText(),
                        addressData.get("building").asText(),
                        addressData.get("city").asText(),
                        addressData.get("district").asText(),
                        addressData.get("governorate").asText(),
                        addressData.get("landmark").asText()
                }
        };
    }

    @Test(priority = 1, dataProvider = "testData")
    public void completeAmazonEgTest(String email, String password, String fullName, String mobile,
                                     String street, String building, String city, String district,
                                     String governorate, String landmark) {
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
        AllVideoGamesPage allVideoGamesPage = new AllVideoGamesPage(driver);
        CartPage cartPage = new CartPage(driver);

        // Step 1: Login
        loginPage.signIn(email, password);

        // Step 2: Navigate and Apply Filters
        homePage.navigateToVideoGames();
        allVideoGamesPage.applyFreeShippingFilter();
        allVideoGamesPage.applyConditionNewFilter();
        allVideoGamesPage.sortByPriceHighToLow();

        // Step 3: Add Products Below 15K to Cart
        allVideoGamesPage.addProductsBelow15KToCart();
        allVideoGamesPage.goToCart();

        // Step 4: Verify Cart Contents & Proceed
        cartPage.verifyCartContents();
        cartPage.clickOnProceedToCheckout();
        cartPage.clickOnAddNewAddress();

        // Step 5: Fill Address Details
        cartPage.addAddressDetails(fullName, mobile, street, building, city, district, governorate, landmark);
    }
}
// i can't choose cash as a payment method because of amazon limitation (If your total open order value exceeds 25000 EGP.)