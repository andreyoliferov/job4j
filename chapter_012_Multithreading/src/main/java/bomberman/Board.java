package bomberman;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @autor aoliferov
 * @since 24.09.2018
 */
public class Board {

    private ReentrantLock[][] board;

    private Board(int x, int y) {
        board = new ReentrantLock[x][y];
    }

}
