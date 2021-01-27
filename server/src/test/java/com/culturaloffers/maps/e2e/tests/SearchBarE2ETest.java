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
        justWait();
        this.mainPage.clickOnFeature(  21.859188584394833, 43.64599952369584);
        justWait();
        justWait();
        this.mainPage.ensureIsDisplayedOffewView();
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
        this.mainPage.clickOnFeature(  21.859188584394833, 43.64599952369584);
        justWait();
        justWait();
        this.mainPage.ensureIsDisplayedOffewView();
        justWait();
        this.mainPage.clickOnFeature(  21.859188584394833, 48.64599952369584);
        justWait();
        this.mainPage.clickOnFeature(  20.8102511883379, 43.28627472234662);
        justWait();
        justWait();
        this.mainPage.ensureIsDisplayedOffewView();
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
        this.mainPage.clickOnFeature(  20.8102511883379, 43.28627472234662);
        justWait();
        justWait();
        justWait();
    }

    @Test
    public void findByGrade() throws InterruptedException{
        mainPage.getSearchField().sendKeys("grade");
        mainPage.getSearchValue().sendKeys("7");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.clickOnFeature(  19.70962757488346, 45.157654398690845);
        justWait();
        justWait();
        this.mainPage.ensureIsDisplayedOffewView();
        justWait();
        this.mainPage.clickOnFeature(  19.70962757488346, 48.157654398690845);
        justWait();
        this.mainPage.clickOnFeature(  19.844731557466783, 45.25624379011394);
        justWait();
        justWait();
        this.mainPage.ensureIsDisplayedOffewView();
        justWait();
        this.mainPage.clickOnFeature(  19.844731557466783, 48.25624379011394);
        justWait();
        this.mainPage.clickOnFeature(  20.45481367582142, 44.81720352816433);
        justWait();
        justWait();
        this.mainPage.ensureIsDisplayedOffewView();
        justWait();
        this.mainPage.clickOnFeature(  20.45481367582142, 48.81720352816433);
        justWait();
        mainPage.getSearchField().sendKeys("grade");
        mainPage.getSearchValue().clear();
        mainPage.getSearchValue().sendKeys("10");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.clickOnFeature(  20.45481367582142, 44.81720352816433);
        justWait();
        justWait();
        this.mainPage.ensureIsDisplayedOffewView();
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
        this.mainPage.nextPage(2);
        justWait();
        this.mainPage.clickOnFeature(  20.89215244206552, 43.612308109766715);
        justWait();
        justWait();
        this.mainPage.ensureIsDisplayedOffewView();
        justWait();
        this.mainPage.clickOnFeature(  20.89215244206552, 48.612308109766715);
        justWait();
        mainPage.getSearchField().sendKeys("subscribers");
        mainPage.getSearchValue().clear();
        mainPage.getSearchValue().sendKeys("2");
        justWait();
        this.mainPage.getSearchBtn().click();
        justWait();
        justWait();
        this.mainPage.clickOnFeature(  20.89215244206552, 43.612308109766715);
        justWait();
        justWait();
        this.mainPage.ensureIsDisplayedOffewView();
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
