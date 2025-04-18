package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.constant.EnvConf;

import java.time.Duration;
import java.util.Objects;


// Главная страница
public class MainPage {
    private final WebDriver driver;

    //Кнопка "да все привыкли" на попапе с куками
    private final By buttonAcceptCookies = By.id("rcc-confirm-button");
    //Кнопка "Заказать" в шапке
    private final By buttonOrderHeader = By.className("Button_Button__ra12g");
    //Кнопка "Заказать" внизу страницы
    private final By buttonOrderFooter = By.className("Button_Middle__1CSJM");
    //"Самокат" в лого
    private final By scooterLogo = By.className("Header_LogoScooter__3lsAR");
    //"Яндекс" в лого
    private final By yandexLogo = By.className("Header_LogoYandex__3TSOI");
    //Изображение "Такого заказа нет"
    private final By imageNotFound = By.cssSelector("img[alt='Not found']");
    //Кнопка "Go" в статусе заказа
    private final By buttonGo = By.className("Header_Button__28dPO");
    //Кнопка "Статус заказа"
    private final By buttonStatus = By.className("Header_Link__1TAG7");
    //Поле ввода номера заказа
    private final By inputOrderId = By.className("Input_Input__1iN_Z");

    //Получение элемента раскрывающего списка по его номеру
    public By getAccordionHeader(int index) {
        String accordionHeaderPattern = "accordion__heading-%d";
        return By.id(String.format(accordionHeaderPattern, index));
    }

    //Получение элемента с текстом внутри раскрывающего списка по его номеру
    public By getAccordionPanelText(int index) {
        String accordionPanelPattern = ".//*[@id='accordion__panel-%d']//p";
        return By.xpath(String.format(accordionPanelPattern, index));
    }

    public By getImageNotFound() {
        return imageNotFound;
    }

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    //Открытие страницы
    public void openPage() {
        driver.get(EnvConf.SCOOTER_URL);
    }

    //Принятие кук, скрывает попап
    public void acceptCookies() {
        driver.findElement(buttonAcceptCookies).click();
    }

    public void clickButtonOrderHeader() {
        driver.findElement(buttonOrderHeader).click();
    }

    public void clickButtonOrderFooter() {
        driver.findElement(buttonOrderFooter).click();
    }

    //Получение текста внутри раскрывающего списка
    public String actualAccordionText(int accordionNumber) {
        driver.findElement(getAccordionHeader(accordionNumber)).click();
        //Ждем анимацию пока список раскроется
        new WebDriverWait(driver, Duration.ofSeconds(EnvConf.EXPLICIT_TIMEOUT)).
                until(ExpectedConditions.visibilityOfElementLocated(getAccordionPanelText(accordionNumber)));
        return driver.findElement(getAccordionPanelText(accordionNumber)).getText();
    }

    //Клик на "Самокат" в логотипе и переход на главную страницу
    public void clickScooterLogo() {
        driver.findElement(scooterLogo).click();
    }

    //Клик на "Яндекс" в логотипе и переключение на активную вкладку
    public void clickYandexLogo() {
        //Запоминаем текущее окно
        String originalWindow = driver.getWindowHandle();
        driver.findElement(yandexLogo).click();
        //Ждем пока окон станет 2
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.numberOfWindowsToBe(2));
        //Переключаемся на второе окно
        for (String windowHandle : driver.getWindowHandles()) {
            if (!windowHandle.equals(originalWindow)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
        //Ждем загрузки страницы для firefox
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> Objects.equals(((JavascriptExecutor) d)
                        .executeScript("return document.readyState"), "complete"));
    }

    public void clickOnStatus() {
        driver.findElement(buttonStatus).click();
    }

    public void enterOrderId(String value) {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConf.EXPLICIT_TIMEOUT))
                .until(ExpectedConditions.visibilityOfElementLocated(inputOrderId));
        driver.findElement(inputOrderId).sendKeys(value);
    }

    public void clickOnGo() {
        new WebDriverWait(driver, Duration.ofSeconds(EnvConf.EXPLICIT_TIMEOUT))
                .until(ExpectedConditions.elementToBeClickable(buttonGo));
        driver.findElement(buttonGo).click();
    }
}