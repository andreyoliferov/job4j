package threads;

/**
 * @autor Андрей
 * @since 09.08.2018
 */
public class JmmProblem {

    private User user;
    private Integer n = 0;

    private void example() {
        user = new User(n, "Andrey");
        for (int i = 0; i < 10; i++) {
            new ProblemThreadIncrem().start();
        }
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(user.getAge());
    }

    public static void main(String[] args) {
        JmmProblem jmm = new JmmProblem();
        jmm.example();
    }

    public class ProblemThreadIncrem extends Thread {
        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                user.setAge(n++);
            }
        }
    }

    public class User {
        private int age;
        private String name;

        public User(int age, String name) {
            this.age = age;
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
