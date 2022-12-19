package ru.nsu.fit.tretyakov;

import java.util.Deque;

/**
 * This class used to test adding new arity operators to the
 * operators factory.
 */
public abstract class TernaryOperators extends ArityOperators {

    /**
     * This class calculates adding value of three operands.
     */
    public static class TernaryPlus extends TernaryOperators implements Operator {
        /**
         * This function calculates result of adding of three operands.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return result of adding three numbers
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(3, expressionStack);
            return new Number(operands[0].real() + operands[1].real()
                    + operands[2].real(), 0);
        }
    }

    /**
     * This class calculates subtraction value of three operands.
     */
    public static class TernaryMinus extends TernaryOperators implements Operator {
        /**
         * This function calculates result of subtracting three numbers.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return result of subtracting three numbers.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(3, expressionStack);
            return new Number(operands[0].real() - operands[1].real()
                    - operands[2].real(), 0);
        }
    }

    /**
     * This class calculates multiply value of three operands.
     */
    public static class TernaryMultiply extends TernaryOperators implements Operator {
        /**
         * This function calculates result of dividing three numbers.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return result of dividing three numbers.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(3, expressionStack);
            return new Number(operands[0].real() * operands[1].real()
                    * operands[2].real(), 0);
        }
    }
}
