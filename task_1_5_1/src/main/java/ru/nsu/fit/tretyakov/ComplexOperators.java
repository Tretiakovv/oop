package ru.nsu.fit.tretyakov;

import java.util.Deque;

/**
 * Binary and unary operators of the complex number. They calculate the value of the
 * complex number that computes by allowing some rules to two specific numbers.
 */
public abstract class ComplexOperators extends ArityOperators {

    /**
     * This class computes the result of adding one complex number to second.
     */
    public static class ComplexPlus extends ComplexOperators implements Operator {
        /**
         * This function calculates result of adding one complex number to second.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return result of adding one complex number to second.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];
            Number snd = complexOperands[1];

            return new Number(fst.real() + snd.real(), fst.imag() + snd.imag());
        }
    }

    /**
     * This class computes the result of subtracting one complex number of second.
     */
    public static class ComplexMinus extends ComplexOperators implements Operator {
        /**
         * This function calculates result of subtracting one complex
         * number of second.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return result of subtracting one complex number of second.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];
            Number snd = complexOperands[1];

            return new Number(fst.real() - snd.real(), fst.imag() - snd.imag());
        }
    }

    /**
     * This class computes the result of multiplying one complex number by second.
     */
    public static class ComplexMultiply extends ComplexOperators implements Operator {
        /**
         * This function calculates result of multiplying one complex number by second.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return result of multiplying one complex number by second.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];
            Number snd = complexOperands[1];

            return new Number(fst.real() * snd.real() - fst.imag() * snd.imag(),
                    fst.real() * snd.imag() + fst.imag() * snd.real());
        }
    }

    /**
     * This class computes the result of dividing one complex number by second.
     */
    public static class ComplexDiv extends ComplexOperators implements Operator {
        /**
         * This function calculates the result of dividing one complex number
         * by second.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return the result of dividing one complex number by second.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];
            Number snd = complexOperands[1];
            double den = Math.pow(snd.mod(), 2);

            return new Number((fst.real() * snd.real() + fst.imag() * snd.imag()) / den,
                    (fst.imag() * snd.real() - fst.real() * snd.imag()) / den);
        }
    }

    /**
     * This class calculates the result of taking the logarithm
     * of one complex number to the natural base.
     */
    public static class ComplexLog extends ComplexOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];

            return new Number(Math.log(fst.mod()), fst.arg());
        }
    }

    /**
     * This class takes the square root of the complex number.
     */
    public static class ComplexSqrt extends ComplexOperators implements Operator {
        /**
         * This function calculates square root of the complex number.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return square root of the complex number.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];
            double r = Math.sqrt(fst.mod());
            double theta = fst.arg() / 2;

            return new Number(r * Math.cos(theta), r * Math.sin(theta));
        }
    }

    /**
     * Sinus of the complex value of the number.
     */
    public static class ComplexSin extends ComplexOperators implements Operator {
        /**
         * This function calculates sinus of the complex number.
         *
         * @param expressionStack is the stack of all operands in which
         *                        this function will get an operand.
         * @return sinus of the complex number.
         */
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];
            return new Number(fst.cosh(fst.imag()) * Math.sin(fst.real()),
                    fst.sinh(fst.imag()) * Math.cos(fst.real()));
        }
    }
}
