package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;
import java.util.List;

import ru.nsu.fit.tretyakov.Operator;

/**
 * This class converts degree value to the radian value of the angle.
 */
public class Rad implements Operator {

    public static final String token = "rad";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function converts degree value of the angle to the radian value.
     *
     * @return radian value of the angle.
     */
    @Override
    public Number calculate() {
        List<Number> complexOperands = getValuesFromStack(1);
        return new Number(Math.toRadians(complexOperands.get(0).real()), 0);
    }
}
