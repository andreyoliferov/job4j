package ru.job4j.chess.firuges.abstractFigures;

import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;

/**
 * @autor Андрей
 * @since 31.05.2018
 */
public abstract class Qeen implements Figure {

    @Override
    public Cell[] way(Cell source, Cell dest) throws ImpossibleMoveException {
        int a = dest.x - source.x;
        int b = dest.y - source.y;
        Cell[] array = new Cell[Math.abs(a) > Math.abs(b) ? Math.abs(a) : Math.abs(b)];
        if (!((Math.abs(a) == Math.abs(b)) || (a == 0 && b != 0 || a != 0 && b == 0))) {
            throw new ImpossibleMoveException();
        }
        if (Math.abs(a) == Math.abs(b)) {
            int stepX = Integer.compare(a, 0);
            int stepY = Integer.compare(b, 0);
            int deltaX = stepX;
            int deltaY = stepY;
            for (int i = 0; i < array.length; i++) {
                array[i] = Cell.find(source.x + deltaX, source.y + deltaY);
                deltaX += stepX;
                deltaY += stepY;
            }
        } else {
            int step = Integer.compare(b == 0 ? a : b, 0);
            int delta = step;
            if (b == 0) {
                for (int i = 0; i < array.length; i++) {
                    array[i] = Cell.find(source.x + delta, source.y);
                    delta += step;
                }
            } else {
                for (int i = 0; i < array.length; i++) {
                    array[i] = Cell.find(source.x, source.y + delta);
                    delta += step;
                }
            }
        }
        return array;
    }
}
