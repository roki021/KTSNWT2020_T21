package com.culturaloffers.maps.e2e.tests;

import com.culturaloffers.maps.e2e.pages.LoginPage;
import com.culturaloffers.maps.e2e.pages.MainPage;
import com.culturaloffers.maps.e2e.pages.OfferTypesPage;
import com.culturaloffers.maps.e2e.pages.SubtypesPage;
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
public class SubtypesTableCRUDE2ETest {
    private WebDriver driver;
    private LoginPage loginPage;
    private OfferTypesPage offerTypesPage;
    private MainPage mainPage;
    private SubtypesPage subtypesPage;
    public static final String BASE_URL = "http://localhost:4200";

    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();
        loginPage = PageFactory.initElements(driver, LoginPage.class);
        mainPage = PageFactory.initElements(driver, MainPage.class);
        offerTypesPage = PageFactory.initElements(driver, OfferTypesPage.class);
        subtypesPage = PageFactory.initElements(driver, SubtypesPage.class);

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
        offerTypesPage.ensureIsDisplayedOfferTypeTable();
        offerTypesPage.getOpenSubtypesTableBtn().click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void AddSubtypeAndDeleteSuccess() throws InterruptedException {
        subtypesPage.ensureIsDisplayedSubtypeTable();
        assertEquals("http://localhost:4200/offer-types/2/subtypes", driver.getCurrentUrl());

        justWait();

        subtypesPage.getAddBtn().click();

        subtypesPage.ensureIsDisplayedAddForm();

        subtypesPage.getName().sendKeys("new type");

        justWait();

        subtypesPage.getCreateBtn().click();

        subtypesPage.ensureIsDisplayedSubtypeTable();

        subtypesPage.nextPage(2);

        subtypesPage.ensureIsDisplayedSubType();

        subtypesPage.getDeleteBtn().click();

        subtypesPage.ensureIsNotDisplayedSubtype();
        justWait();
    }

    @Test
    public void AddSubtypeFail() throws InterruptedException {

        subtypesPage.ensureIsDisplayedSubtypeTable();
        assertEquals("http://localhost:4200/offer-types/2/subtypes", driver.getCurrentUrl());

        justWait();

        subtypesPage.getAddBtn().click();

        subtypesPage.ensureIsDisplayedAddForm();

        subtypesPage.getName().sendKeys("pozoriste");

        justWait();

        subtypesPage.getCreateBtn().click();

        subtypesPage.ensureIsDisplayedErrorMessage();

        justWait();
        justWait();
        justWait();
    }

    @Test
    public void DeleteSubtypeFail() throws InterruptedException {
        subtypesPage.ensureIsDisplayedSubtypeTable();
        assertEquals("http://localhost:4200/offer-types/2/subtypes", driver.getCurrentUrl());

        justWait();

        subtypesPage.ensureIsDisplayedSubType();

        subtypesPage.getDeleteBtn().click();

        subtypesPage.ensureIsDisplayedSubType();
        subtypesPage.ensureIsDisplayedErrorMessage();

        justWait();
        justWait();
        justWait();
    }

    @Test
    public void UpdateSubtypeSuccess() throws InterruptedException {
        subtypesPage.ensureIsDisplayedSubtypeTable();
        assertEquals("http://localhost:4200/offer-types/2/subtypes", driver.getCurrentUrl());
        String newName = "updated subtype";
        justWait();

        subtypesPage.getOpenUpdateBtn().click();

        subtypesPage.ensureIsDisplayedUpdateForm();

        justWait();
        subtypesPage.getName().clear();

        justWait();
        subtypesPage.getName().sendKeys(newName);

        justWait();
        justWait();

        subtypesPage.getUpdateBtn().click();

        subtypesPage.ensureIsDisplayedUpdatedSubtype(newName);

        justWait();
        justWait();
        justWait();
    }


    @Test
    public void UpdateSubtypeFail() throws InterruptedException {
        subtypesPage.ensureIsDisplayedSubtypeTable();
        assertEquals("http://localhost:4200/offer-types/2/subtypes", driver.getCurrentUrl());
        String newName = "pozoriste";
        justWait();

        subtypesPage.getOpenUpdateBtn().click();

        subtypesPage.ensureIsDisplayedUpdateForm();

        justWait();
        subtypesPage.getName().clear();

        justWait();
        subtypesPage.getName().sendKeys(newName);

        justWait();
        justWait();

        subtypesPage.getUpdateBtn().click();

        subtypesPage.ensureIsDisplayedErrorMessage();
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
