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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:test.properties")
public class OfferNewsViewE2ETest {

    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    public static final String BASE_URL = "http://localhost:4200";

    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);

        driver.get(BASE_URL + "/login");

        justWait();

        loginPage.getUsername().sendKeys("perica");

        loginPage.getPassword().sendKeys("12345");

        justWait();

        loginPage.getLoginBtn().click();

        loginPage.ensureIsNotVisibleLoginBtn();

        mainPage.ensureIsDisplayedMap();

        justWait();

        driver.navigate().to(BASE_URL);
    }

    @Test
    public void testOfferNewsView() throws InterruptedException{
        justWait();
        justWait();
        justWait();
        justWait();
        justWait();
        justWait();
        this.mainPage.clickOnFeature(  21.859188584394833, 43.64599952369584);
        justWait();
        justWait();
        justWait();
        this.mainPage.getNewsTab().click();
        justWait();
        justWait();
        this.mainPage.ensureIsDisplayedOfferNewsView();
        justWait();
        justWait();
        this.mainPage.getOpenNewsViewBtn1().click();
        justWait();
        this.mainPage.ensureIsDisplayedNewsDesc("Park se otvara opet od 15.01.2021.");
        justWait();
        justWait();
        this.mainPage.getBackToAllNewsBtn().click();
        justWait();
        this.mainPage.getOpenNewsViewBtn2().click();
        justWait();
        this.mainPage.ensureIsDisplayedNewsDesc("Park ce biti zatvoren od 30.01.2021.");
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
