package ru.job4j.loop;

/**
 * 4.1. Подсчет суммы чётных чисел в диапазоне [#192]
 * @autor Андрей Олиферов
 * @since 05.05.2018
 */
public class Counter {

    /**
     * Подсчет суммы чисел от start до fibish
     * @param start начальное число счетчика
     * @param finish последнее число счетчика
     * @return сумма
     */
    public int counterSum (int start, int finish){
        int sum = 0;
        if(start > finish){
            int temp = start;
            start = finish;
            finish = temp;
        }
        for(int i = start; i <= finish; i++) {
            if(i % 2 == 0){
                sum += i;
            }
        }
        return sum;
    }
}
