package socket.filemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @autor aoliferov
 * @since 29.01.2019
 */
public class StartServerApp {

    private static final Logger LOG = LogManager.getLogger(StartServerApp.class.getName());
    private Socket socket;
    private IFileManager fileManager;

    private StartServerApp(IFileManager fileManager, Socket socket) {
        this.socket = socket;
        this.fileManager = fileManager;
    }

    private void init() throws IOException {
        OutputStream os = socket.getOutputStream();
        PrintWriter out = new PrintWriter(os, true);
        Menu menu = new Menu(fileManager, socket.getInputStream(), os);
        menu.showMenu();
        next(out);
        while (true) {
            try {
                menu.execute();
            } catch (Exception e) {
                out.println(e.getMessage());
                LOG.error(e);
            }
            next(out);
        }
    }

    private void next(PrintWriter out) {
        out.println("________________");
        out.println("write in command:");
        out.println();
    }

    public static void main(String[] args) throws IOException {
        try (Socket socket = new ServerSocket(3606).accept()) {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String dir = br.readLine();
            new StartServerApp(new FileManager(dir), socket).init();
        }
    }
}
