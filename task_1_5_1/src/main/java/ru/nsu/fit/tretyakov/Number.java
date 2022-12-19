package ru.nsu.fit.tretyakov;

/**
 * This class is the boxing of the complex number.
 * It contains imagine and real parts of the complex number
 * and allows user to make some real and operations above this number.
 */
public class Number {
    private final double imag;
    private final double real;

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
     * to calculate complex cosine of the number)
     *
     * @param theta is the current angle between point on the complex striation
     *              and the OX axis.
     * @return the hyperbolic cosine of the number
     */
    double cosh(double theta) {
        return (Math.exp(theta) + Math.exp(-theta)) / 2;
    }

    /**
     * This function calculates hyperbolic sinus (function that helps
     * to calculate complex sinus of the number)
     *
     * @param theta is the current angle between point on the complex striation
     *              and the OX axis.
     * @return the hyperbolic sinus of the number
     */
    double sinh(double theta) {
        return (Math.exp(theta) - Math.exp(-theta)) / 2;
    }
}
