package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;
import java.util.List;

import ru.nsu.fit.tretyakov.Operator;

/**
 * Cosine of the complex value of the number.
 */
public class Cosine implements Operator {

    public static final String token = "cos";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates cosine of the complex number.
     *
     * @return cosine of the complex number.
     */
    @Override
    public Number calculate() {
        List<Number> complexOperands = getValuesFromStack(2);
        Number fst = complexOperands.get(0);
        return new Number(fst.sinh(fst.imag()) * Math.cos(fst.real()),
                (-1) * fst.sinh(fst.imag()) * Math.sin(fst.real()));
    }
}
