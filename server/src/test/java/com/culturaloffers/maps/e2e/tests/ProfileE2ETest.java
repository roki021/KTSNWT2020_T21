package com.culturaloffers.maps.e2e.tests;

import com.culturaloffers.maps.e2e.pages.LoginPage;
import com.culturaloffers.maps.e2e.pages.MainPage;
import com.culturaloffers.maps.e2e.pages.OfferTypesPage;
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
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:test.properties")
public class ProfileE2ETest {
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
        profilePage = PageFactory.initElements(driver, ProfilePage.class);

        driver.get(BASE_URL + "/login");

        justWait();


    }

    private void login(String user, String password) throws InterruptedException {
        loginPage.getUsername().sendKeys(user);

        loginPage.getPassword().sendKeys(password);

        justWait();

        loginPage.getLoginBtn().click();

        loginPage.ensureIsNotVisibleLoginBtn();

        mainPage.ensureIsDisplayedMap();

        justWait();

        driver.navigate().to(BASE_URL + "/profile");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void GetProfileData() throws InterruptedException {
        login("bogi", "12345");
        profilePage.ensureIsDisplayedProfileForm();
        assertEquals("http://localhost:4200/profile", driver.getCurrentUrl());

        justWait();

        assertEquals("bogi", profilePage.getName().getAttribute("value"));
        assertEquals("bbogdan@gmail.com", profilePage.getEmail().getAttribute("value"));

        justWait();
        justWait();
    }

    @Test
    public void UpdateProfileSuccess() throws InterruptedException {
        login("zoka61", "12345");
        profilePage.ensureIsDisplayedProfileForm();
        assertEquals("http://localhost:4200/profile", driver.getCurrentUrl());

        justWait();

        profilePage.getName().clear();
        profilePage.getName().sendKeys("test user");

        profilePage.getFirstName().clear();
        profilePage.getFirstName().sendKeys("new first name");

        justWait();

        profilePage.getUpdateBtn().click();

        justWait();

        driver.navigate().to(BASE_URL + "/profile");
        profilePage.ensureIsDisplayedProfileForm();

        assertEquals("new first name", profilePage.getFirstName().getAttribute("value"));
        assertEquals("test user", profilePage.getName().getAttribute("value"));


        justWait();
        justWait();
    }

    @Test
    public void UpdateProfileFail() throws InterruptedException {
        login("gorama77","12345");
        profilePage.ensureIsDisplayedProfileForm();
        assertEquals("http://localhost:4200/profile", driver.getCurrentUrl());

        justWait();

        profilePage.getName().clear();
        profilePage.getName().sendKeys("mikica");

        profilePage.getFirstName().clear();
        profilePage.getFirstName().sendKeys("new first name");

        justWait();

        profilePage.getUpdateBtn().click();

        profilePage.ensureIsDisplayedErrorMessage();

        justWait();
        driver.navigate().to(BASE_URL + "/profile");
        profilePage.ensureIsDisplayedProfileForm();

        assertNotEquals("new first name", profilePage.getFirstName().getAttribute("value"));
        assertNotEquals("mikica", profilePage.getName().getAttribute("value"));

        justWait();
        justWait();
    }

    @Test
    public void ChangePasswordSuccess() throws InterruptedException {
        login("jovana25", "12345");
        profilePage.ensureIsDisplayedProfileForm();
        assertEquals("http://localhost:4200/profile", driver.getCurrentUrl());

        justWait();

        profilePage.getOpenChangePassBtn().click();

        profilePage.ensureIsDisplayedChangePasswordForm();

        justWait();
        profilePage.getOldPassword().sendKeys("12345");

        justWait();
        profilePage.getNewPassword().sendKeys("123456");

        justWait();
        profilePage.getRepeated().sendKeys("123456");


        profilePage.getChangePassBtn().click();

        profilePage.ensureIsDisplayedProfileForm();

        justWait();
        justWait();
    }

    @Test
    public void ChangePasswordFail() throws InterruptedException {
        login("rakica2","12345");
        profilePage.ensureIsDisplayedProfileForm();
        assertEquals("http://localhost:4200/profile", driver.getCurrentUrl());

        justWait();

        profilePage.getOpenChangePassBtn().click();

        profilePage.ensureIsDisplayedChangePasswordForm();

        justWait();
        profilePage.getOldPassword().sendKeys("123457");

        justWait();
        profilePage.getNewPassword().sendKeys("1234");

        justWait();
        profilePage.getRepeated().sendKeys("1234");

        profilePage.getChangePassBtn().click();

        profilePage.ensureIsDisplayedErrorMessage();

        justWait();
        justWait();
    }

    private void justWait() throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(1000);
        }
    }
}
