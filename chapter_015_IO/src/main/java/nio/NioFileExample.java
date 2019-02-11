package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @autor aoliferov
 * @since 10.02.2019
 */
public class NioFileExample {

    public void copyExample() throws IOException {
        FileChannel input = FileChannel.open(
                Paths.get("D:\\text1.txt"),
                StandardOpenOption.READ
        );
        FileChannel output = FileChannel.open(
                Paths.get("D:\\text2.txt"),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING
        );

        ByteBuffer buffer = ByteBuffer.allocate(1026 * 16); //16 кб

        while (input.read(buffer) != -1) {
            buffer.flip();
            output.write(buffer);
            buffer.compact();
        }
    }

    /**
     * Пример содержит реализацию CompletionHandler (реализует действия при завершении чтения/записи) и рекурсию
     * на его основе.
     */
    public void asychconicCopyExample() throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(1026 * 16); //16 кб
        //асинхронные каналы
        AsynchronousFileChannel source = AsynchronousFileChannel.open(
                Paths.get("D:\\text1.txt"),
                StandardOpenOption.READ
        );
        AsynchronousFileChannel target = AsynchronousFileChannel.open(
                Paths.get("D:\\text2.txt"),
                StandardOpenOption.CREATE,
                StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING
        );

        //read and write возвращают Future
        //Future<Integer> taskRead = source.read(buffer, 0);
        //Future<Integer> taskWrite = target.write(buffer, 0);

        copy(buffer, source, target, 0);
    }

    private void copy(ByteBuffer buffer,
                     AsynchronousFileChannel sourse,
                     AsynchronousFileChannel target,
                     int filePosition) {
        sourse.read(buffer, filePosition, target, new CompletionHandler<>() {
            @Override
            public void completed(Integer result, AsynchronousFileChannel attachment) {
                writePartIterate(buffer, sourse, target, filePosition);
            }

            @Override
            public void failed(Throwable exc, AsynchronousFileChannel attachment) {
                exc.printStackTrace();
            }
        });
    }

    /**
     * Часть итерации с записью и входом в рекурсию.
     */
    private void writePartIterate(ByteBuffer buffer,
                                 AsynchronousFileChannel sourse,
                                 AsynchronousFileChannel target,
                                 int filePosition) {
        buffer.flip();
        target.write(buffer, filePosition, target, new CompletionHandler<>() {
            @Override
            public void completed(Integer result, AsynchronousFileChannel attachment) {
                if (result != 0) {
                    int newPosition = filePosition + result;
                    buffer.compact();
                    copy(buffer, sourse, target, newPosition);
                }
            }

            @Override
            public void failed(Throwable exc, AsynchronousFileChannel attachment) {
                exc.printStackTrace();
            }
        });
    }
}
