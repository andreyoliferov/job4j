package ru.job4j.loop;

import java.util.function.BiPredicate;

/**
 * 4.4. Построить пирамиду в псевдографике. [#4412]
 * @autor Андрей
 * @since 06.05.2018
 */
public class Piramid {

    /**
     * метод рисует пирамиду
     * мой вариант реализации
     * @param height высота пирамиды
     * @return пирамида
     */
    public String paintMy(int height) {
        StringBuilder screen = new StringBuilder();
        for (int row = 1; row <= height; row++) {
            for (int count = 0; count < height - row; count++) {
                screen.append(" ");
            }
            for (int count = 0; count < 1 + (2 * (row - 1)); count++) {
                screen.append("^");
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }

    /**
     * @param height высота пирамиды
     * @param weight ширина
     * @param predict условие
     * @return пирамида
     */
    private String loopBy(int height, int weight, BiPredicate<Integer, Integer> predict) {
        StringBuilder screen = new StringBuilder();
        for (int row = 0; row != height; row++) {
            for (int column = 0; column != weight; column++) {
                if (predict.test(row, column)) {
                    screen.append("^");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(System.lineSeparator());
        }
        return screen.toString();
    }

    /**
     * Метод рисует правую часть пирамиды
     * @param height высота
     * @return правая часть пирамиды
     */
    public String rightTrl(int height) {
        return this.loopBy(
                height,
                height,
                (row, column) -> row >= column
        );
    }

    /**
     * метод рисует левую часть пирамиды
     * @param height высота
     * @return левая часть пирамиды
     */
    public String leftTrl(int height) {
        return this.loopBy(
                height,
                height,
                (row, column) -> row >= height - column - 1
        );
    }

    /**
     * метод рисует пирамиду
     * @param height высота пирамиды
     * @return пирамида
     */
    public String paint(int height) {
        return this.loopBy(
                height,
                2 * height - 1,
                (row, column) -> row >= height - column - 1 && row + height - 1 >= column
        );
    }
}
