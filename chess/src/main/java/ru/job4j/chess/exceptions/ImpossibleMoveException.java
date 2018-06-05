package ru.job4j.chess.exceptions;

/**
 * @autor Андрей
 * @since 04.06.2018
 */
public class ImpossibleMoveException extends RuntimeException {
    public ImpossibleMoveException() {
        super("Некорректное перемещение!");
    }
}
