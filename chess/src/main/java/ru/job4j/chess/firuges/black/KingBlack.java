package ru.job4j.chess.firuges.black;

import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;

/**
 * @autor Андрей
 * @since 31.05.2018
 */
public class KingBlack implements Figure {
    private final Cell position;

    public KingBlack(final Cell position) {
        this.position = position;
    }

    @Override
    public Cell position() {
        return this.position;
    }

    @Override
    public Cell[] way(Cell source, Cell dest)throws ImpossibleMoveException {
        int a = dest.x - source.x;
        int b = dest.y - source.y;
        if (!((a == 0 && Math.abs(b) == 1)||(Math.abs(a) == 1 && b == 0)||(Math.abs(a) == 1 && Math.abs(b) == 1))) {
            throw new ImpossibleMoveException();
        }
        return new Cell[] { dest };
    }

    @Override
    public Figure copy(Cell dest) {
        return new KingBlack(dest);
    }
}
