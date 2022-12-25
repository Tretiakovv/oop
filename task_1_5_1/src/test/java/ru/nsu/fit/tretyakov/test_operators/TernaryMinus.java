package ru.nsu.fit.tretyakov.test_operators;

import java.util.Deque;

import ru.nsu.fit.tretyakov.operators.Number;
import ru.nsu.fit.tretyakov.Operator;

/**
 * This class calculates subtraction value of three operands.
 */
public class TernaryMinus implements Operator {

    public static final String token = "---";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates result of subtracting three numbers.
     *
     * @param expressionStack is the stack of all operands in which
     *                        this function will get an operand.
     * @return result of subtracting three numbers.
     */
    @Override
    public Number calculate(Deque<Number> expressionStack) {
        Number[] operands = getValuesFromStack(3, expressionStack);
        return new Number(operands[0].real() - operands[1].real()
                - operands[2].real(), 0);
    }
}
