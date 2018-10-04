package bomberman.unit;

import bomberman.Logic;
import javafx.scene.shape.Rectangle;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import static bomberman.unit.Unit.Way.*;

/**
 * Абстрактный класс юнита (герой, враг)
 * @autor Андрей
 * @since 31.05.2018
 */
@ThreadSafe
public abstract class Unit {

    @GuardedBy("this")
    private Cell current;
    @GuardedBy("this")
    private final Rectangle rectangle;
    @GuardedBy("logic")
    private final Logic logic;

    private Random rnd = new Random();
    private List<Way> variant = Arrays.asList(UP, RIHGT, DOWN, LEFT);

    public Unit(int x, int y, Logic logic) {
        this.logic = logic;
        this.current = logic.getCell(x, y);
        this.rectangle = this.logic.buildRectangle(current, icon());
    }

    /**
     * @return объект логики
     */
    protected Logic getLogic() {
        synchronized (logic) {
            return logic;
        }
    }

    /**
     * Метод выполняет рандомное движение
     * @return рузультат
     * @throws InterruptedException
     */
    public boolean moveRandom() throws InterruptedException {
        int random = rnd.nextInt(4);
        int way = rnd.nextInt(5);
        boolean result;
        int i = 0;
        do {
            i++;
            result = this.variant.get(random).getPredicate().test(this);
            if (result) {
                Thread.sleep(500);
            } else {
                Thread.sleep(100);
            }
        } while (result && i < way);
        return result;
    }

    /**
     * @return объект отображения юнита
     */
    public synchronized Rectangle getRectangle() {
        return rectangle;
    }

    /**
     * Вернуть позицию юнита
     * @return текущая ячейка
     */
    public synchronized Cell getPosition() {
        return this.current;
    }

    /**
     * Установить позицию юнита
     * @param cell новая ячейка
     */
    public synchronized void setPosition(Cell cell) {
        this.current = cell;
    }

    /**
     * Метод формирует имя изоображения в зависимости от типа юнита
     * @return
     */
    private String icon() {
        return String.format(
                "%s.png", this.getClass().getSimpleName()
        );
    }

    /**
     * Базовый метод движения
     * @param diffX изменение по координате х
     * @param diffY изменение по координате у
     * @return результат
     */
    private synchronized boolean moveBase(int diffX, int diffY) {
        synchronized (logic) {
            int x = current.getX() + diffX;
            int y = current.getY() + diffY;
            Cell to = logic.getCell(x, y);
            boolean result = false;
            if (logic.enableMove(current, to, this)) {
                rectangle.setX(logic.coordinates(x));
                rectangle.setY(logic.coordinates(y));
                result = true;
            }
            return result;
        }
    }

    /**
     * Движение вверх
     * @return результат
     */
    public boolean moveUp() {
        return moveBase(0, -1);
    }

    /**
     * Движение вниз
     * @return результат
     */
    public boolean moveDown() {
        return moveBase(0, 1);
    }

    /**
     * Движение влево
     * @return результат
     */
    public boolean moveLeft() {
        return moveBase(-1, 0);
    }

    /**
     * Движение вправо
     * @return результат
     */
    public boolean moveRight() {
        return moveBase(1, 0);
    }

    /**
     * Перечисление направлений движения
     * как альтератива switch/case
     */
    public enum Way {

        UP(0, Unit::moveUp),
        RIHGT(1, Unit::moveRight),
        DOWN(2, Unit::moveDown),
        LEFT(3, Unit::moveLeft);

        private final int num;
        private Predicate<Unit> move;

        Way(int num, Predicate<Unit> move) {
            this.num = num;
            this.move = move;
        }

        public Predicate<Unit> getPredicate() {
            return this.move;
        }
    }

    public Cell getCell() {
        return current;
    }
}
