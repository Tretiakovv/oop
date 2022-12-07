package ru.nsu.fit.tretyakov;

import java.util.Deque;

public abstract class BinaryOperators extends ArityOperators {

    public static class Plus extends BinaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(2, expressionStack);
            return new Number(operands[0].real() + operands[1].real(),0);
        }
    }

    public static class Minus extends BinaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(2, expressionStack);
            return new Number(operands[0].real() - operands[1].real(),0);
        }
    }

    public static class Multiply extends BinaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(2, expressionStack);
            return new Number(operands[0].real() * operands[1].real(),0);
        }
    }

    public static class Divide extends BinaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(2, expressionStack);
            return new Number(operands[0].real() / operands[1].real(),0);
        }
    }

    public static class Pow extends BinaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(2, expressionStack);
            return new Number(Math.pow(operands[0].real(), operands[1].real()),0);
        }
    }

    public static class Log extends BinaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(2, expressionStack);
            return new Number(Math.log(operands[0].real())
                    / Math.log(operands[1].real()),0);
        }
    }
}
