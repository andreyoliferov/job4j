package garbagecollect;

/**
 * @autor aoliferov
 * @since 20.02.2019
 */
public class Example {

    //32 байт + n * 2 байта + выравнивание до 8 кратного значения
    public static class User {

        private String name;

        public User(String name) {
            this.name = name;
        }
    }

    //1 мб = 1048576 б
    //-Xmx20m
    //-XX:UseG1GC/UseSerialGC/UseParallelGC
    //Concurrent Marc Sweep GC deprecated in java 9
    public static void main(String[] args) {

        while (true) {
            User user = new User("Андрей"); //48 байт
            user = null;
        }
        
    }

}
