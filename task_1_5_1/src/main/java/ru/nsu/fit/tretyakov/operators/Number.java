package ru.nsu.fit.tretyakov.operators;

import ru.nsu.fit.tretyakov.Operator;

import java.util.Deque;

/**
 * This class is the boxing of the complex number.
 * It contains imagine and real parts of the complex number
 * and allows user to make some real and operations above this number.
 */
public class Number implements Operator {
    public String token;
    private double imag;
    private double real;

    /**
     * Constructor of the number. Requires real and complex
     * parameters of the number.
     *
     * @param u is the value of real part of the number
     * @param v is the value of imagine part of the number
     */
    public Number(double u, double v) {
        real = u;
        imag = v;
    }

    /**
     * Constructor of the number with the string representation of it.
     *
     * @param token is the string representation
     *              of the complex number
     */
    public Number(String token) {
        this();
        this.token = token;
    }

    /**
     * Default constructor of the number.
     */
    public Number() {
        this.real = this.imag = 0;
    }

    /**
     * This function handles function value by its measure unit.
     *
     * @param operand is the current operand of the expression
     * @throws IllegalStateException if passed operand isn't correct
     */
    public Number parseNumber(String operand) throws IllegalStateException {
        if (operand.contains("pi")) {
            var radiansNumber = operand.split("/");
            if (radiansNumber.length > 2) {
                throw new IllegalStateException("Radian value is incorrect");
            }
            this.real = Math.PI / Double.parseDouble(radiansNumber[1]);
            return this;
        } else if (operand.contains("i")) {
            return parseComplexValue(operand);
        }
        this.real = Double.parseDouble(operand);
        return this;
    }

    private Number parseComplexValue(String operand) throws IllegalStateException {

        String[] complexValue = operand.split("\\+");

        // if our boy has only imagine part of number
        if (complexValue.length == 1) {

            String imagine = complexValue[0];
            // if imagine part is only 'i'
            if (imagine.length() == 1) {
                this.imag = 1;
                return this;
            }

            var imagineValue = imagine.replace("i", "\0");
            this.imag = Double.parseDouble(imagineValue);

        } else if (complexValue.length == 2) {

            if (complexValue[0].contains("i")) {
                throw new IllegalStateException("Real part of complex number contains i");
            }

            double real = Double.parseDouble(complexValue[0]);
            double imagine;

            if (complexValue[1].length() == 1) {
                imagine = 1;
            } else {
                imagine = Double.parseDouble(
                        complexValue[1].replace("i", "\0"));
            }

            this.real = real;
            this.imag = imagine;
        } else {
            throw new IllegalStateException("Input complex number is incorrect");
        }
        return this;
    }

    /**
     * Getter of the real part of the number.
     *
     * @return real part of the number
     */
    public double real() {
        return this.real;
    }

    /**
     * Getter of the imagine part of the number.
     *
     * @return imagine part of the number
     */
    public double imag() {
        return this.imag;
    }

    /**
     * This function calculates argument of the
     * complex number.
     *
     * @return the argument of the complex number
     */
    public double arg() {
        return Math.atan2(imag, real);
    }

    /**
     * This function calculates the modulus of the complex number.
     *
     * @return the modulus of the complex number
     */
    public double mod() {
        if (real != 0 || imag != 0) {
            return Math.sqrt(real * real + imag * imag);
        } else {
            return 0d;
        }
    }

    /**
     * This function calculates hyperbolic cosine (function that helps
     * to calculate complex cosine of the number).
     *
     * @param theta is the current angle between point on the complex striation
     *              and the OX axis.
     * @return the hyperbolic cosine of the number
     */
    public double cosh(double theta) {
        return (Math.exp(theta) + Math.exp(-theta)) / 2;
    }

    /**
     * This function calculates hyperbolic sinus (function that helps
     * to calculate complex sinus of the number).
     *
     * @param theta is the current angle between point on the complex striation
     *              and the OX axis.
     * @return the hyperbolic sinus of the number
     */
    public double sinh(double theta) {
        return (Math.exp(theta) - Math.exp(-theta)) / 2;
    }

    @Override
    public String getToken() {
        return this.token;
    }

    @Override
    public Number calculate(Deque<Number> expressionStack) {
        parseNumber(this.token);
        return this;
    }
}
