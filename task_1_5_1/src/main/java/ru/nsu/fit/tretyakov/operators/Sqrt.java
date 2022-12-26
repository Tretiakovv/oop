package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;
import java.util.List;

import ru.nsu.fit.tretyakov.Operator;

/**
 * This class takes the square root of the complex number.
 */
public class Sqrt implements Operator {

    public static final String token = "sqrt";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates square root of the complex number.
     *
     * @return square root of the complex number.
     */
    @Override
    public Number calculate() {
        List<Number> complexOperands = getValuesFromStack(1);
        Number fst = complexOperands.get(0);
        double r = Math.sqrt(fst.mod());
        double theta = fst.arg() / 2;

        return new Number(r * Math.cos(theta), r * Math.sin(theta));
    }
}
