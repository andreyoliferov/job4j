package ru.job4j.chess.firuges.base;

import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;

/**
 * @autor Андрей
 * @since 31.05.2018
 */
public abstract class Knight implements Figure {

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int a = dest.x - source.x;
        int b = dest.y - source.y;
        if (!((Math.abs(a) == 1 && Math.abs(b) == 2) || (Math.abs(a) == 2 && Math.abs(b) == 1))) {
            throw new ImpossibleMoveException();
        }
        return new Cell[] {dest};
    }
}
