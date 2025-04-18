package ru.yandex.praktikum.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.praktikum.constant.EnvConf;
import ru.yandex.praktikum.constant.ScooterColors;

import java.time.Duration;


// Страница создания заказа "Для кого самокат" и "Про аренду"
public class CreateOrderPage {
    private final WebDriver driver;

    //Поле "Имя"
    private final By nameInput = By.xpath(".//input[@placeholder='* Имя']");
    //Поле "Фамилия"
    private final By lastnameInput = By.xpath(".//input[@placeholder='* Фамилия']");
    //Поле "Адрес"
    private final By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    //Поле "Метро"
    private final By metroInput = By.className("select-search__input");
    //Поле "Телефон"
    private final By phoneInput = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    //Кнопка "Далее" на странице создания заказа "Для кого самокат"
    private final By buttonCreateOrderNextInput = By.className("Button_Middle__1CSJM");
    //Поле "Дата"
    private final By dateInput = By.xpath(".//*[@class='react-datepicker__input-container']//input");
    //Выбранная дата в календаре в поле "Дата"
    private final By dateInputSelected = By.className("react-datepicker__day--selected");
    //Поле "Срок аренды"
    private final By termInput = By.className("Dropdown-placeholder");
    //Чекбоксы цвет самоката ("черный жемчуг" и "серая безысходность")
    private final By colorBlackCheckbox = By.id("black");
    private final By colorGrayCheckbox = By.id("grey");
    //Поле "Комментарий для курьера"
    private final By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    //Кнопка "Заказать"
    private final By buttonCreateOrder = By.xpath(".//*[@class='Order_Buttons__1xGrp']//*[text()='Заказать']");
    //Кнопка подтверждения заказа
    private final By buttonConfirmCreateOrder = By.xpath(".//*[@class='Order_Buttons__1xGrp']//*[text()='Да']");
    //Заголовок элемента с подтверждением заказа и заказ оформлен
    private final By successCreateOrderElement = By.className("Order_ModalHeader__3FDaJ");
    //Сообщение об ошибке в поле "Имя"
    private final By nameErrorMessage = By.cssSelector(".Input_ErrorMessage__3HvIb.Input_Visible___syz6");
    //Сообщение об ошибке в поле "Фамилия"
    private final By lastnameErrorMessage = By.xpath(".//input[@placeholder='* Фамилия']//parent::div//*[@class='Input_ErrorMessage__3HvIb Input_Visible___syz6']");
    //Сообщение об ошибке в поле "Адрес"
    private final By addressErrorMessage = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']//parent::div//*[@class='Input_ErrorMessage__3HvIb Input_Visible___syz6']");
    //Сообщение об ошибке в поле "Метро"
    private final By metroErrorMessage = By.className("Order_MetroError__1BtZb");
    //Сообщение об ошибке в поле "Телефон"
    private final By phoneErrorMessage = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']//parent::div//*[@class='Input_ErrorMessage__3HvIb Input_Visible___syz6']");

    //Каждой станции соответствует значение от 0 до 224, data-value отличается от data-index начиная с 79 data-index
    public By getMetroValue(int index) {
        //Список со станциями
        String metroValuePattern = ".//*[@class='select-search__options']//*[@data-index='%d']";
        return By.xpath(String.format(metroValuePattern, index));
    }

    //Выбор срока аренды, где срок аренды = termValue
    public By getTermValue(String termValue) {
        //Список со сроком аренды
        String termValuePattern = ".//*[@class='Dropdown-menu']//div[text()='%s']";
        return By.xpath(String.format(termValuePattern, termValue));
    }

    public CreateOrderPage(WebDriver driver) {
        this.driver = driver;
    }

    //Ввод значения в поле "Имя"
    public void enterNameValues(String nameValue) {
        driver.findElement(nameInput).sendKeys(nameValue);
    }

    //Ввод значения в поле "Фамилия"
    public void enterLastnameValues(String lastnameValue) {
        driver.findElement(lastnameInput).sendKeys(lastnameValue);
    }

    //Ввод значения в поле "Адрес"
    public void enterAddressValues(String addressValue) {
        driver.findElement(addressInput).sendKeys(addressValue);
    }

    //Ввод значения в поле "Метро"
    public void enterMetroValues(int metroDataValue) {
        driver.findElement(metroInput).click();
        //Ждем пока список раскроется
        new WebDriverWait(driver, Duration.ofSeconds(EnvConf.EXPLICIT_TIMEOUT)).
                until(ExpectedConditions.visibilityOfElementLocated(getMetroValue(metroDataValue)));
        //Находим в списке станцию по индексу и кликаем
        driver.findElement(getMetroValue(metroDataValue)).click();
    }

