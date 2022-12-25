package ru.nsu.fit.tretyakov.operators;

import ru.nsu.fit.tretyakov.Number;
import ru.nsu.fit.tretyakov.Operator;

import java.util.Deque;

/**
 * This class computes the result of dividing one complex number by second.
 */
public class Divide implements Operator {

    public static final String token = "/";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates the result of dividing one complex number
     * by second.
     *
     * @param expressionStack is the stack of all operands in which
     *                        this function will get an operand.
     * @return the result of dividing one complex number by second.
     */
    @Override
    public Number calculate(Deque<Number> expressionStack) {
        Number[] complexOperands = getValuesFromStack(2, expressionStack);
        Number fst = complexOperands[0];
        Number snd = complexOperands[1];
        double den = Math.pow(snd.mod(), 2);

        return new Number((fst.real() * snd.real() + fst.imag() * snd.imag()) / den,
                (fst.imag() * snd.real() - fst.real() * snd.imag()) / den);
    }
}
