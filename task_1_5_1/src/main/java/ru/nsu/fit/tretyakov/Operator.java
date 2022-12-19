package ru.nsu.fit.tretyakov;

import java.util.Deque;

/**
 * This interface contains only one method that allows Calculator
 * calculate value of the operand by its operator.
 */
public interface Operator {
     /**
      * This function calculates value of the number.
      * @param expressionStack is the stack of all operands in which
      *                        this function will get an operand.
      * @return transformed value of the number.
      */
     Number calculate(Deque<Number> expressionStack);
}
