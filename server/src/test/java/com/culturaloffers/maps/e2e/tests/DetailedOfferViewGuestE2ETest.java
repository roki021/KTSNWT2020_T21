package com.culturaloffers.maps.e2e.tests;

import com.culturaloffers.maps.e2e.pages.LoginPage;
import com.culturaloffers.maps.e2e.pages.MainPage;
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
public class DetailedOfferViewGuestE2ETest {

    private WebDriver driver;

    private LoginPage loginPage;

    private MainPage mainPage;

    @Before
    public void setUp() throws InterruptedException{

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);

        driver.get("http://localhost:4200/login");

        justWait();

        loginPage.getUsername().sendKeys("perica");
        loginPage.getPassword().sendKeys("12345");
        loginPage.getLoginBtn().click();

        loginPage.ensureIsNotVisibleLoginBtn();
        mainPage.ensureIsDisplayedMap();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void openOfferViewOnMapTestFeature() throws InterruptedException {
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
        mainPage.nextPage(3);
        justWait();
        mainPage.clickOnFeature(19.78767464211861, 45.12657023305386);
        justWait();

        mainPage.ensureIsDisplayedOffewView();

        mainPage.ensureIsDisplayedOffewTitle();

        assertEquals("Red Baron Pub", mainPage.getOfferTitle().getText());
    }

    @Test
    public void openOfferViewOnMapTestNoFeature() throws InterruptedException {
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
        mainPage.clickOnFeature(25.78767464211861, 40.12657023305386);
        justWait();

        mainPage.ensureIsNotVisibleOfferView();
    }

    @Test
    public void openOfferViewInfoMapForSelectedOffer() throws InterruptedException {
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
        mainPage.nextPage(2);
        justWait();
        mainPage.clickOnFeature(19.70962757488346, 45.157654398690845);
        justWait();

        mainPage.ensureIsDisplayedOffewView();
        mainPage.ensureIsDisplayedOffewTitle();

        assertEquals("Fruška gora", mainPage.getOfferLocation().getText());
    }

    @Test
    public void subscribeOnSelectedOffer() throws InterruptedException {
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
        mainPage.nextPage(2);
        justWait();
        mainPage.clickOnFeature(20.539827315594135, 43.37352830947373);
        justWait();

        mainPage.ensureIsDisplayedOffewView();
        mainPage.ensureIsDisplayedOffewTitle();

        assertEquals("Manastir Gradac", mainPage.getOfferTitle().getText());

        mainPage.ensureIsDisplayedSubsButton();
        assertEquals("Subscribe", mainPage.getSubscribeBtn().getText());
        mainPage.getSubscribeBtn().click();
        justWait();
        assertEquals("Unsubscribe", mainPage.getSubscribeBtn().getText());
    }

    @Test
    public void unsubscribeOnSelectedOffer() throws InterruptedException {
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
        justWait();
        mainPage.clickOnFeature(20.8102511883379, 43.28627472234662);
        justWait();

        mainPage.ensureIsDisplayedOffewView();
        mainPage.ensureIsDisplayedOffewTitle();

        assertEquals("Kopaonik nacionalni park", mainPage.getOfferTitle().getText());

        mainPage.ensureIsDisplayedSubsButton();
        justWait();
        assertEquals("Unsubscribe", mainPage.getSubscribeBtn().getText());
        mainPage.getSubscribeBtn().click();
        justWait();
        assertEquals("Subscribe", mainPage.getSubscribeBtn().getText());
    }

    @Test
    public void logOutTest() throws InterruptedException {
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());

        mainPage.ensureIsDisplayedMap();

        mainPage.getLogOutBtn().click();
        justWait();

        mainPage.ensureIsDisplayedLogInButton();
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
    }

    private void justWait() throws InterruptedException {
        justWait(1000);
    }

    private void justWait(int milliseconds) throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(milliseconds);
        }
    }
}
