package com.culturaloffers.maps.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class OfferTypesPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"name\"]")
    private WebElement name;

    @FindBy(xpath = "//*[text()=\"Add\"]")
    private WebElement addBtn;

    @FindBy(xpath = "//*[text()=\"Update\"]")
    private WebElement updateBtn;

    @FindBy(xpath = "//*[@class=\"btn\"]//following::button[3]")
    private WebElement openSubtypesTableBtn;

    @FindBy(xpath = "//*[@class=\"btn\"]//following::button[4]")
    private WebElement openUpdateBtn;

    @FindBy(xpath = "//*[@class=\"btn btn-outline-secondary calendar\"]")
    private WebElement addSubtypeBtn;

    @FindBy(xpath = "//*[@id=\"subtype\"]")
    private WebElement subtype;

    @FindBy(xpath = "//*[@id=\"subtypes\"]//li")
    private WebElement subtypeEl;

    @FindBy(xpath = "//*[text()=\"Create new\"]")
    private WebElement createBtn;

    @FindBy(xpath = "//*[@aria-label=\"Next\"]")
    private WebElement nextPage;

    @FindBy(xpath = "//*[@class=\"btn\"]//following::button[5]")
    private WebElement deleteBtn;


    public OfferTypesPage(){

    }

    public OfferTypesPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedOfferTypeTable() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("offer_types")));
    }

    public void ensureIsDisplayedAddForm() {
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@aria-labelledby=\"add-offer-type\"]")));
    }

    public void ensureIsDisplayedUpdateForm() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@aria-labelledby=\"update-offer-type\"]")));
    }

    public void ensureIsDisplayedSubtypeEl() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"subtypes\"]//li")));
    }

    public void ensureIsDisplayedOfferType() {
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@class=\"btn\"]//following::button[5]")));
    }

    public void ensureIsNotDisplayedOfferType() {
        (new WebDriverWait(driver, 40)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//th//following::tr"),1));
    }

    public void ensureIsDisplayedUpdatedOfferType(String newName){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//*[text()=\"" + newName + "\"]"),1));
    }

    public void ensureIsDisplayedErrorMessage(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//small[@class=\"text-danger\"]"),1));
    }

    public WebElement getName() {
        return name;
    }

    public WebElement getOpenSubtypesTableBtn() {
        return openSubtypesTableBtn;
    }

    public WebElement getAddBtn() {
        return addBtn;
    }

    public WebElement getAddSubtypeBtn() {
        return addSubtypeBtn;
    }

    public WebElement getDeleteBtn() {
        return deleteBtn;
    }

    public WebElement getSubtype() {
        return subtype;
    }

    public WebElement getSubtypeEl() {
        return subtypeEl;
    }

    public WebElement getCreateBtn() {
        return createBtn;
    }

    public WebElement getUpdateBtn() {
        return updateBtn;
    }

    public WebElement getOpenUpdateBtn() {
        return openUpdateBtn;
    }

    public void nextPage(int page) {
        for(int i = 0; i < page - 1; i++)
            nextPage.click();
    }
}
