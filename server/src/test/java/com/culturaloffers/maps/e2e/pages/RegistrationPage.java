package com.culturaloffers.maps.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RegistrationPage {
    private WebDriver driver;

    @FindBy(xpath = "//*[@id=\"firstname\"]")
    private WebElement firstname;

    @FindBy(xpath = "//*[@id=\"lastname\"]")
    private WebElement lastname;

    @FindBy(xpath = "//*[@id=\"emailaddress\"]")
    private WebElement emailaddress;

    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement username;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement password;

    @FindBy(xpath = "//*[@id=\"repeatpassword\"]")
    private WebElement repeatpassword;

    @FindBy(xpath = "//*[@id=\"reg-btn\"]")
    private WebElement regbtn;

    @FindBy(xpath = "//*[@id=\"errormsg\"]")
    private WebElement errorMessage;

    public RegistrationPage() {}

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsNotVisibleRegBtn() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("reg-btn")));
    }

    public void ensureIsDisplayedRepPassword() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("repeatpassword")));
    }

    public void ensureIsDisplayedPassword() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("password")));
    }

    public void ensureIsDisplayedUsername() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("username")));
    }

    public void ensureIsDisplayedEmailAddress() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("emailaddress")));
    }

    public void ensureIsDisplayedLastName() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("lastname")));
    }

    public void ensureIsDisplayedFirstName() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("firstname")));
    }

    public void ensureIsDisplayedRegBtn() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("reg-btn")));
    }

    public void ensureIsDisplayedErrorMessage() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("errormsg")));
    }

    public void ensureIsNotVisibleErrorMessage() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("errormsg")));
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebElement getFirstname() {
        return firstname;
    }

    public WebElement getLastname() {
        return lastname;
    }

    public WebElement getEmailaddress() {
        return emailaddress;
    }

    public WebElement getUsername() {
        return username;
    }

    public WebElement getPassword() {
        return password;
    }

    public WebElement getRepeatpassword() {
        return repeatpassword;
    }

    public WebElement getRegbtn() {
        return regbtn;
    }

    public WebElement getErrorMessage() {
        return errorMessage;
    }
}
