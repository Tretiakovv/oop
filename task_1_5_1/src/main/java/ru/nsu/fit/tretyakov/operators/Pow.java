package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;
import java.util.List;

import ru.nsu.fit.tretyakov.Operator;

/**
 * This class calculates the result of raising one number
 * to the power of another.
 */
public class Pow implements Operator {

    public static final String token = "pow";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates the result of raising one number
     * to the power of another.
     *
     * @return the result of raising one number to the power of another.
     */
    @Override
    public Number calculate() {
        List<Number> complexOperands = getValuesFromStack(2);
        return new Number(Math.pow(complexOperands.get(0).real(), complexOperands.get(1).real()), 0);
    }
}
