import org.junit.jupiter.api.Test;
import ru.nsu.fit.tretyakov.Stack;

import java.util.Collection;
import java.util.Objects;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class StackTest {

    @Test
    void testEmptyStack() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Stack<Integer> emptyStack = new Stack<>(0);
        });
    }

    @Test
    void testPopEmptyStack() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Stack<Integer> emptyStack = new Stack<>(1);
            emptyStack.pop();
        });
    }

    @Test
    void testPopSeveralTimes() {
        assertThrows(ArrayIndexOutOfBoundsException.class, () -> {
            Stack<Integer> emptyStack = new Stack<>(2);
            emptyStack.popStack(3);
        });
    }

    @Test
    void testAddStackIntegerCount() {
        Stack<Integer> stackInteger = new Stack<Integer>(1);

        stackInteger.push(1);
        stackInteger.push(2);
        stackInteger.push(3);

        assertEquals(stackInteger.count(), 3); // checking stack len
    }

    @Test
    void testAddStackIntegerArray() {
        Stack<Integer> stackInteger = new Stack<Integer>(1);

        stackInteger.push(1);
        stackInteger.push(2);
        stackInteger.push(3);

        assertArrayEquals(stackInteger.popStack(3).getStack(), new Integer[]{1, 2, 3});
    }

    @Test
    void testNormalPushPopInteger() {
        Stack<Integer> stackInteger = new Stack<Integer>(1);

        stackInteger.push(1);
        stackInteger.push(2);
        stackInteger.push(3);
        stackInteger.popStack(2);
        stackInteger.pushStack(new Stack<>(new Integer[]{5, 6, 7}));
        stackInteger.pop();

        assertArrayEquals(stackInteger.popStack(3).getStack(), new Integer[]{1, 5, 6});
    }

    @Test
    void testHardPushMaxInteger() {
        Integer[] tst = Stream.generate(new Random()::nextInt).limit(10000).toArray(Integer[]::new);
        Stack<Integer> stackInteger = new Stack<>(tst);
        assertArrayEquals(stackInteger.popStack(10000).getStack(), tst);
    }

    @Test
    void testPushString() {
        Stack<String> stackInteger = new Stack<String>(1);
        stackInteger.push("Hello, world!");
        assertEquals(stackInteger.pop(), "Hello, world!");
    }

    @Test
    void testPushArrayString() {
        Stack<String> stackString = new Stack<String>(3);
        stackString.pushStack(new Stack<>(new String[]{"abc", "cde", "efg"}));
        assertArrayEquals(stackString.popStack(3).getStack(), new String[]{"abc", "cde", "efg"});
    }

    @Test
    void testPopManyDouble() {
        Stack<Double> stackDouble = new Stack<Double>(3);
        stackDouble.pushStack(new Stack<>(new Double[]{0.1, 0.0004, 111111.99999}));
        assertArrayEquals(stackDouble.getStack(), new Double[]{0.1, 0.0004, 111111.99999});
    }

}
