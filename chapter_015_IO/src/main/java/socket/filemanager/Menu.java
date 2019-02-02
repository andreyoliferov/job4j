package socket.filemanager;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @autor aoliferov
 * @since 30.01.2019
 */
public class Menu {

    private BufferedReader input;
    private OutputStream out;
    private IFileManager logic;
    private Map<String, IActions> menuItems = new HashMap<>();

    public Menu(IFileManager logic, InputStream input, OutputStream out) {
        this.logic = logic;
        this.input = new BufferedReader(new InputStreamReader(input));
        this.out = out;
        this.fillAclions();
    }

    private void fillAclions() {
        IActions showList = new ShowList();
        IActions downDir = new DownDirectory();
        IActions upDir = new UpDirectory();
        IActions download = new DownloadFile();
        IActions upload = new UploadFile();
        IActions showMenu = new ShowMenu();
        menuItems.put(showList.getKey(), showList);
        menuItems.put(downDir.getKey(), downDir);
        menuItems.put(upDir.getKey(), upDir);
        menuItems.put(download.getKey(), download);
        menuItems.put(upload.getKey(), upload);
        menuItems.put(showMenu.getKey(), showMenu);
    }

    public void execute() throws IOException {
        StringBuilder sb = new StringBuilder();
        String in;
        do {
            in = input.readLine();
            sb.append(in);
        } while (!in.isEmpty());
        String[] params = sb.toString().split(" -");
        IActions action = menuItems.get(params[0]);
        if (action == null) {
            throw new NoSuchElementException("[FAILED] The corresponding command was not found, check the input!");
        }
        action.execute(logic, out, params);
    }

    public void showMenu() {
        menuItems.get("show menu").execute(null, out, null);
    }

    private class ShowList extends BaseAction {

        private ShowList(){
            super("list", "list");
        }

        @Override
        public void execute(IFileManager logic, OutputStream out, String[] params) {
            PrintWriter pw = new PrintWriter(out, true);
            File[] files = logic.list();
            Arrays.stream(files).map((file) -> {
                String name = file.getName();
                if (file.isDirectory()) {
                    name = String.format("[DIR] %s", name);
                }
                return name;
            }).forEach(pw::println);
        }
    }

    private class DownDirectory extends BaseAction {

        private DownDirectory(){
            super("down dir", "down dir -directory");
        }

        @Override
        public void execute(IFileManager logic, OutputStream out, String[] params) {
            PrintWriter pw = new PrintWriter(out, true);
            if (params[1] == null) {
                throw new NoSuchElementException("[FAILED] Attribute not specified, check input!");
            }
            logic.downDir(params[1]);
            pw.println("[DONE]");
        }
    }

    private class UpDirectory extends BaseAction {

        private UpDirectory(){
            super("up dir", "up dir");
        }

        @Override
        public void execute(IFileManager logic, OutputStream out, String[] params) {
            PrintWriter pw = new PrintWriter(out, true);
            logic.upDir();
            pw.println("[DONE]");
        }
    }

    private class DownloadFile extends BaseAction {

        private DownloadFile(){
            super("download", "download -file -directory");
        }

        @Override
        public void execute(IFileManager logic, OutputStream out, String[] params) {
            if (params[1] == null) {
                throw new NoSuchElementException("[FAILED] Attribute not specified, check input!");
            }
            File file = logic.getFile(params[1]);
            PrintWriter pw = new PrintWriter(out, true);


            try {

                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                BufferedOutputStream bos = new BufferedOutputStream(out);
                long s = file.length();
                pw.println(s);
                System.out.println(s);

                byte[] byteArray = new byte[1024];
                while (s > 0) {
                    int i = bis.read(byteArray);
                    bos.write(byteArray, 0, i);
                    s -= i;
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            pw.println("[DONE]");
        }
    }

    private class UploadFile extends BaseAction {

        private UploadFile(){
            super("upload", "upload -path");
        }

        @Override
        public void execute(IFileManager logic, OutputStream out, String[] params) {

        }
    }

    private class ShowMenu extends BaseAction {

        private ShowMenu(){
            super("show menu", "show menu");
        }

        @Override
        public void execute(IFileManager logic, OutputStream out, String[] params) {
            PrintWriter pw = new PrintWriter(out, true);
            pw.println("available commands:");
            menuItems.values().stream().map(IActions::getDisplay).forEach(pw::println);
        }
    }
}
