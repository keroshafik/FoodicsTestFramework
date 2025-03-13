package com.foodics.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By accountList = By.xpath("//div[@class='nav-line-1-container']");
    private By emailField = By.xpath("//input[@id='ap_email']");
    private By continueButton = By.id("continue");
    private By passwordField = By.id("ap_password");
    private By signInButton = By.id("signInSubmit");

// constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

// methods
public void signIn(String email, String password) {
    try {
        // Open the Amazon Egypt page
        driver.get("https://www.amazon.eg/-/en");

        // Initialize FluentWait with a longer timeout (30 seconds)
        FluentWait<WebDriver> wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(TimeoutException.class)
                .ignoring(Exception.class);

        // Wait for "Account List" to be clickable and click it
        wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-link-accountList"))).click();

        // Wait for the email input field to be visible
        System.out.println("Waiting for email input field...");
        WebElement emailFieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        System.out.println("Email input field is now visible.");
        emailFieldElement.sendKeys(email);

        // Click on Continue button
        wait.until(ExpectedConditions.elementToBeClickable(By.id("continue"))).click();

        // Wait for the password field to be visible and enter password
        System.out.println("Waiting for password input field...");
        WebElement passwordFieldElement = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        System.out.println("Password input field is now visible.");
        passwordFieldElement.sendKeys(password);

        // Click on Sign In button
        wait.until(ExpectedConditions.elementToBeClickable(By.id("signInSubmit"))).click();

        System.out.println("Sign In action completed.");
    } catch (TimeoutException e) {
        System.out.println("Timeout while waiting for an element: " + e.getMessage());
    } catch (Exception e) {
        System.out.println("An error occurred during the sign-in process: " + e.getMessage());
    }
}















    public void login(String email, String password) {
        driver.get("https://www.amazon.eg/");
        driver.findElement(accountList).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(emailField)).sendKeys(email);
        driver.findElement(continueButton).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField)).sendKeys(password);
        driver.findElement(signInButton).click();
    }
}
