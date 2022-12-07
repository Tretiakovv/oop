package ru.nsu.fit.tretyakov;

import java.util.*;

public class Calculator {
    private final Deque<Number> calculatorStack;
    private final OperatorFactory operatorFactory;
    private String expression;
    private String[] subExprArray;
    private Integer curIndex;

    /**
     * Empty constructor of the class.
     */
    public Calculator() {
        this.operatorFactory = new OperatorFactory();
        this.calculatorStack = new ArrayDeque<>();
        this.expression = null;
        this.subExprArray = null;
        this.curIndex = 0;
    }

    /**
     * Overload of constructor of the class with string parameter.
     *
     * @param expression is the required expression to be calculated
     */
    public Calculator(String expression) {
        this();
        this.expression = expression;
        this.subExprArray = expression.split(" ");
        this.curIndex = subExprArray.length;
    }

    public void addOperatorsMap(Map<String, Operator> operatorMap) {
        operatorFactory.addOperatorsMap(operatorMap);
    }

    /**
     * This function handles function value by its measure unit
     *
     * @param operand is the current operand of the expression
     * @return calculated function value
     * @throws IllegalStateException if passed operand isn't correct
     */

    private Number parseStringValue(String operand)
            throws IllegalStateException {
        if (operand.contains("pi")) {
            var radiansNumber = operand.split("/");
            if (radiansNumber.length > 2) {
                throw new IllegalStateException("Radian value is incorrect");
            }
            return new Number(Math.PI / Double.parseDouble(radiansNumber[1]), 0);
        } else if (operand.contains("i")) {
            return parseComplexValue(operand);
        }
        return new Number(Double.parseDouble(operand), 0);
    }

    private Number parseComplexValue(String operand) throws IllegalStateException {

        String[] complexValue = operand.split("\\+");
        Number complexNumber;

        // if our boy has only imagine part of number
        if (complexValue.length == 1) {

            String imagine = complexValue[0];
            // if imagine part is only 'i'
            if (imagine.length() == 1) complexNumber = new Number(0, 1);

            var imagineValue = imagine.replace("i", "\0");
            complexNumber = new Number(0, Double.parseDouble(imagineValue));

        } else if (complexValue.length == 2) {

            if (complexValue[0].contains("i")) {
                throw new IllegalStateException("Real part of complex number contains i");
            }

            double real = Double.parseDouble(complexValue[0]);
            double imagine;

            if (complexValue[1].length() == 1) imagine = 1;
            else imagine = Double.parseDouble(
                    complexValue[1].replace("i", "\0"));

            complexNumber = new Number(real, imagine);
        } else {
            throw new IllegalStateException("Input complex number is incorrect");
        }
        return complexNumber;
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

        if (subExprArray == null) subExprArray = expression.split(" ");
        if (curIndex == 0) curIndex = subExprArray.length;

        while (curIndex > 0) {
            String current = peek();

            Operator currentOperator = operatorFactory.runOperator(current);

            if (currentOperator != null) {
                calculatorStack.addLast(currentOperator.calculate(calculatorStack));
            } else {
                var operand = parseStringValue(current);
                calculatorStack.addLast(operand);
            }
            curIndex--;
        }

        if (calculatorStack.size() != 1) {
            throw new IllegalStateException("Input string is incorrect. Result is undetermined");
        }

        subExprArray = null;
        return calculatorStack.poll();
    }

    /**
     * Overload of main method with no parameters.
     *
     * @return the float value of calculated expression
     * @throws IllegalStateException if result of the expression
     *                               contains more than one value.
     */
    public Number calculate() throws IllegalStateException {
        if (expression == null) {
            throw new IllegalStateException("Expression is null");
        }
        return calculate(expression);
    }

    /**
     * This method returns a subexpression at the next index WITHOUT shifting to the next index
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
