package socket.filemanager;

import com.google.common.base.Joiner;
import socket.filemanager.exceptions.ServerException;

import java.io.File;
import java.util.Arrays;
import java.util.Optional;

/**
 * @autor aoliferov
 * @since 30.01.2019
 */
public class FileManager implements IFileManager {

    /**
     * Текущая директория
     */
    private File current;

    public FileManager(String path) {
        File file = new File(path);
        assert file.isDirectory();
        this.current = file;
    }

    @Override
    public File[] list() {
        return current.listFiles();
    }

    @Override
    public void downDir(String name) {
        File[] dirs = current.listFiles(File::isDirectory);
        Optional<File> toDir = Arrays.stream(dirs).filter(dir ->  dir.getName().equals(name)).findFirst();
        current = toDir.orElseThrow(() -> new ServerException("[FAILED] Invalid directory name entered!"));
    }

    @Override
    public void upDir() {
        File parent = current.getParentFile();
        if (parent == null) {
            throw new ServerException("[FAILED] There is no parent directory!");
        }
        current = parent;
    }

    @Override
    public File getFile(String name) {
        File[] dirs = current.listFiles(File::isFile);
        Optional<File> toDownload = Arrays.stream(dirs).filter(dir ->  dir.getName().equals(name)).findFirst();
        return toDownload.orElseThrow(() -> new ServerException("Invalid name file!"));
    }

    @Override
    public String newPath(String name) {
        return Joiner.on(File.separator).join(current.getAbsolutePath(), name);
    }
}
