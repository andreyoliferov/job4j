package ru.job4j.array;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Тестирование класса Matrix
 * @autor Андрей Олиферов
 * @since 09.05.2018
 */
public class MatrixTest {

    /**
     * Тест метода table
     * 2х2
     */
    @Test
    public void when2on2() {
        Matrix test = new Matrix();
        int[][] table = test.table(2);
        int[][] expect = {
                {1, 2},
                {2, 4}
        };
        assertThat(table, is(expect));
    }

    /**
     * Тест метода table
     * 5х5
     */
    @Test
    public void when5on5() {
        Matrix test = new Matrix();
        int[][] table = test.table(5);
        int[][] expect = {
                {1, 2, 3, 4, 5},
                {2, 4, 6, 8, 10},
                {3, 6, 9, 12, 15},
                {4, 8, 12, 16, 20},
                {5, 10, 15, 20, 25}
        };
        assertThat(table, is(expect));
    }
}
