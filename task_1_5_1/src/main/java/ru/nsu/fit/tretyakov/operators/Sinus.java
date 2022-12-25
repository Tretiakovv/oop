package ru.nsu.fit.tretyakov.operators;

import ru.nsu.fit.tretyakov.Number;
import ru.nsu.fit.tretyakov.Operator;

import java.util.Deque;

/**
 * Sinus of the complex value of the number.
 */
public class Sinus implements Operator {

    public static final String token = "sin";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates sinus of the complex number.
     *
     * @param expressionStack is the stack of all operands in which
     *                        this function will get an operand.
     * @return sinus of the complex number.
     */
    @Override
    public Number calculate(Deque<Number> expressionStack) {
        Number[] complexOperands = getValuesFromStack(2, expressionStack);
        Number fst = complexOperands[0];
        return new Number(fst.cosh(fst.imag()) * Math.sin(fst.real()),
                fst.sinh(fst.imag()) * Math.cos(fst.real()));
    }
}
