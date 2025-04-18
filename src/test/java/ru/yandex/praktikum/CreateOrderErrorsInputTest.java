package ru.yandex.praktikum;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.constant.ErrorsInputText;
import ru.yandex.praktikum.pages.CreateOrderPage;
import ru.yandex.praktikum.pages.MainPage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;

//Тесты появления ошибок в полях создания заказа и текста ошибок
public class CreateOrderErrorsInputTest {

    @Rule
    public final DriverFactory driverFactory = new DriverFactory();

    private WebDriver driver;
    private MainPage mainPage;
    private CreateOrderPage createOrderPage;

    @Before
    public void setUp() {
        driver = driverFactory.getDriver();
        mainPage = new MainPage(driver);
        createOrderPage = new CreateOrderPage(driver);
        mainPage.openPage();
        mainPage.acceptCookies();
        mainPage.clickButtonOrderHeader();
    }

    @Test
    //Проверка появления ошибок на экране "Для кого самокат" и текст ошибок для поля "Имя"
    public void checkNameError() {
        createOrderPage.clickButtonCreateOrderNextInput();
        assertTrue("Нет текста ошибки при пустом поле", createOrderPage.isNameErrorMessageDisplayed());
        assertThat("Текст ошибки в поле \"Имя\" неверный", createOrderPage.actualNameErrorText(), equalTo(ErrorsInputText.NAME_ERROR));
    }

    @Test
    //Проверка появления ошибок на экране "Для кого самокат" и текст ошибок для поля "Фамилия"
    public void checkLastnameError() {
        createOrderPage.clickButtonCreateOrderNextInput();
        assertTrue("Нет текста ошибки при пустом поле", createOrderPage.isLastnameErrorMessageDisplayed());
        assertThat("Текст ошибки в поле \"Фамилия\" неверный", createOrderPage.actualLastnameErrorText(), equalTo(ErrorsInputText.LASTNAME_ERROR));
    }

    @Test
    //Проверка появления ошибок на экране "Для кого самокат" и текст ошибок для поля "Адрес"
    public void checkAddressError() {
        createOrderPage.enterAddressValues("error");
        createOrderPage.clickButtonCreateOrderNextInput();
        assertTrue("Нет текста ошибки при некорректно заполненном поле", createOrderPage.isAddressErrorMessageDisplayed());
        assertThat("Текст ошибки в поле \"Адрес\" неверный", createOrderPage.actualAddressErrorText(), equalTo(ErrorsInputText.ADDRESS_ERROR));
    }

    @Test
    //Проверка появления ошибок на экране "Для кого самокат" и текст ошибок для поля "Метро"
    public void checkMetroError() {
        createOrderPage.clickButtonCreateOrderNextInput();
        assertTrue("Нет текста ошибки при пустом поле", createOrderPage.isMetroErrorMessageDisplayed());
        assertThat("Текст ошибки в поле \"Метро\" неверный", createOrderPage.actualMetroErrorText(), equalTo(ErrorsInputText.METRO_ERROR));
    }

    @Test
    //Проверка появления ошибок на экране "Для кого самокат" и текст ошибок для поля "Телефон"
    public void checkPhoneError() {
        createOrderPage.clickButtonCreateOrderNextInput();
        assertTrue("Нет текста ошибки при пустом поле", createOrderPage.isPhoneErrorMessageDisplayed());
        assertThat("Текст ошибки в поле \"Телефон\" неверный", createOrderPage.actualPhoneErrorText(), equalTo(ErrorsInputText.PHONE_ERROR));
    }

    /*
    public void checkDateError()
    public void checkTermError()
    В дереве нет ошибок для обязательных полей "Дата заказа" и "Срок аренды"
     */
}