package socket.chat;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @autor aoliferov
 * @since 23.01.2019
 */
public class ClientSide {

    private static String ip = "127.0.0.1";
    private static int port = 3606;

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket(InetAddress.getByName(ip), port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        Thread t1 = new Thread(() -> {
            String str = null;
            while (true) {
                try {
                    str = in.readLine();
                    if (str.isEmpty()) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(str);
            }
        });

        Thread t2 = new Thread(() -> {
            Scanner console = new Scanner(System.in);
            do {
                out.println(console.nextLine());
            } while (true);
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

}
