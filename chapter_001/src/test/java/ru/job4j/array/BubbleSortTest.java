package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * тестирование класса BubbleSort
 * @autor Андрей Олиферов
 * @since 09.05.2018
 */
public class BubbleSortTest {

    /**
     * тестирование метода bubbleSort
     */
    @Test
    public void whenInputArrayThenArraySort() {
        BubbleSort sort = new BubbleSort();
        int[] array = {3, 6, 2, 1, 7, 9, -1, 3, -5, 0};
        int[] result = sort.bubbleSort(array);
        assertThat(result, is(new int[] {-5, -1, 0, 1, 2, 3, 3, 6, 7, 9}));
    }
}
