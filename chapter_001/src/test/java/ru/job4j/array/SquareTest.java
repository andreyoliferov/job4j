package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Тестирование класса Square
 * @autor Андрей Олиферов
 * @since 07.05.2018
 */
public class SquareTest {

    /**
     * базовая проверка
     * @param bound число До
     * @param expect ожидаемый результат
     */
    private void baseCalculateTest(int bound, int[] expect) {
        Square test = new Square();
        int[] rst = test.calculate(bound);
        assertThat(rst, is(expect));
    }

    /**
     * тестирование метода calculate
     * bound = 3
     */
    @Test
    public void whenBound3Then149() {
        baseCalculateTest(3, new int[] {1, 4, 9});
    }

    /**
     * тестирование метода calculate
     * bound = 4
     */
    @Test
    public void whenBound4Then149And16() {
        baseCalculateTest(4, new int[] {1, 4, 9, 16});
    }

    /**
     * тестирование метода calculate
     * bound = 0
     */
    @Test
    public void whenBound0ThenEmpty() {
        baseCalculateTest(0, new int[] {});
    }
}
