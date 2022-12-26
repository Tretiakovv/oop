package ru.nsu.fit.tretyakov;

import ru.nsu.fit.tretyakov.operators.Number;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

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
     * @return transformed value of the number.
     */
    Number calculate();

    /**
     * This method gets operands from the stack arity times.
     *
     * @param arity is the number of taken operands from the stack
     * @return list of taken operands from the stack
     */
    default List<Number> getValuesFromStack(int arity) {
        List<Number> operators = new ArrayList<>();
        for (int i = 0; i < arity; i++) {
            operators.add(Calculator.calculatorStack.pollLast());
        }
        return operators;
    }
}
