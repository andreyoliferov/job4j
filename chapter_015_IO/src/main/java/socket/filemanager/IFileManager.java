package socket.filemanager;

import java.io.File;
import java.io.IOException;

/**
 * @autor aoliferov
 * @since 30.01.2019
 */
public interface IFileManager {

    File[] list();
    void downDir(String name);
    void upDir();
    File getFile(String name);
    String newPath(String name) throws IOException;

}
