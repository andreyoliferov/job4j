package ru.job4j.calculator.extend;

import ru.job4j.calculator.Calculator;

/**
 * @autor aoliferov
 * @since 12.02.2019
 */
public class ExtendedCalculator extends Calculator {

    public void pow(double a, double b) {
        super.result = Math.pow(a, b);
    }
}
