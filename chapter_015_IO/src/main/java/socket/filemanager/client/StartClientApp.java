package socket.filemanager.client;


import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import socket.filemanager.StartServerApp;
import socket.filemanager.exceptions.ServerException;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Properties;

/**
 * @autor aoliferov
 * @since 30.01.2019
 */
public class StartClientApp {

    private static final Logger LOG = LogManager.getLogger(StartServerApp.class.getName());
    private IOControllerClient io;
    private boolean work = true;

    @Parameter(names = {"-d", "--directory"},
            description = "Start directory",
            required = true)
    private String dir;


    public StartClientApp(IOControllerClient io) {
        this.io = io;
    }

    private void execute(String command) throws IOException {
        if ("exit".equals(command) || command.contains("stop server")) {
            work = false;
        }
        if (command.contains("download")) {
            String[] params = io.parseParams(command);
            io.saveFile(params[2]);
        }
        if (command.contains("upload")) {
            String[] params = io.parseParams(command);
            io.sendFile(params[2]);
        }
    }

    public void start() throws IOException {
        io.printLine(dir);
        while (work) {
            io.consoleMessage();
            try {
                String command = io.userInput();
                io.printLineLn(command);
                execute(command);
            } catch (ServerException ne) {
                io.console(String.format("[FAILED] check input! %s", ne.getMessage()));
            } catch (Exception e) {
                io.console(String.format("[FAILED] check input! %s", e.getMessage()));
                io.printLine(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        try (InputStream is = StartClientApp.class.getClassLoader()
                .getResourceAsStream("configFileManager.properties")) {
            assert is != null;
            prop.load(is);
        }
        try (Socket socket = new Socket(
                InetAddress.getByName(prop.getProperty("address")),
                Integer.parseInt(prop.getProperty("port")));
            IOControllerClient io = new IOControllerClient(socket, System.out, System.in)) {
            StartClientApp app = new StartClientApp(io);
            JCommander commander = JCommander.newBuilder().addObject(app).build();
            commander.parse(args);
            app.start();
        }
    }

}
