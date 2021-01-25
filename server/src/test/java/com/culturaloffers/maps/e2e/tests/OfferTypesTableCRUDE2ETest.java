package com.culturaloffers.maps.e2e.tests;

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
public class OfferTypesTableCRUDE2ETest {
    private WebDriver driver;
    private LoginPage loginPage;
    private OfferTypesPage offerTypesPage;
    private MainPage mainPage;
    public static final String BASE_URL = "http://localhost:4200";

    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        offerTypesPage = PageFactory.initElements(driver, OfferTypesPage.class);

        driver.get(BASE_URL + "/login");

        justWait();

        loginPage.getEmail().sendKeys("admin");

        loginPage.getPassword().sendKeys("admin");

        justWait();

        loginPage.getLoginBtn().click();

        loginPage.ensureIsNotVisibleLoginBtn();

        mainPage.ensureIsDisplayedMap();

        justWait();

        driver.navigate().to(BASE_URL + "/offer-types");
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void AddOfferTypeAndDeleteSuccess() throws InterruptedException {
        offerTypesPage.ensureIsDisplayedOfferTypeTable();
        assertEquals("http://localhost:4200/offer-types", driver.getCurrentUrl());

        justWait();

        offerTypesPage.getAddBtn().click();

        offerTypesPage.ensureIsDisplayedAddForm();

        offerTypesPage.getName().sendKeys("new type");

        justWait();

        offerTypesPage.getSubtype().sendKeys("new subtype");

        offerTypesPage.getAddSubtypeBtn().click();


        justWait();

        offerTypesPage.ensureIsDisplayedSubtypeEl();

        justWait();

        offerTypesPage.getCreateBtn().click();

        offerTypesPage.ensureIsDisplayedOfferTypeTable();

        offerTypesPage.nextPage(3);

        offerTypesPage.ensureIsDisplayedOfferType();

        offerTypesPage.getDeleteBtn().click();

        offerTypesPage.ensureIsNotDisplayedOfferType();
        justWait();
    }

    @Test
    public void AddOfferTypeFail() throws InterruptedException {
        offerTypesPage.ensureIsDisplayedOfferTypeTable();
        assertEquals("http://localhost:4200/offer-types", driver.getCurrentUrl());

        justWait();

        offerTypesPage.getAddBtn().click();

        offerTypesPage.ensureIsDisplayedAddForm();

        offerTypesPage.getName().sendKeys("park");

        justWait();

        offerTypesPage.getSubtype().sendKeys("new subtype");

        offerTypesPage.getAddSubtypeBtn().click();


        justWait();

        offerTypesPage.ensureIsDisplayedSubtypeEl();

        justWait();

        offerTypesPage.getCreateBtn().click();

        offerTypesPage.ensureIsDisplayedErrorMessage();

        justWait();
        justWait();
        justWait();
    }

    @Test
    public void DeleteOfferTypeFail() throws InterruptedException {
        offerTypesPage.ensureIsDisplayedOfferTypeTable();
        assertEquals("http://localhost:4200/offer-types", driver.getCurrentUrl());

        justWait();

        offerTypesPage.ensureIsDisplayedOfferType();

        offerTypesPage.getDeleteBtn().click();

        offerTypesPage.ensureIsDisplayedOfferType();
        offerTypesPage.ensureIsDisplayedErrorMessage();

        justWait();
        justWait();
        justWait();
    }

    @Test
    public void UpdateOfferTypeSuccess() throws InterruptedException {
        offerTypesPage.ensureIsDisplayedOfferTypeTable();
        assertEquals("http://localhost:4200/offer-types", driver.getCurrentUrl());
        String newName = "updated type";
        justWait();

        offerTypesPage.getOpenUpdateBtn().click();

        offerTypesPage.ensureIsDisplayedUpdateForm();

        justWait();
        offerTypesPage.getName().clear();

        justWait();
        offerTypesPage.getName().sendKeys(newName);

        justWait();
        justWait();

        offerTypesPage.getUpdateBtn().click();

        offerTypesPage.ensureIsDisplayedUpdatedOfferType(newName);

        justWait();
        justWait();
        justWait();
    }


    @Test
    public void UpdateOfferTypeFail() throws InterruptedException {
        offerTypesPage.ensureIsDisplayedOfferTypeTable();
        assertEquals("http://localhost:4200/offer-types", driver.getCurrentUrl());
        String newName = "park";
        justWait();

        offerTypesPage.getOpenUpdateBtn().click();

        offerTypesPage.ensureIsDisplayedUpdateForm();

        justWait();
        offerTypesPage.getName().clear();

        justWait();
        offerTypesPage.getName().sendKeys(newName);

        justWait();
        justWait();

        offerTypesPage.getUpdateBtn().click();

        offerTypesPage.ensureIsDisplayedErrorMessage();
        justWait();
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
