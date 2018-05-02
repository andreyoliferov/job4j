package ru.job4j.converter;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
        Converter converter = new Converter();
        int result = converter.rubleToDollar(60);
        assertThat(result, is(1));
    }

    /**
     * Конвертирование рублей в евро
     */
    @Test
    public void when70RubleToEuroThen1() {
        Converter converter = new Converter();
        int result = converter.rubleToEuro(70);
        assertThat(result, is(1));
    }

    /**
     * Конвертирование долларов в рубли
     */
    @Test
    public void when2DollarsToRubleThen120() {
        Converter converter = new Converter();
        int result = converter.dollarToRuble(2);
        assertThat(result, is(120));
    }

    /**
     * Конвертирование евро в рубли
     */
    @Test
    public void when3EuroToRubleThen210() {
        Converter converter = new Converter();
        int result = converter.euroToRuble(3);
        assertThat(result, is(210));
    }
}
