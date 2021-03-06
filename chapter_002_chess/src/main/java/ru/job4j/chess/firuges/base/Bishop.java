package ru.job4j.chess.firuges.base;

import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;

/**
 * @autor Андрей
 * @since 31.05.2018
 */
public abstract class Bishop implements Figure {

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int a = dest.x - source.x;
        int b = dest.y - source.y;
        Cell[] array = new Cell[Math.abs(a)];
        if (!(Math.abs(a) == Math.abs(b))) {
            throw new ImpossibleMoveException();
            }
        int stepX = Integer.compare(a, 0);
        int stepY = Integer.compare(b, 0);
        int deltaX = stepX;
        int deltaY = stepY;
        for (int i = 0; i < array.length; i++) {
            array[i] = Cell.find(source.x + deltaX, source.y + deltaY);
            deltaX += stepX;
            deltaY += stepY;
        }
        return array;
    }
}
