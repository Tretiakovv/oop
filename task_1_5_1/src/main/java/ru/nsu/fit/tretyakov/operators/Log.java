package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;
import java.util.List;

import ru.nsu.fit.tretyakov.Operator;

/**
 * This class calculates the result of taking the logarithm
 * of one complex number to the natural base.
 */
public class Log implements Operator {

    public static final String token = "log";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates logarithm of the complex number.
     *
     * @return logarithm of the complex number.
     */
    @Override
    public Number calculate() {
        List<Number> complexOperands = getValuesFromStack(1);
        Number fst = complexOperands.get(0);

        return new Number(Math.log(fst.mod()), fst.arg());
    }
}
