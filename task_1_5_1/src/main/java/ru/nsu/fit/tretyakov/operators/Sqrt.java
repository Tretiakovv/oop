package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;

import ru.nsu.fit.tretyakov.Number;
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
     * @param expressionStack is the stack of all operands in which
     *                        this function will get an operand.
     * @return square root of the complex number.
     */
    @Override
    public Number calculate(Deque<Number> expressionStack) {
        Number[] complexOperands = getValuesFromStack(2, expressionStack);
        Number fst = complexOperands[0];
        double r = Math.sqrt(fst.mod());
        double theta = fst.arg() / 2;

        return new Number(r * Math.cos(theta), r * Math.sin(theta));
    }
}
