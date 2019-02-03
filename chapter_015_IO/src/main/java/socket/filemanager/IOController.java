package socket.filemanager;

import com.google.common.base.Joiner;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @autor aoliferov
 * @since 03.02.2019
 */
public abstract class IOController implements AutoCloseable {

    protected Socket socket;
    protected BufferedReader input;
    protected PrintWriter pw;
    protected BufferedOutputStream bos;

    public IOController(Socket socket) throws IOException {
        this.socket = socket;
        this.pw = new PrintWriter(socket.getOutputStream(), true);
        this.bos = new BufferedOutputStream(socket.getOutputStream());
        this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String readMessage() throws IOException {
        Joiner joiner = Joiner.on(System.lineSeparator());
        List<String> list = new ArrayList<>();
        String in = readLine();
        while (!in.isEmpty()) {
            list.add(in);
            in = readLine();
        }
        return joiner.join(list);
    }

    public String readLine() throws IOException {
        return input.readLine();
    }

    public void printLine(String data) {
        pw.println(data);
    }

    public void printLineLn(String data) {
        pw.println(data);
        pw.println();
    }

    public String[] parseParams(String command) {
        String[] params = command.split(" -");
        return Arrays.copyOf(params, 5);
    }

    public String[] parseParams() throws IOException {
        String[] temp = readMessage().split(System.lineSeparator());
        String send = temp[temp.length - 1];
        String[] params = send.split(" -");
        return Arrays.copyOf(params, 5);
    }

    public abstract void sendFile(String path) throws IOException;

    public abstract void saveFile(String path) throws IOException;
}
