package ru.job4j.max;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестирование класса Max
 * @autor Андрей Олиферов
 * @since 04.05.2018
 */
public class MaxTest {

    /**
     * тестирование метода maxOfTwoNunber
     * first > second
     */
    @Test
    public void whenNumbers3And5MaxIs5() {
        Max max = new Max();
        int result = max.maxOfTwoNunber(3, 5);
        assertThat(result, is(5));
    }

    /**
     * тестирование метода maxOfTwoNunber
     * first < second
     */
    @Test
    public void whenNumbers4AndMinus5MaxIs3() {
        Max max = new Max();
        int result = max.maxOfTwoNunber(4, -5);
        assertThat(result, is(4));
    }

    /**
     * тестирование метода maxOfTwoNunber
     * first = second
     */
    @Test
    public void whenNumbers3And35MaxIs3() {
        Max max = new Max();
        int result = max.maxOfTwoNunber(3, 3);
        assertThat(result, is(3));
    }
}
