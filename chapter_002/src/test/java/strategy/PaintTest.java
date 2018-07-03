package strategy;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @autor Андрей Олиферов
 * @since 21.05.2018
 */
public class PaintTest {

    /** Поле для хранения данных вывода */
    private ByteArrayOutputStream out;

    /** Вывод в консоль по умолчанию */
    private PrintStream stdout = System.out;

    @BeforeMethod
    public void loadOutput() {
        out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(this.out));
    }

    @AfterMethod
    public void backOutput() {
        System.setOut(this.stdout);
    }

    @Test
    public void whenDrawSquare() {
        new Paint().draw(new Square());
        assertThat(
                new String(out.toByteArray()),
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

    @Test
    public void whenDrawTriangle() {
        new Paint().draw(new Triangle());
        assertThat(
                new String(out.toByteArray()),
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
