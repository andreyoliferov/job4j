package ru.job4j.condition;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Тестирование класса Point
 * @author Andrey Oliferov
 * @since 02.05.2018.
 */
public class PointTest {

    /**
     * Тестирование метода расчета расстояния между точками.
     */
    @Test
    public void whenOnePoint03TwoPoint40Distance5() {
        Point a = new Point(0, 3);
        Point b = new Point(4, 0);
        assertThat(a.distanceTo(b), is(5.0D));
    }
}
