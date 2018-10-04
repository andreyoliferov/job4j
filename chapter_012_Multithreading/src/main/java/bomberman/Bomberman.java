package bomberman;

import bomberman.unit.Enemy;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;

/**
 * @autor aoliferov
 * @since 24.09.2018
 */

/**
 * Application class of game
 */
public class Bomberman extends Application {

    private final int h = 15;
    private final int l = 20;
    private final int sizeCell = 50;

    private Logic logic;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane border = new BorderPane();
        Group grid = this.buildGrid();
        border.setCenter(grid);
        primaryStage.setScene(new Scene(border, 1200, 900));
        primaryStage.setTitle("Bomberman");
        primaryStage.show();
        logic = new Logic(grid, l, h, sizeCell);

        /* установить блоки */
        logic.setBlocks(l, h);

        /* установить героя */
        logic.addHero(5, 6);
        new Controller(logic, primaryStage);

        /* установить врагов */
        Enemy enemy1 = new Enemy(9, 2, logic);
        Enemy enemy2 = new Enemy(7, 4, logic);
        Enemy enemy3 = new Enemy(7, 9, logic);
        Enemy enemy4 = new Enemy(7, 10, logic);
        Enemy enemy5 = new Enemy(5, 7, logic);
        Enemy enemy6 = new Enemy(11, 9, logic);
        Enemy enemy7 = new Enemy(8, 10, logic);
        Enemy enemy8 = new Enemy(3, 3, logic);
        Enemy enemy9 = new Enemy(2, 3, logic);
        Enemy enemy10 = new Enemy(13, 10, logic);
        Enemy enemy11 = new Enemy(11, 8, logic);
        Enemy enemy12 = new Enemy(8, 9, logic);
        Enemy enemy13 = new Enemy(3, 2, logic);
        Enemy enemy14 = new Enemy(2, 2, logic);
        Enemy enemy15 = new Enemy(13, 13, logic);
        List<Enemy> list = Arrays.asList(enemy1, enemy2, enemy3, enemy4, enemy5, enemy6, enemy7, enemy8, enemy9, enemy10,
                enemy11, enemy12, enemy13, enemy14, enemy15);


        /* остановка потоков */
        primaryStage.setOnCloseRequest(event -> {
            for (Enemy enemy : list) {
                Thread t = enemy.getTread();
                if (t.isAlive()) {
                    t.interrupt();
                }
            }
        });
    }

    private Group buildGrid() {
        Group panel = new Group();
        for (int y = 0; y != this.h; y++) {
            for (int x = 0; x != this.l; x++) {
                panel.getChildren().add(
                        this.buildRectangle(x, y, this.sizeCell)
                );
            }
        }

        return panel;
    }

    private Rectangle buildRectangle(int x, int y, int size) {
        Rectangle rect = new Rectangle();
        rect.setX(x * size);
        rect.setY(y * size);
        rect.setHeight(size);
        rect.setWidth(size);
        rect.setFill(Color.GREEN);
        return rect;
    }
}
