package socket.oracul;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @autor aoliferov
 * @since 23.01.2019
 */
public class ClientSide {

    private Socket socket;

    public ClientSide(Socket socket) {
        this.socket = socket;
    }

    public void init() throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader console = new BufferedReader(new InputStreamReader(System.in))) {
            String question;
            do {
                System.out.println("Write in question.");
                question = console.readLine();
                out.println(question);
                String str = in.readLine();
                while (!str.isEmpty()) {
                    System.out.println(str);
                    str = in.readLine();
                }
            } while (!"exit".equals(question));
        }
    }

    public static void main(String[] args) throws IOException {
        try (Socket socket = new Socket(InetAddress.getByName("127.0.0.1"), 3606)) {
            ClientSide cs = new ClientSide(socket);
            cs.init();
        }
    }
}
