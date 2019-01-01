package task;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @autor aoliferov
 * @since 01.01.2019
 */
public class CountingFunctionInRange {

    List<Double> diapason(int start, int end, Function<Double, Double> func) {
        List<Double> result = new ArrayList<>();
        for (int i = start; i < end; i++) {
            result.add(func.apply((double) i));
        }
        return result;
    }
}
