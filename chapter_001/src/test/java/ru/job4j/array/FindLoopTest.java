package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестирование метода FindLoop
 * @autor Андрей
 * @since 09.05.2018
 */
public class FindLoopTest {

    /**
     * базовый тест метода indexOf
     * @param data массив для поиска
     * @param elem искомый елемент
     * @param expectedIndex ожидаемый индекс
     */
    private void baseTestIndexOf(int[] data, int elem, int expectedIndex) {
        FindLoop findLoop = new FindLoop();
        int index =  findLoop.indexOf(data, elem);
        assertThat(index, is(expectedIndex));
    }

    /**
     * элемент найден
     */
    @Test
    public void whenElemToFound() {
        baseTestIndexOf(new int[] {6, 3, 7, -7, 0, 3}, -7, 3);
    }

    /**
     * элемент не найден
     */
    @Test
    public void whenElemToNotFound() {
        baseTestIndexOf(new int[] {6, 3, 7, -7, 0, 3}, -8, -1);
    }

    /**
     * базовый тест метода indexOf
     * @param data массив для поиска
     * @param elem искомый елемент
     * @param expectedIndex ожидаемый индекс
     */
    private void baseTestIndexOf(String[] data, String elem, int expectedIndex) {
        FindLoop findLoop = new FindLoop();
        int index =  findLoop.indexOf(data, elem);
        assertThat(index, is(expectedIndex));
    }

    /**
     * элемент найден
     */
    @Test
    public void whenElemStringToFound() {
        baseTestIndexOf(new String[] {"6", "3", "7", "-7", "0", "3"}, "-7", 3);
    }

    /**
     * элемент не найден
     */
    @Test
    public void whenElemStringToNotFound() {
        baseTestIndexOf(new String[] {"6", "3", "7", "-7", "0", "3"}, "-8", -1);
    }
}
