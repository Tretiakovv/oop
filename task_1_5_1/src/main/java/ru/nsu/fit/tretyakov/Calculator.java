package ru.nsu.fit.tretyakov;

import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class Calculator {
    private final Deque<Double> calculatorStack;
    private final Set<String> binaryOperators, unaryOperators;
    private String expression;
    private String[] subExprArray;
    private Integer curIndex;

    /**
     * Empty constructor of the class.
     */
    public Calculator() {

        this.calculatorStack = new ArrayDeque<>();
        this.expression = null;
        this.subExprArray = null;
        this.curIndex = 0;
        this.unaryOperators = initAryOperators(new String[]{"sqrt", "rad", "deg", "sin", "cos"});
        this.binaryOperators = initAryOperators(new String[]{"+", "-", "*", "/", "pow", "log"});
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

    /**
     * This function handles function value by its measure unit
     *
     * @param operand is the current operand of the expression
     * @return calculated function value
     * @throws IllegalStateException if passed operand isn't correct
     */
    private Double parseStringValue(String operand)
            throws IllegalStateException {

        if (operand.contains("pi")) {
            var radiansNumber = operand.split("/");
            if (radiansNumber.length > 2) {
                throw new IllegalStateException("Radian value is incorrect");
            }
            return Math.PI / Double.parseDouble(radiansNumber[1]);
        } else return Double.parseDouble(operand);
    }

    private Set<String> initAryOperators(String[] operators) {
        return Arrays.stream(operators)
                .collect(Collectors.toSet());
    }

    /**
     * Main method to parse and calculate string expression.
     *
     * @param expression is the passed expression which will be calculated
     * @return the float value of calculated expression
     * @throws IllegalStateException if result of the expression
     *                               contains more than one value.
     */

    public Double calculate(String expression) throws IllegalStateException {

        if (subExprArray == null) subExprArray = expression.split(" ");
        if (curIndex == 0) curIndex = subExprArray.length;

        while (curIndex > 0) {
            String current = peek();

            if (binaryOperators.contains(current) || unaryOperators.contains(current)) {

                var fst = Objects.requireNonNull(calculatorStack.pollLast());

                if (binaryOperators.contains(current)) {
                    var snd = Objects.requireNonNull(calculatorStack.pollLast());
                    switch (current) {
                        case "+" -> calculatorStack.addLast(fst + snd);
                        case "-" -> calculatorStack.addLast(fst - snd);
                        case "*" -> calculatorStack.addLast(fst * snd);
                        case "/" -> calculatorStack.addLast(fst / snd);
                        case "log" -> calculatorStack.addLast(Math.log(fst) / Math.log(snd));
                        case "pow" -> calculatorStack.addLast(Math.pow(fst, snd));
                    }
                } else {
                    switch (current) {
                        case "sqrt" -> calculatorStack.addLast(Math.sqrt(fst));
                        case "sin" -> calculatorStack.addLast(Math.sin(fst));
                        case "cos" -> calculatorStack.addLast(Math.cos(fst));
                        case "deg" -> calculatorStack.addLast(Math.toRadians(fst));
                        case "rad" -> calculatorStack.addLast(fst);
                    }
                }
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
    public Double calculate() throws IllegalStateException {
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
