package socket.oracul;

import com.google.common.base.Joiner;
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
public class ServerSideTest {

    private InputStream inputStream;
    private OutputStream outputStream;
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String ANSWER_SEPARATOR = LINE_SEPARATOR.concat(LINE_SEPARATOR);

    public void ask(String question) throws IOException {
        Socket socket = mock(Socket.class);
        inputStream = new ByteArrayInputStream(question.getBytes());
        outputStream = new ByteArrayOutputStream();
        when(socket.getInputStream()).thenReturn(inputStream);
        when(socket.getOutputStream()).thenReturn(outputStream);
        ServerSide ss = new ServerSide(socket);
        ss.init();
    }

    @DataProvider
    public Object[][] data() {
        return new Object[][]{
                {"exit", Joiner.on(ANSWER_SEPARATOR).join("By by", "")},
                {Joiner.on(LINE_SEPARATOR).join("hello", "exit"),
                        Joiner.on(ANSWER_SEPARATOR)
                                .join("Hello, dear friend, I'm a oracle.", "By by", "")},
                {Joiner.on(LINE_SEPARATOR).join("hello Oracle", "exit"),
                        Joiner.on(ANSWER_SEPARATOR)
                                .join(
                                        Joiner.on(System.lineSeparator()).join(
                                                "Ответ",
                                                "Ответ2",
                                                "Ответ3"),
                                "By by",
                                ""
                        )},
        };
    }
    @Test(dataProvider = "data")
    public void whenAskThenAnswer(String question, String answer) throws IOException {
        ask(question);
        assertThat(outputStream.toString(), is(answer));
    }
}
