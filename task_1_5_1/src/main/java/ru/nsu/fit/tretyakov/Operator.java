package ru.nsu.fit.tretyakov;

import java.util.Deque;

/**
 * This interface contains only one method that allows Calculator
 * calculate value of the operand by its operator.
 */
public interface Operator {

    /**
     * Getter of the token of the specific operator.
     *
     * @return token of the specific operator
     */
    String getToken();

    /**
     * This function calculates value of the number.
     *
     * @param expressionStack is the stack of all operands in which
     *                        this function will get an operand.
     * @return transformed value of the number.
     */
    Number calculate(Deque<Number> expressionStack);

    default Number[] getValuesFromStack(int ARITY, Deque<Number> expressionStack) {
        Number[] operandsArray = new Number[ARITY];
        for (int i = 0; i < ARITY; i++) {
            operandsArray[i] = expressionStack.pollLast();
        }
        return operandsArray;
    }
}
