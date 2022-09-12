import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.nsu.fit.tretyakov.HeapSort;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class HeapSortTest {

    @ParameterizedTest
    @MethodSource("testWithMethodSource")
    public void sampleTest(int[] testArray){
        int[] testSampleArray = Arrays.stream(testArray).sorted().toArray();
        HeapSort sampleHeapSort = new HeapSort(testArray);
        sampleHeapSort.heapSort();
        assertArrayEquals(testArray,testSampleArray);
    }

    static Stream<int[]> testWithMethodSource() {
        return Stream.of(new int[]{1,22,43,100,-12,31321,41,42,0,0,0},
                         new int[]{10,9,8,7,6,5,4,3,2,1},
                         new int[]{1,2,3,4,5,6,7,8,9,10},
                         new int[]{1,1,1,1,1,1,1,1},
                         new int[]{-1,-2,-3,-4,-5,-6,-7,-8,-9,-10},
                         new int[]{-10,-9,-8,-7,-6,-5,-4,-3,-2,-1});
    }
}
