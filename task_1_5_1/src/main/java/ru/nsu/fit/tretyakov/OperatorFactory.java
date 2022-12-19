package ru.nsu.fit.tretyakov;

import java.util.HashMap;
import java.util.Map;

/**
 * This is the factory-class which produces specific operator by
 * specific token. It also can take user's factories and add them to
 * the map of all operators.
 */
public class OperatorFactory {
    private final Map<String, Operator> operatorMap;

    /**
     * Constructor of the factory.
     * It creates current map of operators and initialize it.
     */
    public OperatorFactory() {
        operatorMap = new HashMap<>();
        initOperatorMap();
    }

    /**
     * This method adds user's map of operators.
     *
     * @param userMap is the required user's map of operators which
     *                will be added to the current operators map.
     */
    public void addOperatorsMap(Map<String, Operator> userMap) {
        operatorMap.putAll(userMap);
    }

    /**
     * Main method of the class. It runs (returns) specific operator by the
     * string token of the operator.
     *
     * @param operator is the string token of the operator.
     * @return suitable operator in the operators' map.
     */
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
