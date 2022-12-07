package ru.nsu.fit.tretyakov;

import java.util.Deque;

public interface Operator {
     Number calculate(Deque<Number> expressionStack);
}
