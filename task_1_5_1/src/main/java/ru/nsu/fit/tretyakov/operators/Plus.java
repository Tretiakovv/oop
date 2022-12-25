package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;

import ru.nsu.fit.tretyakov.Operator;

/**
 * This class computes the result of adding one complex number to second.
 */
public class Plus implements Operator {

    public static final String token = "+";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates result of adding one complex number to second.
     *
     * @param expressionStack is the stack of all operands in which
     *                        this function will get an operand.
     * @return result of adding one complex number to second.
     */
    @Override
    public Number calculate(Deque<Number> expressionStack) {
        Number[] complexOperands = getValuesFromStack(2, expressionStack);
        Number fst = complexOperands[0];
        Number snd = complexOperands[1];

        return new Number(fst.real() + snd.real(), fst.imag() + snd.imag());
    }
}
