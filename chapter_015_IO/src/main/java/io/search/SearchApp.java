package io.search;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

/**
 * Консольное приложение для поиска файлов;
 * @autor aoliferov
 * @since 05.02.2019
 */
public class SearchApp {

    @Parameter(names = "--help", help = true)
    private boolean help;

    @Parameter(names = {"-d", "--directory"},
            description = "Directory for search",
            required = true,
            validateWith = DirectoryValidate.class)
    private String dir;

    @Parameter(names = {"-o", "--output"},
            description = "Search result output",
            required = true,
            validateWith = OutputValidate.class)
    private String output;

    @Parameter(names = {"-m", "--mask"},
            description = "Mask search mode")
    private boolean mask;

    @Parameter(names = {"-f", "--full"},
            description = "Full name search mode")
    private boolean full;

    @Parameter(names = {"-r", "--regular"},
            description = "Regular expression mode")
    private boolean regular;

    @Parameter(names = {"-q", "--query"},
            description = "Search query",
            required = true)
    private String query;


    /**
     * Открыть проводник с выделенным файлом результата;
     */
    private void openExplorer(String path) throws IOException {
        Runtime.getRuntime().exec(
                "explorer.exe /select,"
                        + path.replaceAll("/", Matcher.quoteReplacement(File.separator)));
    }

    /**
     * Выполнить поиск;
     */
    private List<String> execute() throws IOException {
        return Files.walk(Paths.get(dir))
                .filter(path -> !Files.isDirectory(path))
                .filter(summary())
                .map(Path::toString)
                .collect(Collectors.toList());
    }

    /**
     * Выбор режима для поиска;
     */
    private Predicate<Path> summary() {
        Predicate<Path> result = null;
        if (mask) {
            result = maskPredicate;
        } else if (full) {
            result = fullPredicate;
        } else if (regular) {
            result = regularPredicate;
        }
        assert result != null;
        return result;
    }

    /**
     * Режимы поиска;
     */
    private Predicate<Path> maskPredicate = (name) -> name.getFileName().toString().contains(query);
    private Predicate<Path> fullPredicate = (name) -> name.getFileName().toString().equals(query);
    private Predicate<Path> regularPredicate = (name) -> name.getFileName().toString().matches(query);


    public static void main(String[] args) throws IOException {
        SearchApp search = new SearchApp();
        JCommander commander = JCommander.newBuilder().addObject(search).build();
        commander.parse(args);
        if (search.help) {
            commander.usage();
            return;
        }
        search.valideUniqMode();
        List<String> result = search.execute();
        try (PrintWriter writer = new PrintWriter(search.output)) {
            result.forEach(writer::println);
        }
        search.openExplorer(search.output);
    }

    /**
     * Валидация режима поиска
     * - должен быть указан только 1 из режимов;
     */
    private void valideUniqMode() {
        int result = (mask ? 1 : 0) + (full ? 1 : 0) + (regular ? 1 : 0);
        if (result != 1) {
            throw new ParameterException("Specify exactly one search mode: --mask, --full or --regular!");
        }
    }

    /**
     * Валидация начальной директории для поиска;
     */
    public static class DirectoryValidate implements IParameterValidator {
        public void validate(String name, String value) throws ParameterException {
            File file = new File(value);
            if (!(file.isDirectory() && file.exists())) {
                throw new ParameterException("Specify a valid existing directory!");
            }
        }
    }

    /**
     * Валидация выходного файла для результата;
     */
    public static class OutputValidate implements IParameterValidator {
        public void validate(String name, String value) throws ParameterException {
            File file = new File(value);
            if (file.isDirectory() && !name.contains(".txt")) {
                throw new ParameterException("Specify a valid path txt file!");
            }
        }
    }
}
