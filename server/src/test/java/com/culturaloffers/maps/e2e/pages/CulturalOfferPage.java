package com.culturaloffers.maps.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CulturalOfferPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"title\"]")
    private WebElement title;

    @FindBy(xpath = "//*[@id=\"description\"]")
    private WebElement description;

    @FindBy(xpath = "//*[@id=\"subtype\"]")
    private WebElement subtype;

    @FindBy(xpath = "//*[@id=\"address\"]")
    private WebElement address;

    @FindBy(xpath = "//*[text()=\"Add\"]")
    private WebElement addBtn;

    @FindBy(xpath = "//*[text()=\"Update\"]")
    private WebElement updateBtn;

    @FindBy(xpath = "//*[@class=\"btn\"]//following::button[3]")
    private WebElement openNewsTableBtn;

    @FindBy(xpath = "//*[@class=\"btn\"]//following::button[4]")
    private WebElement openUpdateBtn;

    @FindBy(xpath = "//*[text()=\"Create new\"]")
    private WebElement createBtn;

    @FindBy(xpath = "//*[@aria-label=\"Next\"]")
    private WebElement nextPage;

    @FindBy(xpath = "//*[@class=\"btn\"]//following::button[5]")
    private WebElement deleteBtn;

    public void ensureIsDisplayedCulturalOfferTable() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("offers")));
    }

    public void ensureAlertAppeared() {
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.alertIsPresent());
    }

    public void ensureIsDisplayedUpdatedCulturalOffer(String newName){
        this.driver.switchTo().alert().accept();
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//*[text()=\"" + newName + "\"]"),1));
    }

    public void ensureIsNotDisplayedUpdatedCulturalOffer(String newName){
        this.driver.switchTo().alert().accept();
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//*[text()=\"" + newName + "\"]"),0));
    }

    public void ensureDeletionHappened(String oldOffer){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//*[text()=\"" + oldOffer + "\"]"),0));
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebElement getTitle() {
        return title;
    }

    public WebElement getDescription() {
        return description;
    }

    public WebElement getSubtype() {
        return subtype;
    }

    public WebElement getAddress() {
        return address;
    }

    public WebElement getAddBtn() {
        return addBtn;
    }

    public WebElement getUpdateBtn() {
        return updateBtn;
    }

    public WebElement getOpenNewsTableBtn() {
        return openNewsTableBtn;
    }

    public WebElement getOpenUpdateBtn() {
        return openUpdateBtn;
    }

    public WebElement getCreateBtn() {
        return createBtn;
    }

    public WebElement getNextPage() {
        return nextPage;
    }

    public WebElement getDeleteBtn() {
        return deleteBtn;
    }

    public CulturalOfferPage(WebDriver driver) {
        this.driver = driver;
    }

    public CulturalOfferPage() {
    }

    public void nextPage(int page) {
        for(int i = 0; i < page - 1; i++)
            nextPage.click();
    }

}
