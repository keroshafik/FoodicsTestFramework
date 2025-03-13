package com.foodics.automation.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage
{
    private WebDriver driver;

    private By allMenu = By.xpath("//a[@id='nav-hamburger-menu']");
    private By seeAll = By.xpath("//a[@class='hmenu-item hmenu-compressed-btn']");
    private By videoGamesLink = By.xpath("//a[@data-menu-id='16']");
    private By allVideoGamesLink = By.xpath("//a[contains(text(),'All Video Games')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToVideoGames() {
        Wait<WebDriver> fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(10))
                .pollingEvery(Duration.ofMillis(500))
                .ignoring(Exception.class);

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Click "All" menu
        wait.until(ExpectedConditions.elementToBeClickable(allMenu)).click();

        // Click "See All" (if applicable)
        wait.until(ExpectedConditions.elementToBeClickable(seeAll)).click();

        // Click "Video Games" link
        wait.until(ExpectedConditions.elementToBeClickable(videoGamesLink)).click();

        // Click "All Video Games"
        WebElement allVideoGamesElement = fluentWait.until(ExpectedConditions.elementToBeClickable(allVideoGamesLink));
        js.executeScript("arguments[0].scrollIntoView(true);", allVideoGamesElement);
        try {
            allVideoGamesElement.click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", allVideoGamesElement);
        }
    }

}
