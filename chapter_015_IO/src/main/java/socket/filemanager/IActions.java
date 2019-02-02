package socket.filemanager;

import javax.naming.directory.NoSuchAttributeException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * @autor aoliferov
 * @since 29.01.2019
 */
public interface IActions {

    String getKey();
    String getDisplay();
    void execute(IFileManager logic, OutputStream out, String[] params);

}
