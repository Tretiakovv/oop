package ru.nsu.fit.tretyakov;

import java.util.Deque;

public abstract class ComplexOperators extends ArityOperators {

    public static class ComplexPlus extends ComplexOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];
            Number snd = complexOperands[1];

            return new Number(fst.real() + snd.real(), fst.imag() + snd.imag());
        }
    }

    public static class ComplexMinus extends ComplexOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];
            Number snd = complexOperands[1];

            return new Number(fst.real() - snd.real(), fst.imag() - snd.imag());
        }
    }

    public static class ComplexMultiply extends ComplexOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];
            Number snd = complexOperands[1];

            return new Number(fst.real() * snd.real() - fst.imag() * snd.imag(),
                    fst.real() * snd.imag() + fst.imag() * snd.real());
        }
    }

    public static class ComplexDiv extends ComplexOperators implements Operator {
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

    public static class ComplexLog extends ComplexOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];

            return new Number(Math.log(fst.mod()), fst.arg());
        }
    }

    public static class ComplexSqrt extends ComplexOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];
            double r = Math.sqrt(fst.mod());
            double theta = fst.arg() / 2;

            return new Number(r * Math.cos(theta), r * Math.sin(theta));
        }
    }

    public static class ComplexSin extends ComplexOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {

            Number[] complexOperands = getValuesFromStack(2, expressionStack);
            Number fst = complexOperands[0];
            return new Number(fst.cosh(fst.imag()) * Math.sin(fst.real()),
                    fst.sinh(fst.imag()) * Math.cos(fst.real()));
        }
    }
}
