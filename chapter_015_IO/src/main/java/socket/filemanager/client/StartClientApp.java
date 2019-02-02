package socket.filemanager.client;


import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * @autor aoliferov
 * @since 30.01.2019
 */
public class StartClientApp {

//    @Parameter(names = {"-d", "--directory"},
//            description = "Start directory",
//            required = true)
//    private String dir;
//
//    @Parameter(names = {"-a", "--address"},
//            description = "Server address",
//            required = true)
//    private String address;
//
//    @Parameter(names = {"-p", "--port"},
//            description = "Server port",
//            required = true)
//    private int port;

    public static void main(String[] args) throws IOException {
        StartClientApp app = new StartClientApp();
//        JCommander jc = JCommander.newBuilder().addObject(app).build();
//        jc.parse(args);
        boolean work = true;
        try(Socket socket = new Socket(InetAddress.getByName(/*app.address*/ "127.0.0.1" ), /*app.port*/ 3606);
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner console = new Scanner(System.in)) {
            output.println(/* app.dir */ "D:\\books\\java");
            while (work) {
                //3. отобразить результат (меню)
                String str = input.readLine();
                while (!str.isEmpty()) {
                    System.out.println(str);
                    str = input.readLine();
                }
                //1. ввести команду
                String command = console.nextLine();
                //2. отправить команду
                output.println(command);
                output.println();
                if ("exit".equals(command)) {
                    work = false;
                }
                if (command.contains("download")) {

                    String[] params = command.split(" -");
                    assert params[2] != null;
                    File file = new File(params[2]);
                    file.createNewFile();
                    try {

                        long size = Long.parseLong(new Scanner(socket.getInputStream()).nextLine());

                        System.out.println(size);

                        BufferedInputStream bis = new BufferedInputStream(socket.getInputStream());
                        FileOutputStream bos = new FileOutputStream(file);
                        byte[] byteArray = new byte[1024];


                        while (size > 0) {
                            int i = bis.read(byteArray);
                            bos.write(byteArray, 0, i);
                            size -= i;
                        }

                        System.out.println("!!!!!!");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

}
