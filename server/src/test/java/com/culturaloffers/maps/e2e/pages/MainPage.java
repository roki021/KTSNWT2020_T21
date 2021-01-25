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

    @FindBy(xpath = "//*[@id=\"offer-title\"]")
    private WebElement offerTitle;

    @FindBy(xpath = "//*[contains(text(), \"Location\")]/following-sibling::div")
    private WebElement offerLocation;

    @FindBy(xpath = "//*[@id=\"button-subscribe\"]")
    private WebElement subscribeBtn;

    @FindBy(xpath = "//a[contains(text(), \"Log out\")]")
    private WebElement logOutBtn;

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

    public void ensureIsDisplayedOffewTitle() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("offer-title")));
    }

    public void ensureIsNotVisibleOfferView() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("offer-view")));
    }

    public void ensureIsDisplayedSubsButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("button-subscribe")));
    }

    public void ensureIsDisplayedLogInButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[contains(text(), \"Log in\")]")));
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

    public WebElement getOfferTitle() { return offerTitle; }

    public WebElement getOfferLocation() { return offerLocation; }

    public WebElement getSubscribeBtn() { return subscribeBtn; }

    public WebElement getLogOutBtn() { return logOutBtn; }
}
