package socket.filemanager;

import socket.filemanager.exceptions.ServerException;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor aoliferov
 * @since 30.01.2019
 */
public class Menu {

    private IOControllerServer io;
    private IFileManager logic;
    private Map<String, IActions> menuItems = new HashMap<>();
    private boolean connectionRunning = true;
    private StartServerApp server;

    public Menu(IFileManager logic, IOControllerServer io, StartServerApp server) {
        this.io = io;
        this.logic = logic;
        this.server = server;
        this.fillAclions();
    }

    private void fillAclions() {
        IActions showList = new ShowList();
        IActions downDir = new DownDirectory();
        IActions upDir = new UpDirectory();
        IActions download = new DownloadFile();
        IActions upload = new UploadFile();
        IActions showMenu = new ShowMenu();
        IActions exit = new Exit();
        IActions stopServer = new StopServer();
        menuItems.put(showList.getKey(), showList);
        menuItems.put(downDir.getKey(), downDir);
        menuItems.put(upDir.getKey(), upDir);
        menuItems.put(download.getKey(), download);
        menuItems.put(upload.getKey(), upload);
        menuItems.put(showMenu.getKey(), showMenu);
        menuItems.put(exit.getKey(), exit);
        menuItems.put(stopServer.getKey(), stopServer);
    }

    public void showMenu() throws IOException {
        menuItems.get("show menu").execute(null);
        io.next();
    }

    public void execute() throws IOException {
        String[] params = io.parseParams();
        IActions action = menuItems.get(params[0]);
        if (action == null) {
            throw new ServerException("[FAILED] The corresponding command was not found!");
        }
        action.execute(params);
    }

    public boolean isRunning() {
        return connectionRunning;
    }

    public void stop() {
        connectionRunning = false;
    }

    /**
     * Показать содержание текущей директории.
     */
    private class ShowList extends BaseAction {

        private ShowList() {
            super("list", "list");
        }

        @Override
        public void execute(String[] params) {
            File[] files = logic.list();
            Arrays.stream(files).map((file) -> {
                String name = file.getName();
                if (file.isDirectory()) {
                    name = String.format("[DIR] %s", name);
                }
                return name;
            }).forEach(io::printLine);
        }
    }

    /**
     * Перейти во внутреннюю директорию.
     */
    private class DownDirectory extends BaseAction {

        private DownDirectory() {
            super("down dir", "down dir -directory");
        }

        @Override
        public void execute(String[] params) {
            if (params[1] == null) {
                throw new ServerException("[FAILED] Attribute not specified!");
            }
            logic.downDir(params[1]);
            io.printLine("[DONE]");
        }
    }

    /**
     * Перейти к родительской директории.
     */
    private class UpDirectory extends BaseAction {

        private UpDirectory() {
            super("up dir", "up dir");
        }

        @Override
        public void execute(String[] params) {
            logic.upDir();
            io.printLine("[DONE]");
        }
    }

    /**
     * Скачать файл с сервера.
     */
    private class DownloadFile extends BaseAction {

        private DownloadFile() {
            super("download", "download -file -directory");
        }

        @Override
        public void execute(String[] params) throws IOException {
            if (params[1] == null) {
                throw new ServerException("Attribute not specified!");
            }
            File file = logic.getFile(params[1]);
            io.sendFile(file.getAbsolutePath());
            io.printLine("[DONE]");
        }
    }

    /**
     * Загрузить файл на сервер.
     */
    private class UploadFile extends BaseAction {

        private UploadFile() {
            super("upload", "upload -name -path");
        }

        @Override
        public void execute(String[] params) throws IOException {
            if (params[1] == null) {
                throw new ServerException("[FAILED] write in name!");
            }
            String newPath = logic.newPath(params[1]);
            io.saveFile(newPath);
            io.printLine("[DONE]");
        }
    }

    /**
     * Показать меню.
     */
    private class ShowMenu extends BaseAction {

        private ShowMenu() {
            super("show menu", "show menu");
        }

        @Override
        public void execute(String[] params) {
            io.printLine("available commands:");
            menuItems.values().stream().map(IActions::getDisplay)
                    .filter((str) -> !str.equals("stop server"))
                    .forEach(io::printLine);
        }
    }

    /**
     * Отключение клиента.
     */
    private class Exit extends BaseAction {

        private Exit() {
            super("exit", "exit");
        }

        @Override
        public void execute(String[] params) {
            connectionRunning = false;
        }
    }

    /**
     * Остановка сервера.
     */
    private class StopServer extends BaseAction {

        private StopServer() {
            super("stop server", "stop server");
        }

        @Override
        public void execute(String[] params) throws IOException {
            boolean full = false;
            if (params[2] != null) {
                full = Boolean.parseBoolean(params[2]);
            }
            if (params[1] != null && params[1].equals("password")) {
                connectionRunning = false;
                server.stopServer(full);
            }
        }
    }
}
