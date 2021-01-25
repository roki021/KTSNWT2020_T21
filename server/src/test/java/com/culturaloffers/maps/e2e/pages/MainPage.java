package com.culturaloffers.maps.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"map\"]")
    private WebElement map;

    @FindBy(xpath = "//*[@id=\"offer-view\"]")
    private WebElement offerView;

    @FindBy(xpath = "//*[@aria-label=\"Next\"]")
    private WebElement nextPage;

    @FindBy(xpath = "//*[@aria-label=\"Previous\"]")
    private WebElement previousPage;

    public MainPage() {
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedMap() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("map")));
    }

    public void activateMap() {
        Actions actions = new Actions(driver);
        actions.moveByOffset(400, 400).click();
    }

    public void ensureIsDisplayedOffewView() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("offer-view")));
    }

    public void clickOnFeature(double lon, double lat) throws InterruptedException {
        ((JavascriptExecutor)driver).executeScript(String.format("window.execClick(%f, %f);", lon, lat));
    }

    public void nextPage(int page) {
        for(int i = 0; i < page - 1; i++)
            nextPage.click();
    }

    public WebElement getMap() {
        return map;
    }

    public WebElement getOfferView() {
        return offerView;
    }
}
