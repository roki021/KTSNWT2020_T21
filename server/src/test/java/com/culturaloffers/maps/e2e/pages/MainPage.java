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

    @FindBy(xpath = "//*[@id=\"comments-link\"]")
    private WebElement commentsTab;

    @FindBy(xpath = "//*[@id=\"news-link\"]")
    private WebElement newsTab;

    @FindBy(xpath = "//*[@class=\"btn\"]")
    private WebElement openNewsViewBtn1;

    @FindBy(xpath = "//*[@class=\"btn\"]//following::button[1]")
    private WebElement openNewsViewBtn2;

    @FindBy(xpath = "//*[@id=\"back-btn\"]")
    private WebElement backToAllNewsBtn;

    @FindBy(xpath = "//*[@id=\"add-comment\"]")
    private WebElement addCommentBtn;

    @FindBy(xpath = "//*[@id=\"submit-comment\"]")
    private WebElement submitCommentButton;

    @FindBy(xpath = "//*[@id=\"content\"]")
    private WebElement commentContent;

    @FindBy(xpath = "//*[@id=\"6\"]")
    private WebElement deleteCommentButton;

    @FindBy(xpath = "//*[@id=\"grading\"]")
    private WebElement gradingElement;

    @FindBy(xpath = "//*[@id=\"searchField\"]")
    private WebElement searchField;

    @FindBy(xpath = "//*[@id=\"searchValue\"]")
    private WebElement searchValue;

    @FindBy(xpath = "//*[@id=\"searchBtn\"]")
    private WebElement searchBtn;

    @FindBy(xpath = "//*[@id=\"discardBtn\"]")
    private WebElement discardBtn;

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

    public void ensureIsDisplayedOfferNewsView() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("news")));
    }

    public void ensureIsDisplayedNewsDesc(String desc){
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//*[text()=\"" + desc + "\"]"),1));
    }

    public void ensureIsDisplayedOffewTitle() {
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.elementToBeClickable(By.id("offer-title")));
    }

    public void ensureIsNumOfOffersDisplayed(int numOfOffers){
        (new WebDriverWait(driver, 30)).until(ExpectedConditions.numberOfElementsToBe(By.xpath("//[contains(text(), \"Location\")]"), numOfOffers));
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

    public void ensureIsDisplayedAddCommentButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("add-comment")));
    }

    public void ensureIsDisplayedSubmitCommentButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("submit-comment")));
    }

    public void ensureIsDisplayedSContent() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("content")));
    }

    public void ensureNotDisplayedSubmitCommentButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("submit-comment")));
    }

    public void ensureNotDisplayedDeletedComment() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("6")));
    }

    public void ensureDisplayedGradingComponent() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.elementToBeClickable(By.id("grading")));
    }

    public void ensureNotDisplayedGradingComponent() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("grading")));
    }

    public void ensureNotDisplayedAddCommentButton() {
        (new WebDriverWait(driver, 10)).until(ExpectedConditions.invisibilityOfElementLocated(By.id("add-comment")));
    }

    public void clickOnFeature(double lon, double lat) throws InterruptedException {
        ((JavascriptExecutor)driver).executeScript(String.format("window.execClick(%s, %s);", lon + "", lat + ""));
    }

    public void nextPage(int page) {
        for(int i = 0; i < page - 1; i++)
            nextPage.click();
    }

    public WebElement getNewsTab() {
        return newsTab;
    }

    public WebElement getOpenNewsViewBtn1() {
        return openNewsViewBtn1;
    }

    public WebElement getOpenNewsViewBtn2() {
        return openNewsViewBtn2;
    }

    public WebElement getBackToAllNewsBtn() {
        return backToAllNewsBtn;
    }

    public WebElement getSearchField() {
        return searchField;
    }

    public WebElement getSearchValue() {
        return searchValue;
    }

    public WebElement getSearchBtn() {
        return searchBtn;
    }

    public WebElement getDiscardBtn() {
        return discardBtn;
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

    public WebElement getCommentsTab() {
        return commentsTab;
    }

    public WebElement getAddCommentBtn() {
        return addCommentBtn;
    }

    public WebElement getSubmitCommentButton() {
        return submitCommentButton;
    }

    public WebElement getCommentContent() {
        return commentContent;
    }

    public WebElement getDeleteCommentButton() {
        return deleteCommentButton;
    }

    public WebElement getGradingElement() {
        return gradingElement;
    }
}
