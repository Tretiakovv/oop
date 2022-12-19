package ru.nsu.fit.tretyakov;

import java.util.Deque;

/**
 * This abstract class return array of exactly ARITY values of stack.
 * I made this class to not write every time this function when user
 * adds new arity operators.
 */
public abstract class ArityOperators {
    /**
     * @param ARITY           is the arity of the current operator-class.
     * @param expressionStack is the stack of all operands in which
     *                        this function will get an operand.
     * @return the array of the operands in the expression stack.
     */
    Number[] getValuesFromStack(int ARITY, Deque<Number> expressionStack) {
        Number[] operandsArray = new Number[ARITY];
        for (int i = 0; i < ARITY; i++) {
            operandsArray[i] = expressionStack.pollLast();
        }
        return operandsArray;
    }
}
