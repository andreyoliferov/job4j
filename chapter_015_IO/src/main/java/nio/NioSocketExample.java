package nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @autor aoliferov
 * @since 09.02.2019
 */
public class NioSocketExample {

    private void review() throws IOException {
        //[ БУФФЕРЫ ДАННЫХ ]
        ByteBuffer bb = ByteBuffer.allocate(256); //непрямой байтовый буфер
        ByteBuffer temporaryBb = ByteBuffer.allocateDirect(256); //создание
        // прямого буфера, блока памяти, выделенного вне JVM
        int position = bb.position(); //указатель
        bb.clear(); //дает разрешение на перезапись данных, сдвигает указатель на 0
        IntBuffer intBb = bb.asIntBuffer(); //отличается работой с указателем,
        // bb - передвижение побайтово, intBB по int(4 байта)
        intBb.put(44);
        bb.put("привет".getBytes());
        bb.get(); //получить элемент указателя и передвинуть указатель
        bb.get(2); //получить элемент по индексу
        byte[] toRead = new byte[32];
        bb.get(toRead); //считать в массив
        bb.flip(); //limit  = текущая позиция, позиция = 0 - подготовка к чтению
        bb.compact(); //байты от текущей позиции до limit копируются в начало буфера, позиция буфера устанавливается
        // равной количеству скопированных байтов, а не нулю, так что за вызовом этого метода может сразу
        // же последовать вызов другого относительного метода put.

        // [ КАНАЛЫ ]
        ServerSocket ss = new ServerSocket(3000);
        Socket socket = ss.accept();
        SocketChannel channel = socket.getChannel();
        //канал поумлчанию блокирующий, устанавливаем параметр (блокировка = false)
        channel.configureBlocking(false);
    }

    /**
     * Низкоуровневый вывод с сокета.
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        // [ СЕЛЕКТОРЫ ]
        Selector selector = Selector.open(); //селектор работает с множеством каналов, канал пишет данные в буфер
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.bind(new InetSocketAddress(3606));
        ssc.configureBlocking(false);
        ssc.register(selector, SelectionKey.OP_ACCEPT); //регистрируем канал
        ByteBuffer readBuffer = ByteBuffer.allocate(4); //буфер для чтения
        while (true) {
            int select = selector.select(); //блокирующий метод, пропускает когда на
            // зарегистрированных каналах есть// несчитанные данные
            if (select == 0) { //по документации может возвращать 0
                continue;
            }
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while (keys.hasNext()) {
                SelectionKey selectionKey = keys.next();
                try {
                    if (selectionKey.channel() == ssc) {
                        SocketChannel socketChannel = ssc.accept();
                        if (socketChannel != null) {
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ); //регистрация канала на чтение
                        }
                    } else {
                        ((SocketChannel) selectionKey.channel()).read(readBuffer); //возвращает количество прочитанных
                        // байтов, 0 или -1 если достиг конца,если заключить в цикл while(read > 0) соответственно пока
                        // этот канал не прочитаем полностью, к следующим не приступим без while получается что читаем
                        // по буфферу из каждого зарегистированного канала
                        readBuffer.flip();
                        String result = new String(readBuffer.array(), readBuffer.position(), readBuffer.remaining());
                        readBuffer.clear();
                        System.out.println(result);
                    }
                } finally {
                    keys.remove();
                }
            }
        }
    }
}
