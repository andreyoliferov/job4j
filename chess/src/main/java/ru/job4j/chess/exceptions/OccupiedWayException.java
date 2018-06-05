package ru.job4j.chess.exceptions;

/**
 * @autor Андрей
 * @since 05.06.2018
 */
public class OccupiedWayException extends RuntimeException {
    public OccupiedWayException() {
        super("Путь занят!");
    }
}
