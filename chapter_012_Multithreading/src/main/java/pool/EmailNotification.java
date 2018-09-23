package pool;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Задание : 2. ExecutorService рассылка почты. [#63097]
 * Cервис для рассылки почты.
 * @autor aoliferov
 * @since 23.09.2018
 */
public class EmailNotification {

    /**
     * Пул задач.
     */
    private ExecutorService pool;

    public EmailNotification() {
        pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors()
        );
    }

    /**
     * Формирует письмо и добавляет задачу в пул потоков
     * @param user адресант
     */
    public void emailTo(User user) {
        String username = user.getUsername();
        String email = user.getEmail();
        String subject = String.format("Notification {%s} to email {%s}.", username, email);
        String body = String.format("Add a new event to {%s}", username);
        pool.submit(() -> send(subject, body, email));
    }

    /**
     * Закрытие пула потоков
     */
    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Метод отправки почты
     * @param subject
     * @param body
     * @param email
     */
    public void send(String subject, String body, String email) {
        /* */
    }
}
