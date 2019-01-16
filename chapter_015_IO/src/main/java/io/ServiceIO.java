package io;

import java.io.*;
import java.util.*;

/**
 * @autor aoliferov
 * @since 09.01.2019
 */
public class ServiceIO {

    /**
     * true если в потоке есть четное число
     * @param in входящий поток
     * @return
     */
    public boolean isEvenNumber(InputStream in) {
        boolean result = false;
        try (Scanner sc = new Scanner(in)) {
            while (sc.hasNext() && !result) {
                if (sc.hasNextInt()) {
                    result =  sc.nextInt() % 2 == 0;
                } else {
                    sc.next();
                }
            }
        }
        return result;
    }

    /**
     * Удаление всех перечисленных в массиве слов из входящего потока, запись в исходящий
     * @param in вход
     * @param out выход
     * @param abuse исключения
     * @throws IOException
     */
    public void dropAbuses(InputStream in, OutputStream out, String[] abuse) throws IOException {
        try (final PrintStream writer = new PrintStream(out);
             final BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            reader.lines()
                    .map(s -> Arrays.stream(abuse)
                            .reduce(s, (s1, s2) -> s1.replaceAll(s2, "")
                                    .replaceAll(" {2}", " ")
                            )
                    )
                    .forEach(writer::print);
        }
    }

    /**
     * Сканирует каталог и возвращает список всех файлов с указанными разширениями
     * Метод обхода в ширину
     * @param parent
     * @param exts
     * @return
     */
    public List<File> files(String parent, List<String> exts) {
        File parentDirectory = new File(parent);
        assert parentDirectory.isDirectory() && parentDirectory.exists() : "Incorrect directory!";
        List<File> directories = List.of(parentDirectory);
        List<File> result = new ArrayList<>();
        seach(exts, directories, result);
        return result;
    }

    /**
     * Рекурсивный метод поиска
     * @param exts расширение
     * @param directories директории для текущей итерации
     * @param result ссылка на результат
     */
    private void seach(List<String> exts, List<File> directories, List<File> result) {
        if (directories.size() == 0) {
            return;
        }
        List<File> newDirs = new ArrayList<>();
        for (File dir : directories) {
            File[] dirs = dir.listFiles(File::isDirectory);
            if (dirs != null) {
                newDirs.addAll(List.of(dirs));
            }
            File[] res = dir.listFiles((file) -> {
                if (file.isDirectory()) {
                    return false;
                }
                return exts.stream().anyMatch((ext) -> file.getName().contains(String.format(".%s", ext)));
            });
            if (res != null) {
                result.addAll(List.of(res));
            }
        }
        seach(exts, newDirs, result);
    }
}
