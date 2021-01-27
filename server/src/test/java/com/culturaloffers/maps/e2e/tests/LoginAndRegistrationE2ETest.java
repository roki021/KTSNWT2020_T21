package com.culturaloffers.maps.e2e.tests;

import com.culturaloffers.maps.e2e.pages.LoginPage;
import com.culturaloffers.maps.e2e.pages.MainPage;
import com.culturaloffers.maps.e2e.pages.ProfilePage;
import com.culturaloffers.maps.e2e.pages.RegistrationPage;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource("classpath:test.properties")
public class LoginAndRegistrationE2ETest {

    private WebDriver driver;
    private LoginPage loginPage;
    private MainPage mainPage;
    private RegistrationPage registrationPage;
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
        registrationPage = PageFactory.initElements(driver, RegistrationPage.class);



        driver.get(BASE_URL + "/login");

        justWait();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void validLogin() throws InterruptedException {
        loginPage.getUsername().sendKeys("perica");

        loginPage.getPassword().sendKeys("12345");

        justWait();

        loginPage.getLoginBtn().click();

        loginPage.ensureIsNotVisibleErrorMessage();

        loginPage.ensureIsNotVisibleLoginBtn();

        mainPage.ensureIsDisplayedMap();

        justWait();

    }

    @Test
    public void invalidLogin() throws InterruptedException {
        loginPage.getUsername().sendKeys("perica");

        loginPage.getPassword().sendKeys("123456");

        justWait();

        loginPage.getLoginBtn().click();

        loginPage.ensureIsDisplayedErrorMessage();

        justWait();

    }

    @Test
    public void validRegistration() throws InterruptedException {
        driver.navigate().to(BASE_URL + "/register");

        registrationPage.getFirstname().sendKeys("Donald");
        registrationPage.getLastname().sendKeys("Trump");
        registrationPage.getEmailaddress().sendKeys("acanikolic021@yahoo.com");
        registrationPage.getUsername().sendKeys("realDonaldTrump");
        registrationPage.getPassword().sendKeys("iWon");
        registrationPage.getRepeatpassword().sendKeys("iWon");

        justWait();

        registrationPage.getRegbtn().click();

        justWait();
        justWait();
        justWait();

        registrationPage.ensureIsNotVisibleErrorMessage();
        registrationPage.ensureIsNotVisibleRegBtn();

        driver.navigate().to(BASE_URL + "/postregistration");

        justWait();

    }

    @Test
    public void invalidRegistration() throws InterruptedException {
        driver.navigate().to(BASE_URL + "/register");

        registrationPage.getFirstname().sendKeys("Donald");
        registrationPage.getLastname().sendKeys("Trump");
        registrationPage.getEmailaddress().sendKeys("acanikolic0211@yahoo.com");
        registrationPage.getUsername().sendKeys("perica");
        registrationPage.getPassword().sendKeys("iWon");
        registrationPage.getRepeatpassword().sendKeys("iWon");

        justWait();

        registrationPage.getRegbtn().click();

        justWait();
        justWait();
        justWait();

        registrationPage.ensureIsDisplayedErrorMessage();

        justWait();

    }

    private void justWait() throws InterruptedException {
        synchronized (driver)
        {
            driver.wait(1000);
        }
    }
}
