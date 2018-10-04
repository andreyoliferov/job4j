package bomberman.unit;

import bomberman.Logic;

/**
 * @autor aoliferov
 * @since 24.09.2018
 */
public class Enemy extends Unit implements Runnable {

    private Thread t;

    public Enemy(int x, int y, Logic logic) {
        super(x, y, logic);
        t = new Thread(this);
        t.start();
    }

    public Thread getTread() {
        return t;
    }

    private void delete() {
        getRectangle().setVisible(false);
        getCell().unlock();
        getLogic().deleteEnemy(this);
    }

    @Override
    public void run() {
        getLogic().addEnemy(this);
        try {
            Thread.sleep(1000);
            while (!Thread.interrupted()) {
                this.moveRandom();
            }
        } catch (InterruptedException e) {
            delete();
        }
    }
}
