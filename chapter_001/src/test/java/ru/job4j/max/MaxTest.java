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
     * тестирование метода maxOfTwoNumber
     * first > second
     */
    @Test
    public void whenNumbers3And5MaxIs5() {
        Max max = new Max();
        int result = max.maxOfTwoNumber(3, 5);
        assertThat(result, is(5));
    }

    /**
     * тестирование метода maxOfTwoNumber
     * first < second
     */
    @Test
    public void whenNumbers4AndMinus5MaxIs3() {
        Max max = new Max();
        int result = max.maxOfTwoNumber(4, -5);
        assertThat(result, is(4));
    }

    /**
     * тестирование метода maxOfTwoNumber
     * first = second
     */
    @Test
    public void whenNumbers3And35MaxIs3() {
        Max max = new Max();
        int result = max.maxOfTwoNumber(3, 3);
        assertThat(result, is(3));
    }

    /**
     * тестирование метода maxOfThreeNunber
     * first > second > third
     */
    @Test
    public void whenNumbers7And5And3MaxIs7() {
        Max max = new Max();
        int result = max.maxOfThreeNumbers(7, 5, 3);
        assertThat(result, is(7));
    }

    /**
     * тестирование метода maxOfThreeNunber
     * first < second > third
     */
    @Test
    public void whenNumbersMinus7And5And3MaxIs7() {
        Max max = new Max();
        int result = max.maxOfThreeNumbers(-7, 5, 3);
        assertThat(result, is(5));
    }

    /**
     * тестирование метода maxOfThreeNunber
     * first < second < third
     */
    @Test
    public void whenNumbersMinus7And3And6MaxIs6() {
        Max max = new Max();
        int result = max.maxOfThreeNumbers(-7, 3, 6);
        assertThat(result, is(6));
    }

    /**
     * тестирование метода maxOfThreeNunber
     * first = second = third = 0
     */
    @Test
    public void whenNumbers0And0And0MaxIs0() {
        Max max = new Max();
        int result = max.maxOfThreeNumbers(0, 0, 0);
        assertThat(result, is(0));
    }
}
