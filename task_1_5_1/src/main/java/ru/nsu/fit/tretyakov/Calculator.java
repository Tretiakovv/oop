package ru.nsu.fit.tretyakov;

import java.util.*;

public class Calculator {
    private final Deque<Float> calculatorStack;
    private final String operators;
    private String expression;
    private Integer curIndex;

    /**
     * Empty constructor of the class.
     */
    public Calculator() {
        this.calculatorStack = new ArrayDeque<>();
        this.expression = null;
        this.curIndex = 0;
        this.operators = "+-*/";
    }

    /**
     * Overload of constructor of the class with string parameter.
     *
     * @param expression is the required expression to be calculated
     */
    public Calculator(String expression) {
        this();
        this.expression = expression;
        this.curIndex = expression.length();
    }

    /**
     * Main method to parse and calculate string expression.
     *
     * @param expression is the passed expression which will be calculated
     * @return the float value of calculated expression
     * @throws IllegalStateException if result of the expression
     *                               contains more than one value.
     */
    public Float calculate(String expression) throws IllegalStateException {

        if (curIndex == 0) curIndex = expression.length();

        StringBuilder curOperator = new StringBuilder();
        StringBuilder curNumber = new StringBuilder();

        while (curIndex > 0){
            Character curChar = peek();

            // parsing Integer value
            while (!operators.contains(curChar.toString()) && !curChar.equals(' ')) {
                curNumber.append(poll());
                curChar = peek();
            }
            if (operators.contains(curChar.toString()) && !curChar.equals(' ')) {

                poll();
                var fst = calculatorStack.poll();
                var snd = calculatorStack.poll();

                switch (curChar) {
                    case '+' -> calculatorStack.addLast(fst + snd);
                    case '-' -> calculatorStack.addLast(fst - snd);
                    case '*' -> calculatorStack.addLast(fst * snd);
                    default -> calculatorStack.addLast(fst / snd);
                }
            } else if (curChar.equals(' ')) {
                poll();
                if (!curNumber.isEmpty()) {
                    calculatorStack.addLast(Float.parseFloat(String.valueOf(curNumber)));
                    curNumber = new StringBuilder("");
                }
            }
        }
        if (calculatorStack.size() != 1) {
            throw new IllegalStateException("Input string is incorrect. Result is undetermined");
        }
        return calculatorStack.poll();
    }

    /**
     * Overload of main method with no parameters.
     *
     * @return the float value of calculated expression
     * @throws IllegalStateException if result of the expression
     *                               contains more than one value.
     */
    public Float calculate() throws IllegalStateException {
        if (expression == null) {
            throw new IllegalStateException("Expression is null");
        }
        return calculate(expression);
    }

    /**
     * This method returns a character at the next index WITHOUT shifting to the next index
     *
     * @return character that will be returned
     */
    private Character peek() {
        return expression.charAt(curIndex - 1);
    }

    /**
     * This method returns a character at the next index with shifting to the next index
     *
     * @return character that will be returned
     */
    private Character poll() {
        curIndex--;
        return expression.charAt(curIndex);
    }
}
