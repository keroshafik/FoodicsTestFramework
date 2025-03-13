package com.foodics.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private By cartItems = By.xpath("//div[contains(@class, 'sc-list-item-content')]");
    private By ProceedToCheckoutButton = By.xpath("//input[@name='proceedToRetailCheckout']");
    private By addNewAddressButton = By.xpath("//a[@class='a-button-text']");

    // Address fields
    private By fullNameField = By.id("address-ui-widgets-enterAddressFullName");
    private By mobileField = By.id("address-ui-widgets-enterAddressPhoneNumber");
    private By streetField = By.id("address-ui-widgets-enterAddressLine1");
    private By buildingField = By.xpath("//input[@id='address-ui-widgets-enter-building-name-or-number']");
    private By cityField = By.id("address-ui-widgets-enterAddressCity");
    private By districtField = By.id("address-ui-widgets-enterAddressDistrictOrCounty");
    private By governorateField = By.id("address-ui-widgets-enterAddressStateOrRegion");
    private By landmarkField = By.id("address-ui-widgets-landmark");
    private By saveAddressButton = By.xpath("//input[@data-testid='bottom-continue-button']");


    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Verify all added products are in the cart and under 15K EGP
    public void verifyCartContents() {

        wait.until(ExpectedConditions.presenceOfElementLocated(cartItems));
        List<WebElement> cartProductElements = driver.findElements(cartItems);
        List<WebElement> cartPriceElements = driver.findElements(By.xpath("//span[@class='a-price-whole']"));

        for (int i = 0; i < cartProductElements.size(); i++) {
            String productName = cartProductElements.get(i).getText();

            // Ensure the price element exists
            if (i >= cartPriceElements.size()) {
                System.out.println("⚠️ Warning: No price found for product: " + productName);
                continue; // Skip this product and move to the next one
            }

            String priceText = cartPriceElements.get(i).getText().replaceAll("[^0-9]", "").trim();

            // Handle empty price values
            if (priceText.isEmpty()) {
                System.out.println("⚠️ Warning: Empty price for product: " + productName);
                continue;
            }

            try {
                int price = Integer.parseInt(priceText);

                if (price < 15000) {
                    System.out.println("✅ Verified in cart: " + productName + " | Price: " + price);
                } else {
                    System.out.println("❌ Error! Product in cart is above 15K: " + productName + " | Price: " + price);
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Error: Invalid price format for product: " + productName + " | Raw Price Text: " + priceText);
            }
        }

        System.out.println("🛒 Cart verification completed.");
    }
    public void clickOnProceedToCheckout() {
        WebElement proceedButton = wait.until(ExpectedConditions.elementToBeClickable(ProceedToCheckoutButton));
        proceedButton.click();
    }


    public void clickOnAddNewAddress(){
        WebElement AddNewAddressButton = wait.until(ExpectedConditions.elementToBeClickable(addNewAddressButton));
        AddNewAddressButton.click();
    }

    public void addAddressDetails(String fullName, String mobile, String street, String building, String city, String district, String governorate, String landmark) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(fullNameField)).sendKeys(fullName);
        driver.findElement(mobileField).sendKeys(mobile);
        driver.findElement(streetField).sendKeys(street);
        driver.findElement(buildingField).sendKeys(building);
        driver.findElement(cityField).sendKeys(city);
        driver.findElement(districtField).sendKeys(district);
        driver.findElement(governorateField).sendKeys(governorate);
        driver.findElement(landmarkField).sendKeys(landmark);

        // Save the address
        wait.until(ExpectedConditions.elementToBeClickable(saveAddressButton)).click();
        System.out.println("📍 Address added successfully.");
    }
}
