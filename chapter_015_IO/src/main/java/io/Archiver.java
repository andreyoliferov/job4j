package io;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @autor aoliferov
 * @since 17.01.2019
 */
public class Archiver {

    @Parameter(names = {"-d", "--directory"},
            description = "Directory for archiving",
            required = true)
    private static String dir;

    @Parameter(names = {"-e", "--extensions"},
            description = "Extensions of files",
            required = true)
    private static List<String> exts;

    @Parameter(names = {"-p", "--path"},
            description = "Path of arhive",
            required = true)
    private static String path;

    //java -jar chapter.jar -d d:\books -e pdf,djvu -p d:\project2.zip
    public static void main(String[] args) throws IOException {
        Archiver arh = new Archiver();
        JCommander commander = JCommander.newBuilder().addObject(arh).build();
        commander.parse(args);

        String basePath = dir.replaceAll("/", Matcher.quoteReplacement(File.separator));
        int toSubstr = basePath.length() + 1;

        try (final ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(path))) {
            Files.walk(Paths.get(dir))
                    .filter((path) -> {
                        if (Files.isDirectory(path)) {
                            return false;
                        }
                        String temp = path.toString();
                        temp = temp.substring(temp.indexOf(".") + 1);
                        return exts.contains(temp);
                    })
                    .collect(Collectors.toMap(
                            (path) -> path.toString().substring(toSubstr),
                            (path) -> path
                    ))
                    .forEach((key, value) -> {
                        ZipEntry ze = new ZipEntry(key);
                        try {
                            zip.putNextEntry(ze);
                            zip.write(Files.newInputStream(value).readAllBytes());
                            zip.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }
}
