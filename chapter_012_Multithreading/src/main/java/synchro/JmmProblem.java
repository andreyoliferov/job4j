package synchro;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * @autor Андрей
 * @since 09.08.2018
 */
public class JmmProblem {

    private void example() {
        Counter counter = new Counter(0);
        for (int i = 0; i < 10; i++) {
            new ProblemThreadIncrem(counter).start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(counter.getValue());
    }

    public static void main(String[] args) {
        JmmProblem jmm = new JmmProblem();
        jmm.example();
    }

    public class ProblemThreadIncrem extends Thread {

        private Counter counter;

        private ProblemThreadIncrem(final Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                this.counter.addValue(1);
            }
        }
    }

    @ThreadSafe
    public class Counter {

        @GuardedBy("this")
        private int value;

        public Counter(int value) {
            this.value = value;
        }

        public synchronized int getValue() {
            return value;
        }

        public synchronized void addValue(int value) {
            this.value += value;
        }
    }
}
