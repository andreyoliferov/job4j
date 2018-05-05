package ru.job4j.loop;


import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестирование класса Factorial
 * @autor Андрей
 * @since 05.05.2018
 */
public class FactorialTest {

    /**
     * metod calc
     * факториал 5!
     */
    @Test
    public void whenNFiveReturnFactorial() {
        Factorial factorial = new Factorial();
        int result = factorial.calc(5);
        assertThat(result, is(120));

    }

    /**
     * metod calc
     * факториал 0!
     */
    @Test
    public void whenNZeroReturnFactorial() {
        Factorial factorial = new Factorial();
        int result = factorial.calc(0);
        assertThat(result, is(1));
    }
}
