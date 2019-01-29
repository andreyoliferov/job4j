package socket.chat;

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

    private static int port = 3606;

    /**
     * //TODO переделать на netty c множественным подключением
     * @param args
     * @throws IOException
     * @throws InterruptedException
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket =  new ServerSocket(port).accept();
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread t1 = new Thread(() -> {
            Scanner console = new Scanner(System.in);
            do {
                out.println(console.nextLine());
            } while (true);
        });

        Thread t2 = new Thread(() -> {
                String str;
                while (true) {
                    try {
                        str = in.readLine();
                        if (str.isEmpty()) {
                            break;
                        }
                        System.out.println(str);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
}
