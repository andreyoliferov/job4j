package socket.filemanager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import socket.filemanager.client.StartClientApp;
import socket.filemanager.exceptions.ClientException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @autor aoliferov
 * @since 29.01.2019
 * Получилось кривовато.
 * 1. Изначально нужно было определить и реализовать свой простой протокол общения клиента и сервера,
 * сделать пакет по форме, были сложности как раз из за согласованности приема-передачи данных, из за безсистемного
 * общения клиента и сервера напрямую через открытые потоки (единственная используемая система, перенос строки и
 * отделение сообщений пустыми строками). Для такой простой системы это простительно, хоть и вызвало сложность
 * синхронизации, если бы система была сильно сложнее я бы запутался в пересылаемых данных.
 * 2. Входная/выходная точка должна была быть одна. Реализована в виде очереди пакетов.
 * 3. Нужно было четко определить структуру пакета, где начинается и заканчивается пакет, механизм интерпретации пакета.
 * 4. А еще лучше использовать netty и асинхронный ввод-вывод NIO, попробовать как будет время.
 */
public class StartServerApp {

    private static final Logger LOG = LogManager.getLogger(StartServerApp.class.getName());
    private ServerSocket serverSocket;
    private ExecutorService pool = Executors.newFixedThreadPool(5);
    private volatile List<Menu> tasks = new ArrayList<>();
    private volatile List<Socket> connections = new ArrayList<>();
    private boolean serverRunning = true;

    /**
     * Инициализация 1-ого подключения.
     */
    private void init(Socket socket) throws IOException {
        IOControllerServer io = new IOControllerServer(socket);
        IFileManager fileManager = new FileManager(io.readLine());
        Menu menu = new Menu(fileManager, io, this);
        tasks.add(menu);
        menu.showMenu();
        while (menu.isRunning()) {
            try {
                menu.execute();
            } catch (ClientException re) {
                LOG.error(re);
            } catch (Exception e) {
                io.printLine(e.getMessage());
                LOG.error(e);
            }
            io.next();
        }
    }

    /**
     * Начать принимать подключения.
     */
    private void startConnections(int port) throws IOException {
        while (serverRunning) {
            Socket socket;
            try (ServerSocket ss = new ServerSocket(port)) {
                serverSocket = ss;
                socket = serverSocket.accept();
                connections.add(socket);
            }
            pool.submit(
                    new Thread(() -> {
                        try {
                            init(socket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    })
            );
        }
    }

    /**
     * Остановка сервера
     * при мягкой остановке сервер завершения работы всех клиентов и не принимает новых
     *
     * @param full жесткая остановка
     */
    public void stopServer(boolean full) throws IOException {
        serverRunning = false;
        serverSocket.close();
        pool.shutdown();
        if (full) {
            tasks.forEach(Menu::stop);
            for (Socket connection : connections) {
                connection.shutdownInput();
                connection.shutdownOutput();
                connection.close();
            }
        }
    }

    /**
     * Запуск сервера.
     */
    public static void main(String[] args) throws IOException {
        Properties prop = new Properties();
        try (InputStream is = StartClientApp.class.getClassLoader().getResourceAsStream("configFileManager.properties")) {
            assert is != null;
            prop.load(is);
        }
        StartServerApp server = new StartServerApp();
        server.startConnections(Integer.parseInt(prop.getProperty("port")));
    }
}
