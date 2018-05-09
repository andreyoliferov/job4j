package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тест класса ArrayDuplicate
 * @autor Андрей Олиферов
 * @since 09.05.2018
 */
public class ArrayDuplicateTest {

    /**
     * Тест метода remove
     */
    @Test
    public void whenRemoveDuplicatesThenArrayWithoutDuplicate() {
        ArrayDuplicate arrayDuplicate = new ArrayDuplicate();
        String[] array = {"Andrey", "Katya", "Andrey", "Maksim", "Maksim", "Sasha", "Maksim"};
        String[] result = arrayDuplicate.remove(array);
        assertThat(result, is(new String[] {"Andrey", "Katya", "Maksim", "Sasha"}));
    }
}
