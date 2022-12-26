package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;
import java.util.List;

import ru.nsu.fit.tretyakov.Operator;

/**
 * This class computes the result of multiplying one complex number by second.
 */
public class Multiply implements Operator {

    public static final String token = "*";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates result of multiplying one complex number by second.
     *
     * @return result of multiplying one complex number by second.
     */
    @Override
    public Number calculate() {
        List<Number> complexOperands = getValuesFromStack(2);
        Number fst = complexOperands.get(0);
        Number snd = complexOperands.get(1);

        return new Number(fst.real() * snd.real() - fst.imag() * snd.imag(),
                fst.real() * snd.imag() + fst.imag() * snd.real());
    }
}
