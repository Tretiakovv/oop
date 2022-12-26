package ru.nsu.fit.tretyakov.test_operators;

import java.util.Deque;
import java.util.List;

import ru.nsu.fit.tretyakov.operators.Number;
import ru.nsu.fit.tretyakov.Operator;

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
    public Number calculate() {
        List<Number> complexOperands = getValuesFromStack(3);
        return new Number(complexOperands.get(0).real() * complexOperands.get(1).real()
                * complexOperands.get(2).real(), 0);
    }
}
