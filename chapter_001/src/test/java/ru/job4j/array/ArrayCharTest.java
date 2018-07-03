package ru.job4j.array;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Тест класса ArrayChar
 * @autor Андрей
 * @since 09.05.2018
 */
public class ArrayCharTest {

    /**
     * Тест метода startWith
     * true
     */
    @Test
    public void whenStartWithPrefixThenTrue() {
        ArrayChar word = new ArrayChar("Hello");
        boolean result = word.startWith("He");
        assertThat(result, is(true));
    }

    /**
     * Тест метода startWith
     * false
     */
    @Test
    public void whenNotStartWithPrefixThenFalse() {
        ArrayChar word = new ArrayChar("Hello");
        boolean result = word.startWith("Hi");
        assertThat(result, is(false));
    }

    /**
     * Тест метода startWith
     * false
     */
    @Test
    public void whenNotStartWithPrefixThenFalseFull() {
        ArrayChar word = new ArrayChar("hello");
        boolean result = word.startWith("hillo");
        assertThat(result, is(false));
    }
}
