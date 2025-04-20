package ru.yandex.praktikum;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import ru.yandex.praktikum.constant.AccordionText;
import ru.yandex.praktikum.pages.MainPage;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


//Тесты соответствия текстов внутри списков на главной странице
@RunWith(Parameterized.class)
public class MainPageAccordionTest {

    @Rule
    public final DriverFactory driverFactory = new DriverFactory();

    private WebDriver driver;
    private MainPage mainPage;
    private final int accordionNumber;
    private final String expectedText;

    public MainPageAccordionTest(int accordionNumber, String expectedText) {
        this.accordionNumber = accordionNumber;
        this.expectedText = expectedText;
    }

    @Before
    public void setUp() {
        driver = driverFactory.getDriver();
        mainPage = new MainPage(driver);
        mainPage.openPage();
    }

    @Parameterized.Parameters(name = "accordionNumber = {0}, expectedText = {1}")
    public static Object[][] data() {
        return new Object[][]{
                {0, AccordionText.ANSWER_SCOOTER_COST},
                {1, AccordionText.ANSWER_SEVERAL_SCOOTER_ORDER},
                {2, AccordionText.ANSWER_RENT_TIME},
                {3, AccordionText.ANSWER_SCOOTER_ORDER_TODAY},
                {4, AccordionText.ANSWER_RENT_TIME_EXTEND_OR_REDUCE},
                {5, AccordionText.ANSWER_SCOOTER_ORDER_CHARGER},
                {6, AccordionText.ANSWER_CANCEL_ORDER},
                {7, AccordionText.ANSWER_SCOOTER_ORDER_AREA}
        };
    }

    @Test
    //Проверка, что текст внутри раскрывающего списка соответствует тексту из требований(класс AccordionText)
    public void accordionTextTest() {
        mainPage.acceptCookies();
        assertThat("Текст в раскрывающемся списке неверный", mainPage.actualAccordionText(accordionNumber), equalTo(expectedText));
    }
}