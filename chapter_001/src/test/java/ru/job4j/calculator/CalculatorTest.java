package ru.job4j.calculator;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Тестирование класса Calculator
 * @author Адрей Олиферов
 * @since 02.05.2018.
 */
public class CalculatorTest {


    /**
     * Сложение.
     */
    @Test
    public void whenAddOnePlusOneThenTwo() {
        Calculator calc = new Calculator();
        calc.add(1D, 1D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }

    /**
     * Вычитание.
     */

    @Test
    public void whenSubtractOneMinusTwoThenMinusOne() {
        Calculator calc = new Calculator();
        calc.subtract(1D, 2D);
        double result = calc.getResult();
        double expected = -1D;
        assertThat(result, is(expected));
    }

    /**
     * Деление.
     */
    @Test
    public void whenDivFourByTwoThenTwo() {
        Calculator calc = new Calculator();
        calc.div(4D, 2D);
        double result = calc.getResult();
        double expected = 2D;
        assertThat(result, is(expected));
    }

    /**
     * Умножение.
     */
    @Test
    public void whenMultipleFourByTwoThenEight() {
        Calculator calc = new Calculator();
        calc.multiple(4D, 2D);
        double result = calc.getResult();
        double expected = 8D;
        assertThat(result, is(expected));
    }

    /**
     * Умножение на 0.
     */
    @Test
    public void whenMultipleFourByNullThenNull() {
        Calculator calc = new Calculator();
        calc.multiple(4D, 0D);
        double result = calc.getResult();
        double expected = 0D;
        assertThat(result, is(expected));
    }
}
