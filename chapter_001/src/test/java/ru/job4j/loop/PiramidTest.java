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
     * тестирование метода paintTwo
     * пирамида 4х4
     */
    @Test
    public void whenPyramid4OfMyPaint() {
        Piramid piramid = new Piramid();
        String rst = piramid.paintTwo(4);
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

    /**
     * тестирование метода leftTrl
     */
    @Test
    public void whenPyramid4Left() {
        Piramid paint = new Piramid();
        String rst = paint.leftTrl(4);
        System.out.println(rst);
        assertThat(rst,
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("   ^")
                                .add("  ^^")
                                .add(" ^^^")
                                .add("^^^^")
                                .toString()
                )
        );
    }

    /**
     * тестирование метода rightTrl
     */
    @Test
    public void whenPyramid4Right() {
        Piramid paint = new Piramid();
        String rst = paint.rightTrl(4);
        System.out.println(rst);
        assertThat(rst,
                is(
                        new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add("^   ")
                                .add("^^  ")
                                .add("^^^ ")
                                .add("^^^^")
                                .toString()
                )
        );
    }

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
}
