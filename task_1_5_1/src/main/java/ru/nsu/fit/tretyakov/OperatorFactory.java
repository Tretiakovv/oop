package ru.nsu.fit.tretyakov;

import java.util.HashMap;
import java.util.Map;

public class OperatorFactory {
    private final Map<String, Operator> operatorMap;

    public OperatorFactory() {
        operatorMap = new HashMap<>();
        initOperatorMap();
    }

    public void addOperatorsMap(Map<String, Operator> userMap) {
        operatorMap.putAll(userMap);
    }

    public Operator runOperator(String operator) {
        return operatorMap.get(operator);
    }

    private void initOperatorMap() {

        // Add binary operators
        operatorMap.put("+", new BinaryOperators.Plus());
        operatorMap.put("-", new BinaryOperators.Minus());
        operatorMap.put("*", new BinaryOperators.Multiply());
        operatorMap.put("/", new BinaryOperators.Divide());
        operatorMap.put("pow", new BinaryOperators.Pow());
        operatorMap.put("log", new BinaryOperators.Log());

        // Add unary operators
        operatorMap.put("sin", new UnaryOperators.Sin());
        operatorMap.put("cos", new UnaryOperators.Cos());
        operatorMap.put("sqrt", new UnaryOperators.Sqrt());
        operatorMap.put("rad", new UnaryOperators.Rad());

        // Add complex operators
        operatorMap.put("i+", new ComplexOperators.ComplexPlus());
        operatorMap.put("i-", new ComplexOperators.ComplexMinus());
        operatorMap.put("i*", new ComplexOperators.ComplexMultiply());
        operatorMap.put("i/", new ComplexOperators.ComplexDiv());
        operatorMap.put("isin", new ComplexOperators.ComplexSin());
        operatorMap.put("ilog", new ComplexOperators.ComplexLog());
        operatorMap.put("isqrt", new ComplexOperators.ComplexSqrt());
    }
}
