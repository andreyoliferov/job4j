package strategy;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @autor Андрей Олиферов
 * @since 21.05.2018
 */
public class TriangleTest {

    @Test
    public void whenDrawTriangle() {
        Triangle triangle = new Triangle();
        assertThat(
                triangle.pic(),
                is(
                        new StringBuilder()
                                .append("   +   ").append(System.lineSeparator())
                                .append("  +++  ").append(System.lineSeparator())
                                .append(" +++++ ").append(System.lineSeparator())
                                .append("+++++++").append(System.lineSeparator())
                                .toString()
                )
        );
    }
}
