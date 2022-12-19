package ru.nsu.fit.tretyakov;

import java.util.Deque;

/**
 * Binary operators of the real number. They calculate the value of the
 * number that computes by allowing some rules to two specific numbers.
 */
public abstract class BinaryOperators extends ArityOperators {

    /**
     * This class computes the result of adding one real number to second.
     */
    public static class Plus extends BinaryOperators implements Operator {
        /**
         * This function calculates result of adding one real number to second.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return result of adding one real number to second.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(2, expressionStack);
            return new Number(operands[0].real() + operands[1].real(), 0);
        }
    }

    /**
     * This class computes the result of subtracting one real number of second.
     */
    public static class Minus extends BinaryOperators implements Operator {
        /**
         * This function calculates result of subtracting one real number of second.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return result of subtracting one real number of second.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(2, expressionStack);
            return new Number(operands[0].real() - operands[1].real(), 0);
        }
    }

    /**
     * This class computes the result of multiplying one real number by second.
     */
    public static class Multiply extends BinaryOperators implements Operator {
        /**
         * This function calculates result of multiplying one real number by second.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return result of multiplying one real number by second.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(2, expressionStack);
            return new Number(operands[0].real() * operands[1].real(), 0);
        }
    }

    /**
     * This class computes the result of dividing one real number by second.
     */
    public static class Divide extends BinaryOperators implements Operator {
        /**
         * This function calculates the result of dividing one real number by second.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return the result of dividing one real number by second.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(2, expressionStack);
            return new Number(operands[0].real() / operands[1].real(), 0);
        }
    }

    /**
     * This class calculates the result of raising one number
     * to the power of another.
     */
    public static class Pow extends BinaryOperators implements Operator {
        /**
         * This function calculates the result of raising one number
         * to the power of another.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return the result of raising one number
         * to the power of another.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(2, expressionStack);
            return new Number(Math.pow(operands[0].real(), operands[1].real()), 0);
        }
    }

    /**
     * This class calculates the result of taking the logarithm
     * of one number to the base of another.
     */
    public static class Log extends BinaryOperators implements Operator {
        /**
         * This function calculates the result of taking the logarithm
         * of one number to the base of another.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return the result of taking the logarithm
         * of one number to the base of another.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(2, expressionStack);
            return new Number(Math.log(operands[0].real())
                    / Math.log(operands[1].real()), 0);
        }
    }
}
