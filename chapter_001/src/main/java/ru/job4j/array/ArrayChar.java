package ru.job4j.array;

/**
 * 5.5. Слова начинается с ... [#41585]
 * @autor Андрей Олиферов
 * @since 09.05.2018
 */
public class ArrayChar {

    private char[] data;

    public ArrayChar(String line) {
        this.data = line.toCharArray();
    }

    /**
     * Проверяет. что слово начинается с префикса.
     * @param prefix префикс.
     * @return если слово начинаеться с префикса
     */
    public boolean startWith(String prefix) {
        boolean result = true;
        char[] value = prefix.toCharArray();
        for (int i = 0; i < value.length; i++) {
            result = value[i] == data[i];
        }
        return result;
    }
}