    //Ввод значения в поле "Телефон"
    public void enterPhoneValues(String phoneValue) {
        driver.findElement(phoneInput).sendKeys(phoneValue);
    }

    public void clickButtonCreateOrderNextInput() {
        driver.findElement(buttonCreateOrderNextInput).click();
    }

    //Ввод значения в поле "Дата"
    public void enterDateValues(String dateValue) {
        driver.findElement(dateInput).click();
        driver.findElement(dateInput).sendKeys(dateValue);
        driver.findElement(dateInputSelected).click();
    }

    //Поиск нужного срока аренды и его выбор в поле "Срок аренды"
    public void enterTermValues(String termValue) {
        driver.findElement(termInput).click();
        driver.findElement(getTermValue(termValue)).click();
    }

    //Проходим по переданному массиву типа enum с цветами самоката и ставим чекбоксы
    public void enterColorValues(ScooterColors[] colorValues) {
        for (ScooterColors colorValue : colorValues) {
            if (colorValue.equals(ScooterColors.BLACK)) {
                driver.findElement(colorBlackCheckbox).click();
            } else if (colorValue.equals(ScooterColors.GREY)) {
                driver.findElement(colorGrayCheckbox).click();
            }
        }
    }

    //Ввод значения в поле "Комментарий"
    public void enterCommentValues(String commentValue) {
        driver.findElement(commentInput).click();
        driver.findElement(commentInput).sendKeys(commentValue);
    }

    public void clickButtonCreateOrder() {
        driver.findElement(buttonCreateOrder).click();
    }

    public void clickButtonConfirmCreateOrder() {
        driver.findElement(buttonConfirmCreateOrder).click();
    }

    //Заполнение всей формы и создание заказа
    public void createOrder(String nameValue, String lastnameValue, String addressValue,
                                 int metroValue, String phoneValue, String dateValue, String termValue,
                                 ScooterColors[] colorsValue, String commentValue) {
        enterOrderInputFirst(nameValue, lastnameValue, addressValue, metroValue, phoneValue);
        clickButtonCreateOrderNextInput();
        //Ждем загрузки страницы
        new WebDriverWait(driver, Duration.ofSeconds(EnvConf.EXPLICIT_TIMEOUT)).
                until(ExpectedConditions.visibilityOfElementLocated(dateInput));
        enterDateValues(dateValue);
        enterTermValues(termValue);
        enterColorValues(colorsValue);
        enterCommentValues(commentValue);
        clickButtonCreateOrder();
        clickButtonConfirmCreateOrder();
    }

    //Заполнение первой части формы создания заказа "Для кого самокат"
    public void enterOrderInputFirst(String nameValue, String lastnameValue, String addressValue,
                                     int metroValue, String phoneValue) {
        enterNameValues(nameValue);
        enterLastnameValues(lastnameValue);
        enterAddressValues(addressValue);
        enterMetroValues(metroValue);
        enterPhoneValues(phoneValue);
    }

    //Возвращаем текст элемента с классом Order_ModalHeader__3FDaJ, элемент при оформлении заказа не меняется, меняется только текст
    public String successCreateOrderText() {
        return driver.findElement(successCreateOrderElement).getText();
    }

    public Boolean isNameErrorMessageDisplayed() {
        return driver.findElement(nameErrorMessage).isDisplayed();
    }

    public String actualNameErrorText() {
        return driver.findElement(nameErrorMessage).getText();
    }

    public Boolean isLastnameErrorMessageDisplayed() {
        return driver.findElement(lastnameErrorMessage).isDisplayed();
    }

    public String actualLastnameErrorText() {
        return driver.findElement(lastnameErrorMessage).getText();
    }

    public Boolean isAddressErrorMessageDisplayed() {
        return driver.findElement(addressErrorMessage).isDisplayed();
    }

    public String actualAddressErrorText() {
        return driver.findElement(addressErrorMessage).getText();
    }

    public Boolean isMetroErrorMessageDisplayed() {
        return driver.findElement(metroErrorMessage).isDisplayed();
    }

    public String actualMetroErrorText() {
        return driver.findElement(metroErrorMessage).getText();
    }

    public Boolean isPhoneErrorMessageDisplayed() {
        return driver.findElement(phoneErrorMessage).isDisplayed();
    }

    public String actualPhoneErrorText() {
        return driver.findElement(phoneErrorMessage).getText();
    }
}
