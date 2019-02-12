package ru.job4j.calculator.extend;

import ru.job4j.calculator.InteractCalc;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @autor aoliferov
 * @since 12.02.2019
 */
public class ExtendedInteractCalc extends InteractCalc {

    public ExtendedInteractCalc(InputStream in, OutputStream out, ExtendedCalculator calc) {
        super(in, out, calc);
    }

    @Override
    protected void init() {
        functions.put("*", calc::multiple);
        functions.put("/", calc::div);
        functions.put("+", calc::add);
        functions.put("-", calc::subtract);
        functions.put("pow", ((ExtendedCalculator) calc)::pow);
    }
}
