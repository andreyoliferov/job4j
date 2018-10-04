package bomberman;

import bomberman.unit.Hero;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * @autor aoliferov
 * @since 28.09.2018
 * Класс управления героем
 */
public class Controller {

    private Logic logic;
    private Stage stage;

    public Controller(Logic logic, Stage stage) {
        this.logic = logic;
        this.stage = stage;
        this.start();
    }

    private void start() {
        Hero hero = logic.getHero();
        stage.getScene().setOnKeyPressed(
                event -> {
                    KeyCode keyCode = event.getCode();
                    if (keyCode.equals(KeyCode.UP)) {
                        hero.moveUp();
                    }
                    if (keyCode.equals(KeyCode.DOWN)) {
                        hero.moveDown();
                    }
                    if (keyCode.equals(KeyCode.LEFT)) {
                        hero.moveLeft();
                    }
                    if (keyCode.equals(KeyCode.RIGHT)) {
                        hero.moveRight();
                    }
                    if (keyCode.equals(KeyCode.SPACE)) {
                        hero.bomb();
                    }
                }
        );
    }
}
