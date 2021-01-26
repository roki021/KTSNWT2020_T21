package com.culturaloffers.maps.e2e.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CulturalOfferPage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"name\"]")
    private WebElement name;

    @FindBy(xpath = "//*[text()=\"Add\"]")
    private WebElement addBtn;

    @FindBy(xpath = "//*[text()=\"Update\"]")
    private WebElement updateBtn;

    @FindBy(xpath = "//*[@class=\"btn\"]//following::button[3]")
    private WebElement openNewsTableBtn;

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

    public CulturalOfferPage(WebDriver driver) {
        this.driver = driver;
    }

    public CulturalOfferPage() {
    }


}
