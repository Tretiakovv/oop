package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;

import ru.nsu.fit.tretyakov.Operator;

/**
 * This class computes the result of multiplying one complex number by second.
 */
public class Multiply implements Operator {

    public static final String token = "*";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates result of multiplying one complex number by second.
     *
     * @param expressionStack is the stack of all operands in which
     *                        this function will get an operand.
     * @return result of multiplying one complex number by second.
     */
    @Override
    public Number calculate(Deque<Number> expressionStack) {
        Number[] complexOperands = getValuesFromStack(2, expressionStack);
        Number fst = complexOperands[0];
        Number snd = complexOperands[1];

        return new Number(fst.real() * snd.real() - fst.imag() * snd.imag(),
                fst.real() * snd.imag() + fst.imag() * snd.real());
    }
}
