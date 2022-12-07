package ru.nsu.fit.tretyakov;

import java.util.Deque;

public abstract class TernaryOperators extends ArityOperators {

    public static class TernaryPlus extends TernaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(3, expressionStack);
            return new Number(operands[0].real() + operands[1].real()
                    + operands[2].real(),0);
        }
    }

    public static class TernaryMinus extends TernaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(3, expressionStack);
            return new Number(operands[0].real() - operands[1].real()
                    - operands[2].real(),0);
        }
    }

    public static class TernaryMultiply extends TernaryOperators implements Operator {
        @Override
        public Number calculate(Deque<Number> expressionStack) {
            Number[] operands = getValuesFromStack(3, expressionStack);
            return new Number(operands[0].real() * operands[1].real()
                    * operands[2].real(),0);
        }
    }
}
