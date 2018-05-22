package strategy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Тестирование класса Paint
 * @autor Андрей Олиферов
 * @since 21.05.2018
 */
public class PaintTest {

    /** Поле для хранения данных вывода */
    private ByteArrayOutputStream out = new ByteArrayOutputStream();

    /** Вывод в консоль по умолчанию */
    private PrintStream stdout = System.out;

    @Before
    public void loadOutput() {
        System.setOut(new PrintStream(this.out));
    }

    @After
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
                                .append("+++++++\n")
                                .append("+++++++\n")
                                .append("+++++++\n")
                                .append("+++++++\n")
                                .append(System.lineSeparator())
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
                                .append("   +   \n")
                                .append("  +++  \n")
                                .append(" +++++ \n")
                                .append("+++++++\n")
                                .append(System.lineSeparator())
                                .toString()
                )
        );
    }
}
