package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @Test
    public void taskTest() {
        Calculator calculator = new Calculator("sin + - 1 2 1");
        assertEquals(calculator.calculate(), 0);
    }

    @Test
    public void manyNumbersTest() {
        Calculator calculator = new Calculator("+ 78 / - 30 * 0.5 + 28 8 6");
        assertEquals(calculator.calculate(), 80);
    }

    @Test
    public void hardNumbersTest() {
        Calculator calculator = new Calculator("+ sqrt / pow log 2 10 4 2 sin pi/6");
        assertEquals(calculator.calculate(), 0.5640773506212137);
    }

    @Test
    public void degreeRadiansTest() {
        Calculator calculator = new Calculator("sin deg 30");
        assertEquals(calculator.calculate(), 0.49999999999999994);
        assertEquals(calculator.calculate("sin rad 30"), -0.9880316240928618);
    }

}
