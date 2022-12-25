package ru.nsu.fit.tretyakov.operators;

import ru.nsu.fit.tretyakov.Number;
import ru.nsu.fit.tretyakov.Operator;

import java.util.Deque;

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
     * @param expressionStack is the stack of all operands in which
     *                        this function will get an operand.
     * @return radian value of the angle.
     */
    @Override
    public Number calculate(Deque<Number> expressionStack) {
        Number[] operands = getValuesFromStack(1, expressionStack);
        return new Number(Math.toRadians(operands[0].real()), 0);
    }
}
