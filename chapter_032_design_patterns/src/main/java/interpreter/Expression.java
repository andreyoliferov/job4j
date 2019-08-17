package interpreter;

import java.util.List;

/**
 * @autor aoliferov
 * @since 17.08.2019
 */
public interface Expression {
    List<String> interpret(Context ctx);
}
