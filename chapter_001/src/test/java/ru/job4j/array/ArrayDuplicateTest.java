package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.Matchers.arrayContainingInAnyOrder;
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
        ArrayDuplicate duplicate = new ArrayDuplicate();
        String[] array = {"Andrey", "Katya", "Andrey", "Maksim", "Maksim", "Sasha", "Maksim"};
        String[] expect = {"Andrey", "Katya", "Sasha", "Maksim"};
        String[] result = duplicate.remove(array);
        assertThat(result, arrayContainingInAnyOrder(expect));
    }
}
