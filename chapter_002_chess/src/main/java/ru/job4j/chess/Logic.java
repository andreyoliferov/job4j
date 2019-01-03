package ru.job4j.chess;

import ru.job4j.chess.exceptions.FigureNotFoundException;
import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.exceptions.OccupiedWayException;
import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;

import java.util.stream.IntStream;

/**
 * @autor Андрей
 * @since 31.05.2018
 */
public class Logic {

    private final Figure[] figures = new Figure[32];
    private int index = 0;

    public void add(Figure figure) {
        this.figures[this.index++] = figure;
    }

    /**
     * Метод выполяет перемещение фигуры
     * @param source начальная ячейка
     * @param dest конечная ячейка
     * @return массив ячеек пути, где последняя ячейка массива = финальная ячейка
     * @throws ImpossibleMoveException перемещение невозможно
     * @throws OccupiedWayException путь занят
     * @throws FigureNotFoundException фигура не найдена
     */
    public boolean move(Cell source, Cell dest)
            throws ImpossibleMoveException, OccupiedWayException, FigureNotFoundException {
        boolean rst = false;
        int index = this.findBy(source);
        if (index != -1) {
            Cell[] steps = this.figures[index].way(source, dest);
            if (steps.length > 0 && steps[steps.length - 1].equals(dest)) {
                for (Cell step : steps) {
                    for (Figure figure : figures) {
                        if (figure == null) {
                            break;
                        }
                        if (figure.position().equals(step)) {
                            if (figure != figures[index]) {
                                throw new OccupiedWayException();
                            }
                        }
                    }
                }
                rst = true;
                this.figures[index] = this.figures[index].copy(dest);
            }
        } else {
            throw new FigureNotFoundException();
        }
        return rst;
    }

    public void clean() {
        for (int position = 0; position != this.figures.length; position++) {
            this.figures[position] = null;
        }
        this.index = 0;
    }

    private int findBy(Cell cell) {
        return IntStream.range(0, figures.length)
                .filter(i -> this.figures[i] != null && this.figures[i].position().equals(cell))
                .findFirst().orElse(-1);
    }

    public Figure[] getFigures() {
        return this.figures;
    }
}
