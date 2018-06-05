package ru.job4j.chess.exceptions;

/**
 * @autor Андрей
 * @since 05.06.2018
 */
public class FigureNotFoundException extends RuntimeException {
    public FigureNotFoundException() {
        super("Фигура не найдена!");
    }
}
