package ru.job4j.array;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * тестирование класса Tern
 * @autor Андрей Олиферов
 * @since 09.05.2018
 */
public class TurnTest {

    /**
     * @param input исходны массив
     * @param expect ожидаемый переврнутый
     */
    private void baseTurn(int[] input, int[] expect) {
        Turn turner = new Turn();
        int[] result = turner.turn(input);
        assertThat(result, is(expect));
    }

    /**
     * тестирование метода tern
     * четное количество элементов массива
     */
    @Test
    public void whenTurnArrayWithEvenAmountOfElementsThenTurnedArray() {
        baseTurn(new int[] {1, 2, 3, 4, 5, 6}, new int[] {6, 5, 4, 3, 2, 1});
    }

    /**
     * тестирование метода tern
     * нечетное количество элементов массива
     */
    @Test
    public void whenTurnArrayWithOddAmountOfElementsThenTurnedArray() {
        baseTurn(new int[] {1, 2, 3, 4, 5}, new int[] {5, 4, 3, 2, 1});
    }
}
