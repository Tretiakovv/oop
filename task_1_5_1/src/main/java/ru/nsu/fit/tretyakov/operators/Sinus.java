package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;
import java.util.List;

import ru.nsu.fit.tretyakov.Operator;

/**
 * Sinus of the complex value of the number.
 */
public class Sinus implements Operator {

    public static final String token = "sin";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates sinus of the complex number.
     *
     * @return sinus of the complex number.
     */
    @Override
    public Number calculate() {
        List<Number> complexOperands = getValuesFromStack(1);
        Number fst = complexOperands.get(0);
        return new Number(fst.cosh(fst.imag()) * Math.sin(fst.real()),
                fst.sinh(fst.imag()) * Math.cos(fst.real()));
    }
}
