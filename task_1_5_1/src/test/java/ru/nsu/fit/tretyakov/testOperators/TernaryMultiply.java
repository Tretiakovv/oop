package ru.nsu.fit.tretyakov.testOperators;

import ru.nsu.fit.tretyakov.Number;
import ru.nsu.fit.tretyakov.Operator;

import java.util.Deque;

/**
 * This class calculates multiply value of three operands.
 */
public class TernaryMultiply implements Operator {

    public static final String token = "***";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates result of dividing three numbers.
     *
     * @param expressionStack is the stack of all operands in which
     *                        this function will get an operand.
     * @return result of dividing three numbers.
     */
    @Override
    public Number calculate(Deque<Number> expressionStack) {
        Number[] operands = getValuesFromStack(3, expressionStack);
        return new Number(operands[0].real() * operands[1].real()
                * operands[2].real(), 0);
    }
}
