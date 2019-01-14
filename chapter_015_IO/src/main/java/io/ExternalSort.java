package io;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @autor aoliferov
 * @since 14.01.2019
 */
public class ExternalSort {

    private final ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Внешняя сортировка большого файла
     * Сортировка по длине строки
     * @param source источник
     * @param distance отсортированный файл
     */
    public void sort(File source, File distance) throws IOException, InterruptedException {
        if (distance.exists()) {
            distance.delete();
        }
        File result = merge(sortSegment(segmentation(source, 0x8FFFFF)), 1).get(0);
        File temp = new File(result.getParent());
        Files.move(
                result.toPath(),
                distance.toPath()
        );
        temp.delete();
    }

    /**
     * Сегментация файла
     * @param source источник
     * @param maxSize размер сегмента
     * @return лист сегментов
     */
    private List<File> segmentation(File source, long maxSize) throws IOException {
        List<File> segments = new ArrayList<>();
        new File("src/main/java/io/temp/segments").mkdirs();
        try (BufferedReader br = new BufferedReader(new FileReader(source))) {
            int i = 1;
            String str = br.readLine();
            do {
                File file = new File(String.format("src/main/java/io/temp/segments/temp%s.txt", i));
                segments.add(file);
                try (RandomAccessFile temp = new RandomAccessFile(file, "rw")) {
                    while (str != null && temp.length() < maxSize) {
                        int l = 0;
                        while (l < 10000) {
                            temp.writeBytes(str + "\r\n");
                            str = br.readLine();
                            l++;
                        }
                    }
                    i++;
                }
            } while (str != null);
        }
        return segments;
    }

    /**
     * Соритровка сегментов
     * @param segments источник
     * @return лист сортированных сегментов
     */
    private List<File> sortSegment(List<File> segments) throws InterruptedException {
        new File("src/main/java/io/temp/merged0").mkdir();
        List<File> sorted = new ArrayList<>();
        AtomicInteger i = new AtomicInteger(1);
        List<Callable<Void>> tasks = new ArrayList<>();
        segments.forEach((segment) -> tasks.add(() -> {
            sortUnit(sorted, segment, i.getAndIncrement());
            return null;
        }));
        pool.invokeAll(tasks);
        new File("src/main/java/io/temp/segments").delete();
        return sorted;
    }

    /**
     * задание на сортировку 1 снгмента
     * @param sorted лист для добавления отсортированных файлов
     * @param segment сортируемый сегмент
     * @param i итерация
     */
    private void sortUnit(List<File> sorted, File segment, int i) {
        File file = new File(String.format("src/main/java/io/temp/merged0/temp%s.txt", i));
        sorted.add(file);
        try (BufferedReader br = new BufferedReader(new FileReader(segment));
             PrintStream writer = new PrintStream(new FileOutputStream(file))) {
            br.lines().sorted(Comparator.comparingInt(String::length)).forEach(writer::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert segment.delete();
    }

    /**
     * Слияние
     * @param sorted источник
     * @param iterate итерация
     * @return лист слитых файлов после итерации
     */
    private List<File> merge(List<File> sorted, int iterate) throws IOException, InterruptedException {

        /* выход из рекурсии */
        if (sorted.size() == 1)  {
            return sorted;
        }

        new File(String.format("src/main/java/io/temp/merged%s", iterate)).mkdir();
        List<File> temp = new ArrayList<>();
        int size = sorted.size();

        List<Callable<Void>> tasks = new ArrayList<>();
        for (int i = 1; i < (size % 2 == 0 ? size : size - 1); i += 2) {
            int finalI = i;
            tasks.add(() -> {
                mergeUnit(sorted, temp, iterate, finalI);
                return null;
            });
        }
        pool.invokeAll(tasks);

        if (size % 2 != 0) {
            File last = new File(String.format("src/main/java/io/temp/merged%s/temp%s.txt", iterate, (size + 1) / 2));
            Files.copy(sorted.get(size - 1).toPath(), last.toPath());
            temp.add(last);
            assert sorted.get(size - 1).delete();
        }

        File forDel = new File(String.format("src/main/java/io/temp/merged%s", iterate - 1));
        Arrays.stream(forDel.listFiles()).forEach(File::delete);
        forDel.delete();

        return merge(temp, iterate + 1);
    }

    /**
     * Задание на слияние 2 файлов
     * @param sorted источник
     * @param temp лист для результата
     * @param iterate итерация слияния
     * @param i внутренняя итерация
     */
    private void mergeUnit(List<File> sorted, List<File> temp, int iterate, int i) throws IOException {
        File file = new File(String.format("src/main/java/io/temp/merged%s/temp%s.txt", iterate, (i + 1) / 2));
        try (BufferedReader sc1 = new BufferedReader(new FileReader(sorted.get(i - 1)));
             BufferedReader sc2 = new BufferedReader(new FileReader(sorted.get(i)));
             PrintStream writer = new PrintStream(file)) {
            String s1 = sc1.readLine();
            String s2 = sc2.readLine();
            boolean b;
            while (s1 != null || s2 != null) {
                if (s2 == null || (s1 != null && (s1.length() <= s2.length()))) {
                    writer.println(s1);
                    b = true;
                } else {
                    writer.println(s2);
                    b = false;
                }
                if (b) {
                    s1 = sc1.readLine();
                }
                if (!b) {
                    s2 = sc2.readLine();
                }
            }
        }
        temp.add(file);
    }
}
