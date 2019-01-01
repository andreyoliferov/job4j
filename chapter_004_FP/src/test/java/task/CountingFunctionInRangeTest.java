package task;

import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * @autor aoliferov
 * @since 01.01.2019
 */
public class CountingFunctionInRangeTest {

    private final CountingFunctionInRange function = new CountingFunctionInRange();

    @Test
    public void whenLinearFunctionThenLinearResults() {
        List<Double> result = function.diapason(5, 8, x -> 2 * x + 1);
        List<Double> expected = Arrays.asList(11D, 13D, 15D);
        assertThat(result, is(expected));
    }

    @Test
    public void whenQuadraticFunctionThenQuadraticResults() {
        List<Double> result = function.diapason(5, 8, x -> Math.pow(x, 2) + 2 * x + 1);
        List<Double> expected = Arrays.asList(36D, 49D, 64D);
        assertThat(result, is(expected));
    }

    @Test
    public void whenLogarithmicFunctionThenLogarithmicResults() {
        List<Double> result = function.diapason(9, 10, x -> Math.log(x) / Math.log(3));
        List<Double> expected = Arrays.asList(2D);
        assertThat(result, is(expected));
    }
}
