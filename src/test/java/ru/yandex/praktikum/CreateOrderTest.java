package ru.yandex.praktikum;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.constant.ScooterColors;
import ru.yandex.praktikum.pages.CreateOrderPage;
import ru.yandex.praktikum.pages.MainPage;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;


//Позитивные тесты создания заказа
@RunWith(Parameterized.class)
public class CreateOrderTest {

    @Rule
    public final DriverFactory driverFactory = new DriverFactory();

    //Тип для определения входа в сценарий создания заказа
    private enum EntryPoint {
        HEADER, FOOTER
    }

    private WebDriver driver;
    private MainPage mainPage;
    private CreateOrderPage createOrderPage;
    private final EntryPoint entryPoint;
    private final String nameValue;
    private final String lastnameValue;
    private final String addressValue;
    private final int metroValue;
    private final String phoneValue;
    private final String dateValue;
    private final String termValue;
    private final ScooterColors[] colorsValue;
    private final String commentValue;

    public CreateOrderTest(EntryPoint entryPoint, String nameValue, String lastnameValue,
                           String addressValue, int metroValue, String phoneValue, String dateValue,
                           String termValue, ScooterColors[] colorsValue, String commentValue) {
        this.entryPoint = entryPoint;
        this.nameValue = nameValue;
        this.lastnameValue = lastnameValue;
        this.addressValue = addressValue;
        this.metroValue = metroValue;
        this.phoneValue = phoneValue;
        this.dateValue = dateValue;
        this.termValue = termValue;
        this.colorsValue = colorsValue;
        this.commentValue = commentValue;
    }

    @Before
    public void setUp() {
        driver = driverFactory.getDriver();
        mainPage = new MainPage(driver);
        createOrderPage = new CreateOrderPage(driver);
        mainPage.openPage();
    }

    @Parameterized.Parameters(name = "entryPoint = {0}, name = {1}, lastname = {2}, address = {3}, " +
            "metro = {4}, phone = {5}, date = {6}, term = {7}, color = {8}, comment = {9}")
    public static Object[][] data() {
        return new Object[][] {
                {EntryPoint.HEADER, "Иван", "Иванов", "Тестовый адрес 1", 12, "+79998887766",
                        "24.04.2025", "сутки", new ScooterColors[]{ScooterColors.BLACK}, "тест"},
                {EntryPoint.HEADER, "Олег", "Олегов", "Тестовый адрес 2", 23, "79998887766", "28.04.2025",
                        "трое суток", new ScooterColors[]{ScooterColors.BLACK, ScooterColors.GREY}, "тест"},
                {EntryPoint.HEADER, "Илон", "Маск", "город 1, улица 2", 44, "+79898887766",
                        "30.05.2025", "двое суток", new ScooterColors[]{ScooterColors.GREY}, "ну тест и тест"},
                {EntryPoint.HEADER, "тест", "тест", "", 0, "79993437766", "11.11.2026",
                        "семеро суток", new ScooterColors[]{}, ""},
                {EntryPoint.FOOTER, "Иван", "Иванов", "Тестовый адрес 1", 12, "+79998887766",
                        "24.04.2025", "сутки", new ScooterColors[]{ScooterColors.BLACK}, "тест"},
                {EntryPoint.FOOTER, "Олег", "Олегов", "Тестовый адрес 2", 23, "79998887766", "28.04.2025",
                        "трое суток", new ScooterColors[]{ScooterColors.BLACK, ScooterColors.GREY}, "тест"},
                {EntryPoint.FOOTER, "Илон", "Маск", "город 1, улица 2", 44, "+79898887766",
                        "30.05.2025", "двое суток", new ScooterColors[]{ScooterColors.GREY}, "ну тест и тест"},
                {EntryPoint.FOOTER, "тест", "тест", "", 0, "79993437766", "11.11.2026",
                        "семеро суток", new ScooterColors[]{}, ""},
        };
    }

    @Test
    //Проверка создания заказа
    public void createOrderTest() {
        mainPage.acceptCookies();
        //От значения HEADER или FOOTER типа EntryPoint определяется вход в сценарий создания заказа
        switch (entryPoint) {
            case HEADER:
                mainPage.clickButtonOrderHeader();
                break;
            case FOOTER:
                mainPage.clickButtonOrderFooter();
                break;
        }
        //Заполнение формы
        createOrderPage.createOrder(nameValue, lastnameValue, addressValue,
                metroValue, phoneValue, dateValue, termValue, colorsValue, commentValue);
        assertThat("Заказ не был оформлен", createOrderPage.successCreateOrderText(), containsString("Заказ оформлен"));
    }
}