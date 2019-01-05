package task;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @autor aoliferov
 * @since 05.01.2019
 */
public class Student implements Comparable<Student> {

    private String name;
    private int scope;

    public Student(String name, int scope) {
        this.name = name;
        this.scope = scope;
    }

    @Override
    public int compareTo(Student o) {
        return this.scope - o.scope;
    }

    public static List<Student> levelOf(List<Student> students, int bound) {
        return students.stream()
                .sorted()
                .dropWhile(student -> student.scope <= bound)
                .collect(Collectors.toList());
    }

    /* работает быстрее в 2 раза */
    public static List<Student> levelOfFilter(List<Student> students, int bound) {
        return students.stream()
                .filter(student -> student.scope > bound)
                .collect(Collectors.toList());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return scope == student.scope
                && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, scope);
    }
}
