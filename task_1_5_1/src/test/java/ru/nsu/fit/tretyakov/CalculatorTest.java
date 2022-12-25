package ru.nsu.fit.tretyakov;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import ru.nsu.fit.tretyakov.testOperators.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {

    @ParameterizedTest
    @CsvSource(value = {
            "sin + - 1 2 1, 0",
            "+ 78 / - 30 * 0.5 + 28 8 6, 80",
            "+ sqrt / pow log 2 10 4 2 sin pi/6, 0.5800013488785706",
            "sin pi/6, 0.49999999999999994",
            "sin rad 30, 0.49999999999999994"
    })
    public void realNumberTest(String expression, double result) {
        Calculator calculator = new Calculator();
        assertEquals(calculator.calculate(expression).real(), result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "+++ 1 2 3, 6",
            "*** 1 2 4, 8",
            "--- 1 2 4, -5"
    })
    public void addNewOperatorsTest(String expression, double result) {

        Calculator calculator = new Calculator();

        List<Operator> operatorList = new ArrayList<>();
        operatorList.add(new TernaryPlus());
        operatorList.add(new TernaryMinus());
        operatorList.add(new TernaryMultiply());
        calculator.addOperatorsList(operatorList);

        assertEquals(calculator.calculate(expression).real(),result);
    }

    @ParameterizedTest
    @CsvSource(value = {
            "+ 2i 2+3i, 2, 5",
            "- 0 2+3i, -2, -3",
            "* 2i 2+3i, -6, 4",
            "* 2 2+3i, 4, 6",
            "/ 2 2+i, 0.7999999999999998, -0.3999999999999999"
    })
    public void complexNumbersTest(String expression, double real, double image){
        Calculator calculator = new Calculator();
        Number complexNumber = calculator.calculate(expression);
        assertEquals(complexNumber.real(),real);
        assertEquals(complexNumber.imag(),image);
    }

}
