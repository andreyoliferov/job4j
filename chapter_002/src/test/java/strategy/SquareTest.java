package strategy;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

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
                                .append("+++++++\n")
                                .append("+++++++\n")
                                .append("+++++++\n")
                                .append("+++++++\n")
                                .toString()
                )
        );
    }
}
