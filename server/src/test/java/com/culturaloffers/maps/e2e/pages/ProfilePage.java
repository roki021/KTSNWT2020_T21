package com.culturaloffers.maps.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProfilePage {

    private WebDriver driver;

    @FindBy(xpath = "//*[@name=\"name\"]")
    private WebElement name;

    @FindBy(xpath = "//*[@name=\"email\"]")
    private WebElement email;

    @FindBy(xpath = "//*[@name=\"firstName\"]")
    private WebElement firstName;

    @FindBy(xpath = "//*[@name=\"lastName\"]")
    private WebElement lastName;

    @FindBy(xpath = "//*[text()=\"Update\"]")
    private WebElement updateBtn;

    @FindBy(xpath = "//*[text()=\"Change password\"]")
    private WebElement openChangePassBtn;

    @FindBy(xpath = "//*[text()=\"Change\"]")
    private WebElement changePassBtn;

    @FindBy(xpath = "//*[@name=\"oldPassword\"]")
    private WebElement oldPassword;

    @FindBy(xpath = "//*[@name=\"newPassword\"]")
    private WebElement newPassword;

    @FindBy(xpath = "//*[@name=\"repeated\"]")
    private WebElement repeated;

    @FindBy(xpath = "//app-gen-table//table")
    private WebElement subscriptionTable;

    public ProfilePage(){

    }

    public ProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void ensureIsDisplayedChangePasswordForm() {
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@aria-labelledby=\"change-password\"]")));
    }

    public void ensureIsDisplayedProfileForm() {
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"profile\"]")));
    }

    public void ensureIsDisplayedSubscriptionTable() {
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.elementToBeClickable(By.xpath("//app-gen-table//table")));
    }

    public void ensureIsDisplayedErrorMessage(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//small[@class=\"text-danger\"]"),1));
    }

    public void ensureIsDisplayedUnsubscription(){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//ngb-toast"),1));
    }

    public WebElement getName() {
        return name;
    }

    public WebElement getEmail() {
        return email;
    }

    public WebElement getFirstName() {
        return firstName;
    }

    public WebElement getLastName() {
        return lastName;
    }

    public WebElement getUpdateBtn() {
        return updateBtn;
    }

    public WebElement getOpenChangePassBtn() {
        return openChangePassBtn;
    }

    public WebElement getChangePassBtn() {
        return changePassBtn;
    }

    public WebElement getOldPassword() {
        return oldPassword;
    }

    public WebElement getNewPassword() {
        return newPassword;
    }

    public WebElement getRepeated() {
        return repeated;
    }

    public WebElement getSubscriptionTable() { return subscriptionTable; }
}
