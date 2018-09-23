package pool;

import notify.SimpleBlockingQueue;

/**
 * Задание: 1. Реализовать ThreadPool [#1099]
 * Пул потоков
 * @autor aoliferov
 * @since 23.09.2018
 */
public class ThreadPool {

    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(30);
    private volatile boolean isRunning = true;

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        for (int i = 0; i < size; i++) {
            new ThreadRunner().start();
        }
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        this.isRunning = false;
    }

    private class ThreadRunner extends Thread {

        @Override
        public void run() {
            synchronized (tasks) {
                while (isRunning) {
                    if (tasks.size() != 0) {
                        tasks.poll().run();
                    } else {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
