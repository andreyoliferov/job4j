package bomberman;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * @autor aoliferov
 * @since 24.09.2018
 */
public class Bomberman extends Application {

    private final int h = 12;
    private final int l = 10;

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane border = new BorderPane();
        border.setCenter(this.buildGrid());
        primaryStage.setScene(new Scene(border, 700, 700));
        primaryStage.show();
    }

    private Group buildGrid() {
        Group panel = new Group();
        for (int y = 0; y != this.l; y++) {
            for (int x = 0; x != this.h; x++) {
                panel.getChildren().add(
                        this.buildRectangle(x, y, 50)
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
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.LIGHTGRAY);
        return rect;
    }
}
