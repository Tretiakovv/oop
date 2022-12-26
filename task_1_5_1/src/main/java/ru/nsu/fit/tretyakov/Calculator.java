package ru.nsu.fit.tretyakov;

import ru.nsu.fit.tretyakov.operators.Number;

import java.util.*;

/**
 * Main class of the calculator. Parses the user's
 * expression and calculates it. This calculator can
 * parse complex expressions and real expressions.
 */
public class Calculator {
    protected static Deque<Number> calculatorStack;
    private final OperatorFactory operatorFactory;
    private String[] subExprArray;
    private Integer curIndex;

    /**
     * Empty constructor of the class.
     */
    public Calculator() {
        this.operatorFactory = new OperatorFactory();
        calculatorStack = new ArrayDeque<>();
        this.subExprArray = null;
        this.curIndex = 0;
    }

    /**
     * This method adds user's custom operators to the default operators list.
     *
     * @param operators is the operators of the variable length which
     *                  will be added to the default operators list
     */
    public void addOperatorsList(Operator... operators) {
        List<Operator> userOperators = Arrays.stream(operators).toList();
        operatorFactory.addOperatorsList(userOperators);
    }

    /**
     * Main method to parse and calculate string expression.
     *
     * @param expression is the passed expression which will be calculated
     * @return the float value of calculated expression
     * @throws IllegalStateException if result of the expression
     *                               contains more than one value.
     */

    public Number calculate(String expression) throws IllegalStateException {

        if (subExprArray == null) {
            subExprArray = expression.split(" ");
        }
        if (curIndex == 0) {
            curIndex = subExprArray.length;
        }

        while (curIndex > 0) {
            String current = peek();
            Operator currentOperator = operatorFactory.runOperator(current);
            calculatorStack.addLast(currentOperator.calculate());
            curIndex--;
        }

        if (calculatorStack.size() != 1) {
            throw new IllegalStateException("Input string is incorrect. Result is undetermined");
        }

        subExprArray = null;
        return calculatorStack.poll();
    }

    /**
     * This method returns a subexpression at the next index WITHOUT shifting to the next index.
     *
     * @return n-th subexpression of passed expression
     * @throws ArrayIndexOutOfBoundsException if current index is out of bounds
     */
    private String peek() throws ArrayIndexOutOfBoundsException {
        if (curIndex - 1 < 0) {
            throw new ArrayIndexOutOfBoundsException(
                    "Current index is out of bounds"
            );
        }
        return subExprArray[curIndex - 1];
    }
}
