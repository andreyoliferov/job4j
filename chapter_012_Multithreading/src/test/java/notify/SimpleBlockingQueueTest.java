package notify;

import org.testng.annotations.Test;

/**
 * Тест реализует pattern Producer Consumer
 * @autor Андрей
 * @since 12.08.2018
 */
public class SimpleBlockingQueueTest {

    @Test
    public void whenExapmleBlockQuueue() {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(10);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    queue.offer(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(String.format("Поставщик[элемент : %s][размер : %s]", i, queue.size()));
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    System.out.println(String.format("Потребитель[элемент : %s][размер : %s]", queue.poll(), queue.size()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        consumer.start();
        producer.start();

        try {
            consumer.join();
            producer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
