package com.culturaloffers.maps.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OfferNewsPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"title\"]")
    private WebElement title;

    @FindBy(xpath = "//*[@id=\"description\"]")
    private WebElement description;

    @FindBy(xpath = "//*[text()=\"Add\"]")
    private WebElement addBtn;

    @FindBy(xpath = "//*[text()=\"Update\"]")
    private WebElement updateBtn;

    @FindBy(xpath = "//*[@class=\"btn\"]//following::button[2]")
    private WebElement openViewTableBtn;

    @FindBy(xpath = "//*[@class=\"btn\"]")
    private WebElement openUpdateBtn;

    @FindBy(xpath = "//*[text()=\"Create new\"]")
    private WebElement createBtn;

    @FindBy(xpath = "//*[@aria-label=\"Next\"]")
    private WebElement nextPage;

    @FindBy(xpath = "//*[@class=\"btn\"]//following::button[1]")
    private WebElement deleteBtn;

    public void ensureIsDisplayedOfferNewsTable() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("news")));
    }

    public void ensureAlertAppeared() {
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.alertIsPresent());
    }

    public void ensureIsDisplayedUpdatedOfferNews(String newName){
        this.driver.switchTo().alert().accept();
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//*[text()=\"" + newName + "\"]"),1));
    }

    public void ensureDeletionHappened(String oldNews){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//*[text()=\"" + oldNews + "\"]"),0));
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

    public WebElement getAddBtn() {
        return addBtn;
    }

    public WebElement getUpdateBtn() {
        return updateBtn;
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

    public OfferNewsPage(WebDriver driver) {
        this.driver = driver;
    }

    public OfferNewsPage() {
    }

}
