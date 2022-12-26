package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;
import java.util.List;

import ru.nsu.fit.tretyakov.Operator;

/**
 * This class computes the result of adding one complex number to second.
 */
public class Plus implements Operator {

    public static final String token = "+";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates result of adding one complex number to second.
     *
     * @return result of adding one complex number to second.
     */
    @Override
    public Number calculate() {
        List<Number> complexOperands = getValuesFromStack(2);
        Number fst = complexOperands.get(0);
        Number snd = complexOperands.get(1);

        return new Number(fst.real() + snd.real(), fst.imag() + snd.imag());
    }
}
