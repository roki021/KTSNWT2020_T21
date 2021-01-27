package com.culturaloffers.maps.e2e.tests;

import com.culturaloffers.maps.e2e.pages.LoginPage;
import com.culturaloffers.maps.e2e.pages.MainPage;
import com.culturaloffers.maps.e2e.pages.OfferNewsPage;
import com.culturaloffers.maps.helper.SmtpServerRule;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
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
public class OfferNewsTableCRUDE2ETest {

    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private OfferNewsPage offerNewsPage;
    public static final String BASE_URL = "http://localhost:4200";

    @Rule
    public SmtpServerRule smtpServerRule = new SmtpServerRule(2525);

    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        offerNewsPage = PageFactory.initElements(driver, OfferNewsPage.class);

        driver.get(BASE_URL + "/login");

        justWait();

        loginPage.getUsername().sendKeys("admin");

        loginPage.getPassword().sendKeys("admin");

        justWait();

        loginPage.getLoginBtn().click();

        loginPage.ensureIsNotVisibleLoginBtn();

        mainPage.ensureIsDisplayedMap();

        justWait();

        driver.navigate().to(BASE_URL + "/news/2");
    }

    @Test
    public void AddOfferNewsSuccess() throws InterruptedException {
        offerNewsPage.ensureIsDisplayedOfferNewsTable();
        assertEquals("http://localhost:4200/news/2", driver.getCurrentUrl());

        justWait();

        offerNewsPage.getAddBtn().click();

        justWait();

        offerNewsPage.getTitle().sendKeys("new title");

        offerNewsPage.getDescription().sendKeys("new desc");

        justWait();

        offerNewsPage.getCreateBtn().click();

        justWait();
        justWait();
        justWait();
        justWait();

        offerNewsPage.ensureAlertAppeared();

        justWait();
        justWait();

        assertEquals(offerNewsPage.getDriver().switchTo().alert().getText(), "Offer news created!");

        justWait();
        justWait();
    }

    @Test
    public void AddOfferNewsFail() throws InterruptedException {
        offerNewsPage.ensureIsDisplayedOfferNewsTable();
        assertEquals("http://localhost:4200/news/2", driver.getCurrentUrl());

        justWait();

        offerNewsPage.getAddBtn().click();

        justWait();

        offerNewsPage.getTitle().sendKeys("");

        offerNewsPage.getDescription().sendKeys("new desc");

        justWait();

        offerNewsPage.getCreateBtn().click();

        justWait();
        justWait();

        offerNewsPage.ensureAlertAppeared();

        assertEquals(offerNewsPage.getDriver().switchTo().alert().getText(), "Some fields are empty or invalid!");

        justWait();
        justWait();
        justWait();
    }

    @Test
    public void UpdateCulturalOffer() throws InterruptedException {
        offerNewsPage.ensureIsDisplayedOfferNewsTable();
        assertEquals("http://localhost:4200/news/2", driver.getCurrentUrl());
        String newTitle = "updated Title";
        justWait();

        offerNewsPage.getOpenUpdateBtn().click();

        justWait();
        offerNewsPage.getTitle().clear();
        offerNewsPage.getTitle().sendKeys(newTitle);

        justWait();

        offerNewsPage.getUpdateBtn().click();

        justWait();
        justWait();

        offerNewsPage.ensureIsDisplayedUpdatedOfferNews(newTitle);

        justWait();
        justWait();
        justWait();
    }

    @Test
    public void DeleteCulturalOffer() throws InterruptedException {
        offerNewsPage.ensureIsDisplayedOfferNewsTable();
        assertEquals("http://localhost:4200/news/2", driver.getCurrentUrl());
        justWait();

        offerNewsPage.getDeleteBtn().click();

        justWait();
        justWait();

        String oldNews = "Ponovno otvaranje parka";
        justWait();
        offerNewsPage.ensureDeletionHappened(oldNews);

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
