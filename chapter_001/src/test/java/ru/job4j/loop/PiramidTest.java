package ru.job4j.loop;

import org.junit.Test;

import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестирование класса Piramid
 * @autor Андрей
 * @since 06.05.2018
 */
public class PiramidTest {

    /**
     * тестирование метода paint
     * пирамида 4х4
     */
    @Test
    public void whenPyramid4() {
        Piramid piramid = new Piramid();
        String rst = piramid.paint(4);
        System.out.println(rst);
        assertThat(rst,
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("   ^   ")
                                .add("  ^^^  ")
                                .add(" ^^^^^ ")
                                .add("^^^^^^^")
                                .toString()
                )
        );
    }

    /**
     * тестирование метода paintMy
     * пирамида 4х4
     */
    @Test
    public void whenPyramid4OfMyPaint() {
        Piramid piramid = new Piramid();
        String rst = piramid.paintMy(4);
        System.out.println(rst);
        assertThat(rst,
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("   ^")
                                .add("  ^^^")
                                .add(" ^^^^^")
                                .add("^^^^^^^")
                                .toString()
                )
        );
    }
}
