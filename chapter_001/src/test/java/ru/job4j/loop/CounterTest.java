package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;


/**
 * Тестирование класса Counter
 * @autor Андрей Олиферов
 * @since 05.05.2018
 */
public class CounterTest {

    /**
     * metod sum
     * start меньше чем finish
     */
    @Test
    public void whenWriteInStartAndFinishReturnSumCuonter() {
        Counter count = new Counter();
        int result = count.sum(-3, 2);
        assertThat(result, is(0));
    }

    /**
     * metod sum
     * start больше чем finish
     */
    @Test
    public void whenWriteInStartMoreThenFinishReturnSumCuonter() {
        Counter count = new Counter();
        int result = count.sum(7, 2);
        assertThat(result, is(12));
    }
}
