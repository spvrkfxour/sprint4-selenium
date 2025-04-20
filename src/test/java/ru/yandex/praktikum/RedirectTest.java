package ru.yandex.praktikum;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.constant.EnvConf;
import ru.yandex.praktikum.pages.MainPage;

import java.time.Duration;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;


//Тесты переходов на главную страницу Яндекса, Самоката и "Такого заказа нет"
public class RedirectTest {

    @Rule
    public final DriverFactory driverFactory = new DriverFactory();

    private WebDriver driver;
    private MainPage mainPage;

    @Before
    public void setUp() {
        driver = driverFactory.getDriver();
        mainPage = new MainPage(driver);
        mainPage.openPage();
    }

    @Test
    public void logoScooterRedirectTest() {
        mainPage.clickButtonOrderHeader();
        mainPage.clickScooterLogo();
        String currentUrl = driver.getCurrentUrl();
        assertThat("Главная страница Самоката не открылась", currentUrl, equalTo(EnvConf.SCOOTER_URL));
    }

    @Test
    public void logoYandexRedirectTest() {
        mainPage.clickYandexLogo();
        String currentUrl = driver.getCurrentUrl();
        assertThat("Главная страница Яндекса не открылась", currentUrl, containsString("ya.ru"));
    }

    @Test
    public void errorOrderTest() {
        mainPage.clickOnStatus();
        mainPage.enterOrderId("1234");
        mainPage.clickOnGo();
        new WebDriverWait(driver, Duration.ofSeconds(EnvConf.EXPLICIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(mainPage.getImageNotFound()));
        assertTrue("Изображение \"Такого заказа нет\" не найдено", driver.findElement(mainPage.getImageNotFound()).isDisplayed());
    }
}