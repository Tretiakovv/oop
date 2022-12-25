package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;

import ru.nsu.fit.tretyakov.Number;
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
     * @param expressionStack is the stack of all operands in which
     *                        this function will get an operand.
     * @return the result of raising one number to the power of another.
     */
    @Override
    public Number calculate(Deque<Number> expressionStack) {
        Number[] operands = getValuesFromStack(2, expressionStack);
        return new Number(Math.pow(operands[0].real(), operands[1].real()), 0);
    }
}
