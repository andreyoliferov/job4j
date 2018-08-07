package threads;

import javafx.scene.shape.Rectangle;

/**
 * @autor Андрей
 * @since 06.08.2018
 */
public class RectangleMove implements Runnable {

    private final Rectangle rect;
    private int x = 1;
    private int y = 1;

    public RectangleMove(Rectangle rect) {
        this.rect = rect;
    }

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            if (rect.getX() == 0 || rect.getX() == 300) {
                x *= -1;
            }
            if (rect.getY() == 0 || rect.getY() == 300) {
                y *= -1;
            }
            this.rect.setX(this.rect.getX() + x);
            this.rect.setY(this.rect.getY() + y);
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
