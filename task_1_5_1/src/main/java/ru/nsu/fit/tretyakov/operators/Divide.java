package ru.nsu.fit.tretyakov.operators;

import java.util.Deque;
import java.util.List;

import ru.nsu.fit.tretyakov.Operator;

/**
 * This class computes the result of dividing one
 * complex number by second.
 */
public class Divide implements Operator {

    public static final String token = "/";

    @Override
    public String getToken() {
        return token;
    }

    /**
     * This function calculates the result of dividing one complex number
     * by second.
     *
     * @return the result of dividing one complex number by second.
     */
    @Override
    public Number calculate() {
        List<Number> complexOperands = getValuesFromStack(2);
        Number fst = complexOperands.get(0);
        Number snd = complexOperands.get(1);
        double den = Math.pow(snd.mod(), 2);

        return new Number((fst.real() * snd.real() + fst.imag() * snd.imag()) / den,
                (fst.imag() * snd.real() - fst.real() * snd.imag()) / den);
    }
}
