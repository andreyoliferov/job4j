package bomberman.unit;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @autor aoliferov
 * @since 24.09.2018
 */
@ThreadSafe
public class Cell {

    private final int x;
    private final int y;
    @GuardedBy("this")
    private final ReentrantLock lock;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        lock = new ReentrantLock();
    }

    public synchronized boolean tryLock() {
        return lock.tryLock();
    }

    public synchronized boolean isLocked() {
        return lock.isLocked();
    }

    public synchronized void unlock() {
        this.lock.unlock();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
