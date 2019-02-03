package socket.filemanager.client;

import socket.filemanager.IOController;
import socket.filemanager.exceptions.ClientException;
import socket.filemanager.exceptions.ServerException;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @autor aoliferov
 * @since 03.02.2019
 */
public class IOControllerClient extends IOController {

    private PrintStream console;
    private Scanner userInput;

    public IOControllerClient(Socket socket, PrintStream console, InputStream userInput) throws IOException {
        super(socket);
        this.console = console;
        this.userInput = new Scanner(userInput);
    }

    public void consoleMessage() throws IOException {
        String str = readLine();
        while (!str.isEmpty()) {
            console(str);
            str = readLine();
        }
    }

    public void console(String data) {
        console.println(data);
    }

    public String userInput() {
        return userInput.nextLine();
    }

    @Override
    public void sendFile(String path) throws IOException {
        printLine("client ready");
        if (path == null) {
            throw new ClientException("incorrect path!");
        }
        File file = new File(path);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            long s = file.length();
            printLine(String.valueOf(s));
            String message = readLine();
            if (!"server ready".equals(message)) {
                throw new ServerException(message);
            }
            byte[] byteArray = new byte[1024];
            while (s > 0) {
                int i = bis.read(byteArray);
                bos.write(byteArray, 0, i);
                bos.flush();
                s -= i;
            }
            String result = readLine();
            if (!"done".equals(result)) {
                throw new ServerException(message);
            }
        }
    }

    @Override
    public void saveFile(String path) throws IOException {
        String message = readLine();
        if (!"server ready".equals(message)) {
            throw new ServerException(message);
        }
        long size = Long.parseLong(readLine());
        if (path == null) {
            throw new ClientException("has no pathfile to save!");
        }
        File file = new File(path);
        if (!file.createNewFile()) {
            throw new ClientException("file not created!");
        }
        printLine("client ready");
        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
        try (FileOutputStream bos = new FileOutputStream(file)) {
            byte[] byteArray = new byte[1024];
            while (size > 0) {
                int i = bis.read(byteArray);
                bos.write(byteArray, 0, i);
                size -= i;
            }
        }
        printLine("done");
    }

    @Override
    public void close() throws IOException {
        console.close();
        userInput.close();
        socket.close();
    }
}
