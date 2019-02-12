package ru.job4j.calculator;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.BiConsumer;

/**
 * @autor aoliferov
 * @since 07.02.2019
 */
public class InteractCalc {

    private Calculator calc = new Calculator();
    private Map<String, BiConsumer<Double, Double>> functions = new HashMap<>();
    private Scanner in;
    private PrintWriter out;

    private InteractCalc(InputStream in, OutputStream out) {
        this.in = new Scanner(in);
        this.out = new PrintWriter(out, true);
        this.addFunction();
    }

    private void addFunction() {
        functions.put("*", calc::multiple);
        functions.put("/", calc::div);
        functions.put("+", calc::add);
        functions.put("-", calc::subtract);
    }

    /**
     * Цикл вычисления.
     */
    private void execute() {
        boolean retry = true;
        while (retry) {
            out.println("Введите значение");
            double result = readDouble();
            boolean contin = true;
            while (contin) {
                out.println("Введите действие");
                String function = readFunction();
                out.println("Введите следующее значение");
                double next = readDouble();
                performFunction(result, next, function);
                result = calc.getResult();
                out.println(String.format("Результат = %s , продолжить(true) вычисление?", result));
                contin = readBool();
            }
            out.println(String.format("Результат = %s", calc.getResult()));
            out.println("Новый расчет(true) или выйти(false) из программы?");
            retry = readBool();
        }
    }

    /**
     * Прочитать значение.
     */
    private double readDouble() {
        while (!in.hasNextDouble()) {
            out.println(String.format("Некорректный ввод! {%s}", in.nextLine()));
        }
        return Double.parseDouble(in.nextLine());
    }

    /**
     * Прочитать действие калькулятора.
     */
    private String readFunction() {
        Set<String> allFunc = functions.keySet();
        String temp = "";
        boolean correct = false;
        while (!correct) {
            temp = in.nextLine();
            if (allFunc.contains(temp)) {
                correct = true;
            } else {
                out.println(String.format("Нет функции {%s}, введите корректную функцию!", temp));
            }
        }
        return temp;
    }

    /**
     * Прочитать решение пользователя.
     */
    private boolean readBool() {
        String temp = in.nextLine();
        return Boolean.parseBoolean(temp);
    }

    /**
     * Выполнить действие.
     */
    private void performFunction(double first, double second, String function) {
        functions.get(function).accept(first, second);
    }

    /**
     * Запуск калькулятора.
     */
    public static void main(String[] args) {
        InteractCalc interactCalc = new InteractCalc(System.in, System.out);
        interactCalc.execute();
    }
}
