package com.culturaloffers.maps.e2e.tests;

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
public class SearchBarE2ETest {

    private WebDriver driver;
    private MainPage mainPage;
    public static final String BASE_URL = "http://localhost:4200";

    @Before
    public void setUp() throws InterruptedException {

        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        mainPage = PageFactory.initElements(driver, MainPage.class);

        driver.get(BASE_URL);

        justWait();
    }

    @Test
    public void findByTitle() throws InterruptedException{
        mainPage.getSearchField().sendKeys("title");
        mainPage.getSearchValue().sendKeys("Akva");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        this.mainPage.ensureIsNumOfOffersDisplayed(1);
        justWait();
        this.mainPage.getDiscardBtn().click();
        justWait();
        justWait();
        justWait();
    }

    @Test
    public void findByType() throws InterruptedException{
        mainPage.getSearchField().sendKeys("type");
        mainPage.getSearchValue().sendKeys("park");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.ensureIsNumOfOffersDisplayed(2);
        justWait();
        justWait();
        justWait();
    }

    @Test
    public void findBySubytype() throws InterruptedException{
        mainPage.getSearchField().sendKeys("subtype");
        mainPage.getSearchValue().sendKeys("nacionalni");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.ensureIsNumOfOffersDisplayed(1);
        justWait();
        justWait();
        justWait();
    }

    @Test
    public void findByGrade() throws InterruptedException{
        mainPage.getSearchField().sendKeys("grade");
        mainPage.getSearchValue().sendKeys("2");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.ensureIsNumOfOffersDisplayed(4);
        justWait();
        mainPage.getSearchField().sendKeys("grade");
        mainPage.getSearchValue().sendKeys("4");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.ensureIsNumOfOffersDisplayed(3);
        justWait();
        mainPage.getSearchField().sendKeys("grade");
        mainPage.getSearchValue().sendKeys("9");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.ensureIsNumOfOffersDisplayed(2);
        justWait();
        mainPage.getSearchField().sendKeys("grade");
        mainPage.getSearchValue().sendKeys("2");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.ensureIsNumOfOffersDisplayed(1);
        justWait();
        mainPage.getSearchField().sendKeys("grade");
        mainPage.getSearchValue().sendKeys("13");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.ensureIsNumOfOffersDisplayed(0);
        justWait();
        justWait();
        justWait();
    }

    @Test
    public void findBySubscribers() throws InterruptedException{
        mainPage.getSearchField().sendKeys("subscribers");
        mainPage.getSearchValue().sendKeys("1");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.ensureIsNumOfOffersDisplayed(5);
        justWait();
        mainPage.getSearchField().sendKeys("subscribers");
        mainPage.getSearchValue().sendKeys("2");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.ensureIsNumOfOffersDisplayed(1);
        justWait();
        mainPage.getSearchField().sendKeys("subscribers");
        mainPage.getSearchValue().sendKeys("3");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.ensureIsNumOfOffersDisplayed(0);
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
