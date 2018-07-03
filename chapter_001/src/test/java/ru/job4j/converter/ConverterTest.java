package ru.job4j.converter;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Тестирование класса Converter
 * @author Андрей Олиферов
 * @since  02.05.2018.
 */
public class ConverterTest {

    /**
     * Конвертирование рублей в доллары
     */
    @Test
    public void when60RubleToDollarThen1() {
        Converter convert = new Converter();
        int result = convert.rubleToDollar(60);
        assertThat(result, is(1));
    }

    /**
     * Конвертирование рублей в евро
     */
    @Test
    public void when70RubleToEuroThen1() {
        Converter convert = new Converter();
        int result = convert.rubleToEuro(70);
        assertThat(result, is(1));
    }

    /**
     * Конвертирование долларов в рубли
     */
    @Test
    public void when2DollarsToRubleThen120() {
        Converter convert = new Converter();
        int result = convert.dollarToRuble(2);
        assertThat(result, is(120));
    }

    /**
     * Конвертирование евро в рубли
     */
    @Test
    public void when3EuroToRubleThen210() {
        Converter convert = new Converter();
        int result = convert.euroToRuble(3);
        assertThat(result, is(210));
    }
}
