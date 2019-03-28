package ru.job4j;

/**
 * @autor aoliferov
 * @since 28.03.2019
 */
public class Task {

    private int paving(int a, int b, int start) {
        if (a > b) {
            a = a - b;
        } else {
            b = b - a;
        }
        start++;
        if (a == 0 || b == 0) {
            return start;
        }
        return paving(a, b, start);
    }

    public int paving(int a, int b) {
        return paving(a, b, 0);
    }

    public static void main(String[] args) {
        int result = new Task().paving(4, 3);
        System.out.println();
    }

}
