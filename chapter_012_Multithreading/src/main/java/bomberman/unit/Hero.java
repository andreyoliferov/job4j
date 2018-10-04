package bomberman.unit;

import bomberman.Logic;
import javafx.application.Platform;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * @autor aoliferov
 * @since 24.09.2018
 */
public class Hero extends Unit implements Runnable {

    public Hero(int x, int y, Logic logic) {
        super(x, y, logic);
    }

    /**
     * Установить бомбу
     */
    public void bomb() {
        new Thread(()-> {
            Cell cell = getCell();
            final Rectangle[] rec = new Rectangle[1];
            Platform.runLater(() -> {
                rec[0] = getLogic().buildRectangle(cell, "bomb.png");
                getLogic().getGrid().getChildren().add(rec[0]);
            });
            int x = cell.getX();
            int y = cell.getY();
            List<Cell> list = new ArrayList<>();
            list.addAll(Arrays.asList(
                    getLogic().getCell(x, y),
                    getLogic().getCell(x, y + 1),
                    getLogic().getCell(x, y - 1),
                    getLogic().getCell(x + 1, y),
                    getLogic().getCell(x - 1, y)
            ));
            if (x < getLogic().getL() - 2) {
                list.add(getLogic().getCell(x + 2, y));
            }
            if (x > 2) {
                list.add(getLogic().getCell(x - 2, y));
            }
            if (y < getLogic().getH() - 2) {
                list.add(getLogic().getCell(x, y + 2));
            }
            if (y > 1) {
                list.add(getLogic().getCell(x, y - 2));
            }
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                    rec[0].setVisible(false);
                    Thread.sleep(100);
                    rec[0].setVisible(true);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            List<Rectangle> bangList = new ArrayList<>();
            for (Cell bang : list) {
                Platform.runLater(() -> {
                    Rectangle bangRec = getLogic().buildRectangle(bang, "bang.png");
                    bangRec.setArcWidth(40);
                    bangRec.setArcHeight(40);
                    getLogic().getGrid().getChildren().add(bangRec);
                    bangList.add(bangRec);
                });
            }
            for (Enemy enemy : getLogic().getEnemys()) {
                for (Cell c : list) {
                    if (c.equals(enemy.getCell())) {
                        enemy.getTread().interrupt();
                    }
                }
            }
            rec[0].setVisible(false);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (Rectangle b : bangList) {
                b.setVisible(false);
            }
        }).start();
    }

    @Override
    public void run() {
        //
    }
}
