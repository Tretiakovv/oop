package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @Test
    public void simpleTest(){
        Calculator calculator = new Calculator("+ - 1 2 1");
        assertEquals(calculator.calculate(),0);
    }

}
