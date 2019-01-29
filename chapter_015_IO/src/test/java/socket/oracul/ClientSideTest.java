package socket.oracul;

import com.google.common.base.Joiner;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.*;
import java.net.Socket;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @autor aoliferov
 * @since 29.01.2019
 */
public class ClientSideTest {
    private InputStream inputStream;
    private OutputStream outputStream;
    private ByteArrayInputStream consoleIn;
    private ByteArrayOutputStream consoleOut;
    private PrintStream defaultOut = System.out;
    private InputStream defaultIn = System.in;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String ANSWER_SEPARATOR = LINE_SEPARATOR.concat(LINE_SEPARATOR);

    @AfterMethod
    public void back() {
        System.setIn(defaultIn);
        System.setOut(defaultOut);
    }

    public void baseTest(String question, String answer) throws IOException {
        consoleIn = new ByteArrayInputStream(question.getBytes());
        consoleOut = new ByteArrayOutputStream();
        System.setIn(consoleIn);
        System.setOut(new PrintStream(consoleOut));
        Socket socket = mock(Socket.class);
        inputStream = new ByteArrayInputStream(answer.concat(ANSWER_SEPARATOR).getBytes());
        outputStream = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(outputStream);
        ClientSide cs = new ClientSide(socket);
        cs.init();
    }

    @DataProvider
    public Object[][] data() {
        return new Object[][]{
                {"exit",
                        Joiner.on(LINE_SEPARATOR).join("Ответ1", ""),
                        Joiner.on(LINE_SEPARATOR).join("Write in question.", "Ответ1", "")},
                {Joiner.on(LINE_SEPARATOR).join("hello", "exit"),
                        Joiner.on(ANSWER_SEPARATOR).join("Ответ1", "Ответ2", ""),
                        Joiner.on(LINE_SEPARATOR).join(
                                "Write in question.",
                                "Ответ1",
                                "Write in question.",
                                "Ответ2", "")},
        };
    }
    @Test(dataProvider = "data")
    public void whenAskThenAnswerAndOut(String question, String answer, String out) throws IOException {
        baseTest(question, answer);
        assertThat(outputStream.toString(), is(question.concat(LINE_SEPARATOR)));
        assertThat(consoleOut.toString(), is(out));
    }
}
