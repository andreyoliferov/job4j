package ru.job4j.calculator;

import java.io.*;

/**
 * @autor aoliferov
 * @since 07.02.2019
 */
public class InteractCalc {

    private Calculator calc = new Calculator();
    private BufferedReader in;
    private PrintWriter out;
    private double result;
    private double next;
    private char func;

    private InteractCalc(InputStream in, OutputStream out) {
        this.in = new BufferedReader(new InputStreamReader(in));
        this.out = new PrintWriter(out, true);
    }

    /**
     * Цикл вычисления.
     */
    private void execute() throws IOException {
        boolean retry = true;
        while (retry) {
            out.println("Введите значение");
            result = readDouble();
            boolean contin = true;
            while (contin) {
                out.println("Введите действие");
                func = readFunction();
                out.println("Введите следующее значение");
                next = readDouble();
                performFunction();
                out.println(String.format("Результат = %s , продолжить(true) вычисление?", calc.getResult()));
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
    private Double readDouble() throws IOException {
        String temp;
        Double result = null;
        boolean correct = false;
        while (!correct) {
            temp = in.readLine();
            try {
                result = Double.parseDouble(temp);
                correct = true;
            } catch (NumberFormatException e) {
                out.println("Некорректный ввод!");
            }
        }
        return result;
    }

    /**
     * Прочитать действие калькулятора.
     */
    private char readFunction() throws IOException {
        String temp;
        char result = 0;
        boolean correct = false;
        while (!correct) {
            temp = in.readLine();
            result = temp.charAt(0);
            if (result == '*' || result == '/' || result == '+' || result == '-') {
                correct = true;
            } else {
                out.println("Введите корректное действие");
            }
        }
        return result;
    }

    /**
     * Прочитать решение пользователя.
     */
    private boolean readBool() throws IOException {
        String temp = in.readLine();
        return Boolean.parseBoolean(temp);
    }

    /**
     * Выполнить действие.
     */
    private void performFunction() {
        switch (func) {
            case '*' : calc.multiple(result, next);
            break;
            case '/' : calc.div(result, next);
            break;
            case  '+' : calc.add(result, next);
            break;
            case  '-' : calc.subtract(result, next);
            break;
            default: throw new IllegalArgumentException();
        }
        result = calc.getResult();
    }

    /**
     * Запуск калькулятора.
     */
    public static void main(String[] args) throws IOException {
        InteractCalc interactCalc = new InteractCalc(System.in, System.out);
        interactCalc.execute();
    }
}
