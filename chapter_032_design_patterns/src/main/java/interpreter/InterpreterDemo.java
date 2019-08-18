package interpreter;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Интерпретатор
 * @autor aoliferov
 * @since 17.08.2019
 */
public class InterpreterDemo {

    public static void main(String[] args) {
        Context ctx = new Context();
        //Интрепретация
        System.out.println(
                interpreter("select name from people", ctx)
        );
        System.out.println(
                interpreter("select name from people where startWith d", ctx)
        );
        System.out.println(
                interpreter("select * from people where length 12", ctx)
        );
    }

    private static List<String> interpreter(String command, Context ctx) {
        String[] items = command.split(" ");
        Expression query4 = null;
        //можно переделать на TreeMap
        if ("select".equals(items[0])) {
            if ("from".equals(items[2])) {
                if (items.length == 4) {
                    query4 = new Select(items[1], new From(items[3]));
                } else if ("where".equals(items[4])) {
                    if ("startWith".equals(items[5])) {
                        //предикат так же надо заменить на класс команды
                        query4 = new Select(items[1], new From(items[3], new Where(name -> name.toLowerCase().startsWith(items[6]))));
                    } else if ("length".equals(items[5])) {
                        query4 = new Select(items[1], new From(items[3], new Where(name -> name.length() == Integer.parseInt(items[6]))));
                    }
                }
            }
        }
        assertNotNull("Unsupported command", query4);
        return query4.interpret(ctx);
    }
}
