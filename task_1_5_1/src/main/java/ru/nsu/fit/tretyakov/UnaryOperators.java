package ru.nsu.fit.tretyakov;

import java.util.Deque;

/**
 * Unary operators of the complex number. They transform value of the
 * current complex number by specific rule.
 */
public abstract class UnaryOperators extends ArityOperators {

    /**
     * Sinus of the real value of the number.
     */
    public static class Sin extends UnaryOperators implements Operator {
        /**
         * This function calculates sinus of the real number.
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return sinus of the real number.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(1, expressionStack);
            return new Number(Math.sin(operands[0].real()),0);
        }
    }

    /**
     * Cosine of the real value of the number.
     */
    public static class Cos extends UnaryOperators implements Operator {
        /**
         * This function calculates cosine of the real number.
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return cosine of the real number.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(1, expressionStack);
            return new Number(Math.cos(operands[0].real()),0);
        }
    }

    /**
     * This class converts degree value to the radian value of the angle.
     */
    public static class Rad extends UnaryOperators implements Operator {
        /**
         * This function converts degree value of the angle to the radian value.
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return radian value of the angle.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(1, expressionStack);
            return new Number(Math.toRadians(operands[0].real()),0);
        }
    }

    /**
     * This class takes the square root of the number.
     */
    public static class Sqrt extends UnaryOperators implements Operator {
        /**
         * This function calculates square root of the real number.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return square root of the real number.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(1, expressionStack);
            return new Number(Math.sqrt(operands[0].real()),0);
        }
    }
}
