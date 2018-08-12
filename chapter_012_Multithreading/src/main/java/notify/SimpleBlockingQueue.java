package notify;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @autor Андрей
 * @since 12.08.2018
 */
@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private Queue<T> queue = new LinkedList<>();
    private final int maxSize;

    public SimpleBlockingQueue(int size) {
        this.maxSize = size;
    }

    public synchronized int size() {
        return queue.size();
    }

    public synchronized void offer(T value) {
        while (queue.size() == maxSize) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        queue.offer(value);
        this.notify();
    }

    public synchronized T poll() {
        while (queue.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        T elem =  queue.poll();
        this.notify();
        return elem;
    }
}
