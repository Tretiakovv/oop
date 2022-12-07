package ru.nsu.fit.tretyakov;

import java.util.Deque;

public abstract class UnaryOperators extends ArityOperators {

    public static class Sin extends UnaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(1, expressionStack);
            return new Number(Math.sin(operands[0].real()),0);
        }
    }

    public static class Cos extends UnaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(1, expressionStack);
            return new Number(Math.cos(operands[0].real()),0);
        }
    }

    public static class Rad extends UnaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(1, expressionStack);
            return new Number(Math.toRadians(operands[0].real()),0);
        }
    }

    public static class Sqrt extends UnaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(1, expressionStack);
            return new Number(Math.sqrt(operands[0].real()),0);
        }
    }
}
