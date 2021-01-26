package com.culturaloffers.maps.e2e.tests;

import com.culturaloffers.maps.e2e.pages.CulturalOfferPage;
import com.culturaloffers.maps.e2e.pages.LoginPage;
import com.culturaloffers.maps.e2e.pages.MainPage;
import com.culturaloffers.maps.e2e.pages.OfferTypesPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:test.properties")
public class CulturalOffersTableCRUDE2ETest {

    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private CulturalOfferPage culturalOffersPage;
    public static final String BASE_URL = "http://localhost:4200";

    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        culturalOffersPage = PageFactory.initElements(driver, CulturalOfferPage.class);

        driver.get(BASE_URL + "/login");

        justWait();

        loginPage.getUsername().sendKeys("admin");

        loginPage.getPassword().sendKeys("admin");

        justWait();

        loginPage.getLoginBtn().click();

        loginPage.ensureIsNotVisibleLoginBtn();

        mainPage.ensureIsDisplayedMap();

        justWait();

        driver.navigate().to(BASE_URL + "/all_offers");
    }

    @Test
    public void AddCulturalOfferSuccess() throws InterruptedException {
        culturalOffersPage.ensureIsDisplayedCulturalOfferTable();
        assertEquals("http://localhost:4200/all_offers", driver.getCurrentUrl());

        justWait();

        culturalOffersPage.getAddBtn().click();

        justWait();

        culturalOffersPage.getTitle().sendKeys("new title");

        culturalOffersPage.getDescription().sendKeys("new desc");

        culturalOffersPage.getAddress().sendKeys("Kragujevac");

        culturalOffersPage.getSubtype().sendKeys("crkva");

        justWait();

        culturalOffersPage.getCreateBtn().click();

        justWait();

        culturalOffersPage.ensureAlertAppeared();

        justWait();

        assertEquals(culturalOffersPage.getDriver().switchTo().alert().getText(), "New cultural offer added!");

        justWait();
        justWait();
    }

    @Test
    public void AddCulturalOfferFail() throws InterruptedException {
        culturalOffersPage.ensureIsDisplayedCulturalOfferTable();
        assertEquals("http://localhost:4200/all_offers", driver.getCurrentUrl());

        justWait();

        culturalOffersPage.getAddBtn().click();

        justWait();

        culturalOffersPage.getTitle().sendKeys("");

        culturalOffersPage.getDescription().sendKeys("new desc");

        culturalOffersPage.getAddress().sendKeys("Kragujevac");

        culturalOffersPage.getSubtype().sendKeys("crkva");

        justWait();

        culturalOffersPage.getCreateBtn().click();

        justWait();
        justWait();

        culturalOffersPage.ensureAlertAppeared();

        assertEquals(culturalOffersPage.getDriver().switchTo().alert().getText(), "Some fields are empty or invalid!");

        justWait();
        justWait();
        justWait();
    }

    @Test
    public void UpdateCulturalOfferSuccess() throws InterruptedException {
        culturalOffersPage.ensureIsDisplayedCulturalOfferTable();
        assertEquals("http://localhost:4200/all_offers", driver.getCurrentUrl());
        String newTitle = "updated Title";
        justWait();

        culturalOffersPage.getOpenUpdateBtn().click();

        justWait();
        culturalOffersPage.getTitle().clear();
        culturalOffersPage.getTitle().sendKeys(newTitle);

        justWait();

        culturalOffersPage.getUpdateBtn().click();

        justWait();
        justWait();

        culturalOffersPage.ensureIsDisplayedUpdatedCulturalOffer(newTitle);

        justWait();
        justWait();
        justWait();
    }


    @Test
    public void UpdateCulturalOfferFail() throws InterruptedException {
        culturalOffersPage.ensureIsDisplayedCulturalOfferTable();
        assertEquals("http://localhost:4200/all_offers", driver.getCurrentUrl());
        String newName = "Hram Svetog Save";
        justWait();

        culturalOffersPage.getOpenUpdateBtn().click();

        justWait();
        culturalOffersPage.getTitle().clear();
        culturalOffersPage.getTitle().sendKeys(newName);

        justWait();

        culturalOffersPage.getUpdateBtn().click();

        justWait();
        justWait();

        culturalOffersPage.ensureIsNotDisplayedUpdatedCulturalOffer(newName);

        justWait();
        justWait();
        justWait();
    }

    @Test
    public void DeleteCulturalOffer() throws InterruptedException {
        culturalOffersPage.ensureIsDisplayedCulturalOfferTable();
        assertEquals("http://localhost:4200/all_offers", driver.getCurrentUrl());
        justWait();

        culturalOffersPage.getDeleteBtn().click();

        justWait();
        justWait();

        String oldOffer = "Akva park Podina Sokobanja";
        justWait();
        culturalOffersPage.ensureDeletionHappened(oldOffer);

        justWait();
        justWait();
        justWait();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    private void justWait() throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(1000);
        }
    }

}
