package pool;

import notify.SimpleBlockingQueue;

import java.util.ArrayList;
import java.util.List;

/**
 * Задание: 1. Реализовать ThreadPool [#1099]
 * Пул потоков
 * @autor aoliferov
 * @since 23.09.2018
 */
public class ThreadPool {

    private SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(30);
    private Thread[] threads;

    public ThreadPool() {
        int size = Runtime.getRuntime().availableProcessors();
        threads = new Thread[size];
        for (int i = 0; i < size; i++) {
            threads[i] = new ThreadRunner();
        }
    }

    public void work(Runnable job) {
        tasks.offer(job);
    }

    public void shutdown() {
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }

    private class ThreadRunner extends Thread {

        private ThreadRunner() {
            start();
        }

        @Override
        public void run() {
            while (!Thread.interrupted()) {
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
