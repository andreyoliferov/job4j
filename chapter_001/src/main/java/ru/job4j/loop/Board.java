package ru.job4j.loop;

/**
 * @autor Андрей
 * @since 06.05.2018
 */
public class Board {

    /**
     * Метод рисует шахматную доску строкой
     * @param width ширина доски
     * @param height высота доски
     * @return String доска
     */
    public String paint(int width, int height) {
        StringBuilder screen = new StringBuilder();
        String ln = System.lineSeparator();
        for (int i = 0; i < height; i++) {
            for (int a = 0; a < width; a++) {
                if ((i + a) % 2 == 0) {
                    screen.append("X");
                } else {
                    screen.append(" ");
                }
            }
            screen.append(ln);
        }
        return screen.toString();
    }
}
