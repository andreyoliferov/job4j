package io;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.testng.Assert.assertEquals;

/**
 * @autor aoliferov
 * @since 09.01.2019
 */
public class ServiceIOTest {

    private ServiceIO io = new ServiceIO();

    @DataProvider
    public Object[][] data() {
        return new Object[][]{
                {"13", false},
                {"f", false},
                {"g3", false},
                {"g4", false},
                {"g4 3 f", false},
                {"14", true},
                {"8", true},
                {"8 d", true},
                {"13 rf 8 d", true},
        };
    }
    @Test(dataProvider = "data")
    public void whenInThenResult(String in, boolean except) {
        boolean result = io.isEvenNumber(new ByteArrayInputStream(in.getBytes(StandardCharsets.UTF_8)));
        assertThat(result, is(except));
    }

    @Test
    public void whenInThenFilter() throws IOException {
        try (ByteArrayInputStream bf = new ByteArrayInputStream("1 2 3 4 5".getBytes());
             ByteArrayOutputStream bs = new ByteArrayOutputStream()) {
            io.dropAbuses(bf, bs, new String[]{"3", "5"});
            assertThat(bs.toString(), is("1 2 4 "));
        }
    }


    @Test
    public void whenSearchPdfDjvuThenFindFive() {
        String tmpdir = System.getProperty("java.io.tmpdir");
        createFiles(tmpdir);
        List<File> result = io.files(String.format("%s/test", tmpdir), List.of("pdf", "djvu"));
        assertThat(result.size(), is(6));
        deleteFiles(tmpdir);
    }

    @Test
    public void whenSearchDjvuThenFindOne() {
        String tmpdir = System.getProperty("java.io.tmpdir");
        createFiles(tmpdir);
        List<File> result = io.files(String.format("%s/test", tmpdir), List.of("djvu"));
        assertThat(result.size(), is(1));
        assertEquals(result.get(0).getName(), "one1.djvu");
        deleteFiles(tmpdir);
    }

    @Test
    public void whenSearchExeThenEmpty() {
        String tmpdir = System.getProperty("java.io.tmpdir");
        createFiles(tmpdir);
        List<File> result = io.files(String.format("%s/test", tmpdir), List.of("exe"));
        assertThat(result.size(), is(0));
        deleteFiles(tmpdir);
    }

    private List<String> listPathDirs() {
        return List.of(
                "%s/test/one1/two1",
                "%s/test/one1/two2",
                "%s/test/one2/two1",
                "%s/test/one3"
        );
    }

    private List<String> listPathFiles() {
        return List.of(
                "%s/test/one1/two1/one1.txt",
                "%s/test/one1/two1/one2.pdf",
                "%s/test/one1/two1/one3.txt",
                "%s/test/one1/two2/one1.pdf",
                "%s/test/one1/two2/one2.pdf",
                "%s/test/one1/two2/one3.txt",
                "%s/test/one1/one1.djvu",
                "%s/test/one1/one3.pdf",
                "%s/test/one2/one1.txt",
                "%s/test/one3/one1.pdf"
        );
    }

    private void createFiles(String tmpdir) {
        listPathDirs().forEach((path) -> new File(String.format(path, tmpdir)).mkdirs());
        listPathFiles().forEach((path) -> {
            try {
                new File(String.format(path, tmpdir)).createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void deleteFiles(String tmpdir) {
        listPathFiles().forEach((path) -> new File(String.format(path, tmpdir)).delete());
        listPathDirs().forEach((path) -> new File(String.format(path, tmpdir)).delete());
    }
}
