package ru.job4j.chess;


import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.job4j.chess.exceptions.ImpossibleMoveException;
import ru.job4j.chess.exceptions.OccupiedWayException;
import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;
import ru.job4j.chess.firuges.black.*;
import ru.job4j.chess.firuges.white.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static ru.job4j.chess.firuges.Cell.*;

/**
 * @autor Андрей
 * @since 10.06.2018
 */
public class ChessMoveTest {

    /**
     * Тестирование корректного перемещения
     * @return тестовые данные
     */
    @DataProvider
    public Object[][] wayCorrect() {
        return new Object[][]{
                {new RookBlack(A1), A1, A8},
                {new RookWhite(A1), A1, A8},
                {new RookBlack(A1), A1, D1},

                {new BishopBlack(A1), A1, G7},
                {new BishopWhite(A1), A1, G7},
                {new BishopBlack(C7), C7, F4},

                {new KingBlack(E4), E4, E5},
                {new KingWhite(E4), E4, D4},
                {new KingBlack(E4), E4, D5},

                {new KnightBlack(E4), E4, D6},
                {new KnightWhite(E4), E4, C5},
                {new KnightBlack(E4), E4, D6},

                {new PawnBlack(D5), D5, D4},
                {new PawnWhite(D5), D5, D6},

                {new QeenBlack(F3), F3, B7},
                {new QeenBlack(F3), F3, D1},
                {new QeenWhite(F3), F3, F1},
                {new QeenBlack(F3), F3, G3},
        };
    }
    @Test(dataProvider = "wayCorrect")
    public void whenFiguresWayIsCorrect(Figure figure, Cell start, Cell end) {
        Logic logic = new Logic();
        logic.add(figure);
        assertThat(logic.getFigures()[0].position(), is(start));
        logic.move(start, end);
        assertThat(logic.getFigures()[0].position(), is(end));
    }

    /**
     * Тестирование невозможности перемещения из за препятствия
     * @return тестовые данные
     */
    @DataProvider
    public Object[][] wayWithBarrier() {
        return new Object[][]{
                {new RookBlack(A1), A1, A8, A6},
                {new RookWhite(A1), A1, A8, A6},
                {new RookBlack(A1), A1, D1, D1},

                {new BishopBlack(A1), A1, G7, C3},
                {new BishopWhite(A1), A1, G7, C3},
                {new BishopBlack(C7), C7, F4, F4},

                {new KingBlack(E4), E4, E5, E5},
                {new KingWhite(E4), E4, E5, E5},

                {new KnightBlack(E4), E4, D6, D6},
                {new KnightWhite(E4), E4, C5, C5},

                {new PawnBlack(D5), D5, D4, D4},
                {new PawnWhite(D5), D5, D6, D6},

                {new QeenBlack(F3), F3, B7, D5},
                {new QeenBlack(F3), F3, D1, E2},
                {new QeenWhite(F3), F3, F1, F2},
                {new QeenBlack(F3), F3, G3, G3},
        };
    }
    @Test(dataProvider = "wayWithBarrier")
    public void whenFiguresWayHaveBarrier(Figure figure, Cell start, Cell end, Cell barrier) throws Exception {
        Logic logic = new Logic();
        logic.add(figure);
        logic.add(new PawnBlack(barrier));
        try {
            logic.move(start, end);
            throw new Exception("Выполнено перемещение, заблокированное препятствием!");
        } catch (OccupiedWayException owe) {
            System.out.println("Путь занят!");
        }
        assertThat(logic.getFigures()[0].position(), is(start));
    }

    /**
     * Тестирование невозможности некорректного перемещения
     * @return тестовые данные
     */
    @DataProvider
    public Object[][] wayIncorrect() {
        return new Object[][]{
                {new RookBlack(A1), A1, B2},
                {new RookWhite(B2), B2, A3},
                {new RookBlack(D4), D4, G6},

                {new BishopBlack(F8), F8, E6},
                {new BishopWhite(F8), F8, F3},
                {new BishopBlack(E5), E5, D7},

                {new KingBlack(E4), E4, E6},
                {new KingWhite(E4), E4, C4},
                {new KingBlack(E4), E4, C6},
                {new KingBlack(E4), E4, D6},

                {new KnightBlack(E4), E4, E6},
                {new KnightWhite(E4), E4, A4},
                {new KnightBlack(E4), E4, B1},
                {new KnightBlack(E4), E4, H6},

                {new PawnBlack(D5), D5, D6},
                {new PawnWhite(D5), D5, D4},
                {new PawnBlack(D5), D5, E5},
                {new PawnBlack(D5), D5, C6},
                {new PawnWhite(D5), D5, E5},
                {new PawnWhite(D5), D5, C6},

                {new QeenBlack(F3), F3, E5},
                {new QeenWhite(F3), F3, H2},
        };
    }
    @Test(dataProvider = "wayIncorrect")
    public void whenFiguresWayIncorrect(Figure figure, Cell start, Cell end) throws Exception {
        Logic logic = new Logic();
        logic.add(figure);
        try {
            logic.move(start, end);
            throw new Exception("Выполнено некорректное перемещение!");
        } catch (ImpossibleMoveException ime) {
            System.out.println("Некорректное перемещение!");
        }
        assertThat(logic.getFigures()[0].position(), is(start));
    }
}
