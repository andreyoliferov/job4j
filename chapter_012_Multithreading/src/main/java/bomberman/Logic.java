package bomberman;

import bomberman.unit.Cell;
import bomberman.unit.Enemy;
import bomberman.unit.Hero;
import bomberman.unit.Unit;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;

/**
 * @autor aoliferov
 * @since 24.09.2018
 * Logic class
 */
@ThreadSafe
public class Logic {

    private final Group grid;

    @GuardedBy("this")
    private final Cell[][] cells;
    private final int sizeCell;

    private Hero hero;
    @GuardedBy("this")
    private List<Enemy> units = new ArrayList<>();

    private int h;
    private int l;

    public Logic(Group grid, int l, int h, int sizeCell) {
        this.grid = grid;
        this.sizeCell = sizeCell;
        this.l = l;
        this.h = h;
        cells = new Cell[l][h];
        for (int x = 0; x < l; x++) {
            for (int y = 0; y < h; y++) {
                cells[x][y] = new Cell(x, y);
            }
        }
    }

    public int getH() {
        return h;
    }

    public int getL() {
        return l;
    }

    /**
     * Метод выполняет движение, если движение невозможно возвращает false
     * @param from начальная позиция
     * @param to куда
     * @param unit юнит
     * @return результат
     */
    public synchronized boolean enableMove(Cell from, Cell to, Unit unit) {
        boolean result = false;
        if (!to.isLocked() && to.tryLock()) {
            from.unlock();
            unit.setPosition(to);
            result = true;
        }
        return result;
    }

    public int coordinates(int value) {
        return value * sizeCell;
    }

    /**
     * Добавляет героя на указанные координаты поля
     * @param x координата
     * @param y координата
     */
    public void addHero(int x, int y) {
        hero = new Hero(x, y, this);
        hero.getPosition().tryLock();
        grid.getChildren().add(
                hero.getRectangle()
        );
    }

    /**
     * @return ссылка на объект героя (используется контроллером)
     */
    public Hero getHero() {
        return this.hero;
    }

    /**
     * Добавить врага на поле
     * @param unit враг
     */
    public synchronized void addEnemy(Enemy unit) {
        units.add(unit);
        unit.getPosition().tryLock();
        Platform.runLater(() -> grid.getChildren().add(unit.getRectangle()));
    }

    public synchronized void deleteEnemy(Enemy unit) {
        units.remove(unit);
        if (units.size() == 0) {
            System.out.println("Победа!");
        }
    }

    /**
     * Возвращает ячейку по параметрам
     * @param x координата
     * @param y координата
     * @return ссылка на объект ячейки
     */
    public synchronized Cell getCell(int x, int y) {
        return cells[x][y];
    }

    /**
     * Установить ограждения
     * @param l длина поля
     * @param h ширина поля
     */
    public synchronized void setBlocks(int l, int h) {
        for (int i = 0; i < h; i++) {
            cells[0][i].tryLock();
            cells[l - 1][i].tryLock();
            grid.getChildren().add(buildRectangle(cells[0][i], "block.png"));
            grid.getChildren().add(buildRectangle(cells[l - 1][i], "block.png"));
        }
        for (int i = 0; i < l; i++) {
            cells[i][0].tryLock();
            cells[i][h - 1].tryLock();
            grid.getChildren().add(buildRectangle(cells[i][0], "block.png"));
            grid.getChildren().add(buildRectangle(cells[i][h - 1], "block.png"));
        }
    }

    /**
     * Создание визуального отображения объекта
     * @param cell ячейка
     * @param imgName изображение из ресурсов
     * @return отображение (далее добавляется на поле)
     */
    public Rectangle buildRectangle(Cell cell, String imgName) {
        Rectangle rect = new Rectangle();
        rect.setX(coordinates(cell.getX()));
        rect.setY(coordinates(cell.getY()));
        rect.setHeight(sizeCell);
        rect.setWidth(sizeCell);
        Image img = new Image(this.getClass().getClassLoader().getResource(imgName).toString());
        rect.setFill(new ImagePattern(img));
        return rect;
    }

    public Group getGrid() {
        return grid;
    }

    public synchronized List<Enemy> getEnemys() {
        return units;
    }
}
