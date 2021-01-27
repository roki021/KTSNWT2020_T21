package com.culturaloffers.maps.e2e.tests;

import com.culturaloffers.maps.e2e.pages.LoginPage;
import com.culturaloffers.maps.e2e.pages.MainPage;
import com.culturaloffers.maps.e2e.pages.ProfilePage;
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
public class CommentAndGradeE2ETest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ProfilePage profilePage;
    private MainPage mainPage;
    public static final String BASE_URL = "http://localhost:4200";

    @Before
    public void setUp() throws InterruptedException {

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
    public void addAndDeleteComment() throws InterruptedException {
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
        mainPage.nextPage(2);
        justWait();
        mainPage.clickOnFeature(19.70962757488346, 45.157654398690845);
        justWait();

        mainPage.ensureIsDisplayedOffewView();
        mainPage.getCommentsTab().click();
        mainPage.ensureIsDisplayedAddCommentButton();

        justWait();

        mainPage.getAddCommentBtn().click();

        justWait();

        mainPage.ensureIsDisplayedSubmitCommentButton();
        mainPage.ensureIsDisplayedSContent();
        mainPage.getCommentContent().sendKeys("Izuzetna planina");

        justWait();

        mainPage.getSubmitCommentButton().click();

        justWait();

        mainPage.ensureNotDisplayedSubmitCommentButton();
        mainPage.getDeleteCommentButton().click();

        justWait();

        mainPage.ensureNotDisplayedDeletedComment();

        justWait();
    }

    @Test
    public void gradeOffer() throws InterruptedException {
        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
        mainPage.nextPage(2);
        justWait();
        mainPage.clickOnFeature(19.70962757488346, 45.157654398690845);
        justWait();

        mainPage.ensureIsDisplayedOffewView();
        mainPage.getCommentsTab().click();
        justWait();
        mainPage.ensureDisplayedGradingComponent();
        justWait();
        justWait();
        mainPage.getGradingElement().click();
        justWait();
        justWait();
        mainPage.ensureDisplayedGradingComponent();
        justWait();
    }

    @Test
    public void invalidGradeOffer() throws InterruptedException {
        mainPage.getLogOutBtn().click();
        driver.navigate().to(BASE_URL + "/login");

        loginPage.getUsername().sendKeys("admin");
        loginPage.getPassword().sendKeys("admin");
        loginPage.getLoginBtn().click();

        loginPage.ensureIsNotVisibleLoginBtn();
        mainPage.ensureIsDisplayedMap();

        justWait();
        justWait();

        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
        mainPage.nextPage(2);
        justWait();
        mainPage.clickOnFeature(19.70962757488346, 45.157654398690845);
        justWait();

        mainPage.ensureIsDisplayedOffewView();
        mainPage.getCommentsTab().click();

        justWait();
        justWait();

        mainPage.ensureNotDisplayedGradingComponent();

        justWait();
    }

    @Test
    public void invalidCommentOffer() throws InterruptedException {
        mainPage.getLogOutBtn().click();
        driver.navigate().to(BASE_URL + "/login");

        loginPage.getUsername().sendKeys("admin");
        loginPage.getPassword().sendKeys("admin");
        loginPage.getLoginBtn().click();

        loginPage.ensureIsNotVisibleLoginBtn();
        mainPage.ensureIsDisplayedMap();

        justWait();
        justWait();

        assertEquals("http://localhost:4200/", driver.getCurrentUrl());
        mainPage.nextPage(2);
        justWait();
        mainPage.clickOnFeature(19.70962757488346, 45.157654398690845);
        justWait();

        mainPage.ensureIsDisplayedOffewView();
        mainPage.getCommentsTab().click();

        justWait();
        justWait();

        mainPage.ensureNotDisplayedGradingComponent();

        justWait();
    }

    private void justWait() throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(1000);
        }
    }

}
