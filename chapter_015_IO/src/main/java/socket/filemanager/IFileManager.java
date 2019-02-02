package socket.filemanager;

import java.io.File;

/**
 * @autor aoliferov
 * @since 30.01.2019
 */
public interface IFileManager {

    File[] list();
    void downDir(String name);
    void upDir();
    File getFile(String name);
    void uploadFile(String path);

}
