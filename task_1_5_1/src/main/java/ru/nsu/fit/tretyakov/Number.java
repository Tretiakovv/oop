package ru.nsu.fit.tretyakov;

public class Number{
    private final double imag;
    private final double real;

    public Number(double u, double v) {
        real = u;
        imag = v;
    }

    public double real() {
        return this.real;
    }

    public double imag() {
        return this.imag;
    }

    public double arg() {
        return Math.atan2(imag, real);
    }

    public double mod() {
        if (real != 0 || imag != 0) {
            return Math.sqrt(real * real + imag * imag);
        } else {
            return 0d;
        }
    }

    double cosh(double theta) {
        return (Math.exp(theta) + Math.exp(-theta)) / 2;
    }

    double sinh(double theta) {
        return (Math.exp(theta) - Math.exp(-theta)) / 2;
    }
}
