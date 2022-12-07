package ru.nsu.fit.tretyakov;
import java.util.Deque;

public abstract class ArityOperators {
    Number[] getValuesFromStack(int ARITY, Deque<Number> expressionStack) {
        Number[] operandsArray = new Number[ARITY];
        for (int i = 0; i < ARITY; i++) {
            operandsArray[i] = expressionStack.pollLast();
        }
        return operandsArray;
    }
}
