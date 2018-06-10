package ru.job4j.chess.firuges;

/**
 * @autor Андрей
 * @since 31.05.2018
 */
public interface Figure {



    Cell position();
    Cell[] way(Cell source, Cell dest);

    default String icon() {
        return String.format(
                "%s.png", this.getClass().getSimpleName()
        );

    }

    Figure copy(Cell dest);
}
