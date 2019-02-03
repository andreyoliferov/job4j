package socket.filemanager;

import socket.filemanager.exceptions.ClientException;
import socket.filemanager.exceptions.ServerException;

import java.io.*;
import java.net.Socket;

/**
 * @autor aoliferov
 * @since 03.02.2019
 */
public class IOControllerServer extends IOController {

    public IOControllerServer(Socket socket) throws IOException {
        super(socket);
    }

    public String readLine() throws IOException {
        return input.readLine();
    }

    public void next() {
        pw.println("________________");
        pw.println("write in command:");
        pw.println();
    }

    @Override
    public void sendFile(String path) throws IOException {
        printLine("server ready");
        if (path == null) {
            throw new ServerException("incorrect path!");
        }
        File file = new File(path);
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            long s = file.length();
            printLine(String.valueOf(s));
            String message = readLine();
            if (!"client ready".equals(message)) {
                throw new ClientException(message);
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
                throw new ClientException(message);
            }
        }
    }

    @Override
    public void saveFile(String path) throws IOException {
        String message = readLine();
        if (!"client ready".equals(message)) {
            throw new ClientException(message);
        }
        long size;
        try {
            size = Long.parseLong(readLine());
        } catch (NumberFormatException ne) {
            throw new ClientException("file not found!");
        }
        if (path == null) {
            throw new ServerException("has no pathfile to save!");
        }
        File file = new File(path);
        if (!file.createNewFile()) {
            throw new ServerException("file not created!");
        }
        printLine("server ready");
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
        socket.close();
    }
}
