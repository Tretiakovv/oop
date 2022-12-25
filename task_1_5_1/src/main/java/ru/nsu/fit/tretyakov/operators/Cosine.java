package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;
import ru.nsu.fit.tretyakov.Number;
import ru.nsu.fit.tretyakov.Operator;

/**
 * Cosine of the complex value of the number.
 */
public class Cosine implements Operator {

    public static final String token = "cos";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates cosine of the complex number.
     *
     * @param expressionStack is the stack of all operands in which
     *                        this function will get an operand.
     * @return cosine of the complex number.
     */
    @Override
    public Number calculate(Deque<Number> expressionStack) {
        Number[] complexOperands = getValuesFromStack(2, expressionStack);
        Number fst = complexOperands[0];
        return new Number(fst.sinh(fst.imag()) * Math.cos(fst.real()),
                (-1) * fst.sinh(fst.imag()) * Math.sin(fst.real()));
    }
}
