package socket.oracul;

import com.google.common.base.Joiner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @autor aoliferov
 * @since 23.01.2019
 */
public class ServerSide {

    private Socket socket;

    public ServerSide(Socket socket) {
        this.socket = socket;
    }

    public void init() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            String ask;
            do {
                System.out.println("wait command ...");
                ask = in.readLine();
                String answer = this.answer(ask);
                out.println(answer);
                out.println();
            } while (!"exit".equals(ask));
        }
    }

    /**
     * Ленивая реализация, подразумевается сторонний сервис, подготавливающий ответы
     * @param question вопрос
     * @return ответ
     */
    private String answer(String question) {
        String result;
        if ("hello".equals(question)) {
            result = "Hello, dear friend, I'm a oracle.";
        } else if ("exit".equals(question)) {
            result = "By by";
        } else {
            result = Joiner.on(System.lineSeparator()).join("Ответ", "Ответ2", "Ответ3");
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        try (Socket socket =  new ServerSocket(3606).accept()) {
            ServerSide ss = new ServerSide(socket);
            ss.init();
        }
    }
}
