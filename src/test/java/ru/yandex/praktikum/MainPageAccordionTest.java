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
                {0, AccordionText.ACCORDION_TEXT_0},
                {1, AccordionText.ACCORDION_TEXT_1},
                {2, AccordionText.ACCORDION_TEXT_2},
                {3, AccordionText.ACCORDION_TEXT_3},
                {4, AccordionText.ACCORDION_TEXT_4},
                {5, AccordionText.ACCORDION_TEXT_5},
                {6, AccordionText.ACCORDION_TEXT_6},
                {7, AccordionText.ACCORDION_TEXT_7}
        };
    }

    @Test
    //Проверка, что текст внутри раскрывающего списка соответствует тексту из требований(класс AccordionText)
    public void checkAccordionText() {
        mainPage.acceptCookies();
        //Assert.assertEquals("Текст в раскрывающем списке отличается",expectedText, mainPage.actualAccordionText(accordionNumber));
        assertThat("Текст в раскрывающемся списке неверный", mainPage.actualAccordionText(accordionNumber), equalTo(expectedText));
    }
}