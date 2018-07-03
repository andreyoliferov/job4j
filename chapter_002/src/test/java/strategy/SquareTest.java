package strategy;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @autor Андрей Олиферов
 * @since 21.05.2018
 */
public class SquareTest {

    @Test
    public void whenDrawSquare() {
        Square square = new Square();
        assertThat(
                square.pic(),
                is(
                        new StringBuilder()
                                .append("+++++++").append(System.lineSeparator())
                                .append("+++++++").append(System.lineSeparator())
                                .append("+++++++").append(System.lineSeparator())
                                .append("+++++++").append(System.lineSeparator())
                                .toString()
                )
        );
    }
}
