package ru.nsu.fit.tretyakov;

import ru.nsu.fit.tretyakov.operators.Number;

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

    /**
     * This method gets operands from the stack arity times.
     *
     * @param arity           is the number of taken operands from the stack
     * @param expressionStack is the calculator's expression stack
     * @return list of taken operands from the stack
     */
    default Number[] getValuesFromStack(int arity, Deque<Number> expressionStack) {
        Number[] operandsArray = new Number[arity];
        for (int i = 0; i < arity; i++) {
            operandsArray[i] = expressionStack.pollLast();
        }
        return operandsArray;
    }
}
