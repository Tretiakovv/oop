import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;

import ru.nsu.fit.tretyakov.Stack;

public class StackTest {

    // изменить на assertThrows
    @Test
    void testEmptyStack(){
        Stack<Integer> emptyStack= new Stack<Integer>(0);
        assertNull(emptyStack.pop());
    }

    @Test
    void testAddStackIntegerCount(){
        Stack<Integer> stackInteger = new Stack<Integer>(1);

        stackInteger.push(1);
        stackInteger.push(2);
        stackInteger.push(3);

        assertEquals(stackInteger.count(),3); // checking stack len
    }

    @Test
    void testAddStackIntegerArray(){
        Stack<Integer> stackInteger = new Stack<Integer>(1);

        stackInteger.push(1);
        stackInteger.push(2);
        stackInteger.push(3);

        assertArrayEquals(stackInteger.popStack(3),new Integer[]{1,2,3});
    }

    @Test
    void testNormalPushPopInteger(){
        Stack<Integer> stackInteger = new Stack<Integer>(1);

        stackInteger.push(1);
        stackInteger.push(2);
        stackInteger.push(3);
        stackInteger.popStack(2);
        stackInteger.pushStack(new Integer[]{5,6,7});
        stackInteger.pop();

        assertArrayEquals(stackInteger.popStack(3),new Integer[]{1,5,6});
    }

    @Test
    void testCrazyPushPopInteger(){
        Stack<Integer> stackInteger = new Stack<Integer>(1);

        stackInteger.push(1);
        stackInteger.push(2);
        stackInteger.push(3);
        stackInteger.popStack(100);
        stackInteger.pop();
        stackInteger.pushStack(new Integer[]{1,2,3});

        assertArrayEquals(stackInteger.popStack(3),new Integer[]{1,2,3});
    }

    @Test
    void testHardPushMaxInteger(){
        Stack<Integer> stackInteger = new Stack<Integer>(1);
        Integer[] testArray = new Integer[10000];

        for (int i = 0; i < 10000; i++) {
            stackInteger.push(Integer.MAX_VALUE);
            testArray[i] = Integer.MAX_VALUE;
        }

        assertArrayEquals(stackInteger.popStack(10000),testArray);
    }

    @Test
    void testPushString(){
        Stack<String> stackInteger = new Stack<String>(1);
        stackInteger.push("Hello, world!");
        assertEquals(stackInteger.pop(),"Hello, world!");
    }

    @Test
    void testPushArrayString(){
        Stack<String> stackInteger = new Stack<String>(1);
        String[] stringArray = new String[]{"abc","cde","efg"};

        stackInteger.pushStack(stringArray);

        assertArrayEquals(stackInteger.popStack(3),stringArray);
    }

    @Test
    void testPopManyDouble(){
        Stack<Double> stackInteger = new Stack<Double>(1);
        Double[] doubleArray = new Double[]{0.1,0.0004,111111.99999};

        stackInteger.pushStack(doubleArray);

        assertArrayEquals(stackInteger.popStack(3),doubleArray);
    }

}
