package com.foodics.automation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class AllVideoGamesPage {
    private WebDriver driver;
    private WebDriverWait wait;
    private List<String> addedProductNames = new ArrayList<>();

    public AllVideoGamesPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // XPaths for Filters
    private By freeShippingFilter = By.xpath("//span[contains(text(), 'Free Shipping')]/ancestor::a");
    private By conditionNewFilter = By.xpath("//span[text()='New']");

    // XPaths for Sorting
    private By sortDropdown = By.xpath("//span[@id='a-autoid-0-announce']");
    private By sortHighToLow = By.xpath("//a[@id='s-result-sort-select_2']");

    // XPaths for Products
    private By productPrices = By.xpath("//span[@class='a-price-whole']");
    private By nextPageButton = By.xpath("//a[contains(@class, 's-pagination-next')]");
    private By cartIcon = By.xpath("//div[@id='nav-cart-count-container']");

    // Apply "Free Shipping" Filter
    public void applyFreeShippingFilter() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("s-refinements")));
        List<WebElement> filters = driver.findElements(freeShippingFilter);
        if (!filters.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(filters.get(0))).click();
        }

    }

    // Apply "New" Condition Filter
    public void applyConditionNewFilter() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("s-refinements")));
        List<WebElement> filters = driver.findElements(conditionNewFilter);
        if (!filters.isEmpty()) {
            wait.until(ExpectedConditions.elementToBeClickable(filters.get(0))).click();
        }
    }

    // Sort Products by "Price: High to Low"
    public void sortByPriceHighToLow() {
        wait.until(ExpectedConditions.elementToBeClickable(sortDropdown)).click();
        wait.until(ExpectedConditions.elementToBeClickable(sortHighToLow)).click();
    }

    // Scroll to the bottom to load all products dynamically
    public void scrollToBottomAndLoadAllProducts() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        long lastHeight = (long) js.executeScript("return document.body.scrollHeight");

        while (true) {
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));

            long newHeight = (long) js.executeScript("return document.body.scrollHeight");
            if (newHeight == lastHeight) {
                break;
            }
            lastHeight = newHeight;
        }
    }

    // Click "Next Page" safely
    public boolean clickNextPageSafely() {
        try {
            WebElement nextPageButton = driver.findElement(By.xpath("//a[contains(@class, 's-pagination-next')]"));

            if (nextPageButton.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", nextPageButton);
                Thread.sleep(500); // Allow time for smooth scrolling

                try {
                    nextPageButton.click();
                } catch (ElementClickInterceptedException e) {
                    System.out.println("⚠️ Click intercepted! Trying JavaScript click...");
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", nextPageButton);
                }

                System.out.println("✅ Successfully clicked 'Next Page'. Waiting for page load...");
                Thread.sleep(2000); // Allow new products to load
                return true;
            } else {
                System.out.println("🚫 No 'Next Page' button found. Stopping pagination.");
                return false;
            }
        } catch (NoSuchElementException e) {
            System.out.println("🚫 No 'Next Page' button available. Ending pagination.");
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return false;
        }
    }

    // Add all products below 15K EGP to cart
    public void addProductsBelow15KToCart() {
        boolean foundBelow15K = false;

        while (true) {
            scrollToBottomAndLoadAllProducts(); // Ensure all products are loaded

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".s-main-slot .s-result-item")));

            List<WebElement> productContainers = driver.findElements(By.cssSelector(".s-main-slot .s-result-item"));
            System.out.println("🔎 Found " + productContainers.size() + " products.");

            if (productContainers.isEmpty()) {
                System.out.println("🚨 No products found! Stopping execution.");
                return;
            }

            for (WebElement product : productContainers) {
                try {
                    WebElement priceElement = product.findElement(By.xpath(".//span[@class='a-price-whole']"));
                    String priceText = priceElement.getText().replace(",", "").trim();
                    if (priceText.isEmpty()) continue;

                    int price = Integer.parseInt(priceText);
                    System.out.println("🔢 Checking Price: " + price);

                    if (price < 15000) {
                        foundBelow15K = true;

                        WebElement addToCartButton = product.findElement(By.xpath(".//button[@name='submit.addToCart']"));

                        // Scroll product into view
                        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", addToCartButton);
                        Thread.sleep(500);

                        // Click "Add to Cart" button
                        wait.until(ExpectedConditions.elementToBeClickable(addToCartButton)).click();

                        // Store added product name
                        WebElement productTitle = product.findElement(By.cssSelector("h2 a"));
                        String productName = productTitle.getText();
                        addedProductNames.add(productName);

                        System.out.println("✅ Added to cart: " + productName);
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("⚠️ No 'Add to Cart' button found for this product. Skipping.");
                } catch (Exception e) {
                    System.out.println("⚠️ Unexpected error processing product: " + e.getMessage());
                }
            }

            if (foundBelow15K) {
                System.out.println("✅ Products under 15K found and added. Stopping pagination.");
                break;
            }

            if (!clickNextPageSafely()) {
                System.out.println("🚫 No more pages to check. Stopping execution.");
                break;
            }
        }
    }

    // Navigate to Cart Page
    public void goToCart() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement cartIconElement = wait.until(ExpectedConditions.elementToBeClickable(cartIcon));

            // Scroll to the element before clicking
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cartIconElement);

            try {
                // First, try to click normally
                cartIconElement.click();
            } catch (ElementClickInterceptedException e) {
                System.out.println("Element click intercepted, using JavaScript executor to click...");
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", cartIconElement);
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Could not click on cart icon: " + e.getMessage());
        }
    }




}
