package task;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @autor aoliferov
 * @since 05.01.2019
 */
public class StudentTest {

    private List<Student> list;

    @BeforeClass
    public void start() {
        this.list = Arrays.asList(
                new Student("", 1),
                new Student("", 2),
                new Student("", 3),
                new Student("", 5),
                new Student("", 7),
                new Student("", 3),
                new Student("", 4));
    }

    @Test
    public void testWhenBoundIsExist() {
        List<Student> result = Student.levelOf(list, 5);
        assertThat(result, is(Arrays.asList(new Student("", 7))));
    }

    @Test
    public void testWhenBoundIsNotExist() {
        List<Student> result = Student.levelOf(list, 6);
        assertThat(result, is(Arrays.asList(new Student("", 7))));
    }

    @Test
    public void testWhenBoundIsBiggest() {
        List<Student> result = Student.levelOf(list, 7);
        assertThat(result, is(new ArrayList<Student>()));
    }

    @Test
    public void testWhenBoundIsDouble() {
        List<Student> result = Student.levelOf(list, 2);
        assertThat(result, is(Arrays.asList(
                new Student("", 3),
                new Student("", 3),
                new Student("", 4),
                new Student("", 5),
                new Student("", 7))
        ));
    }
}
