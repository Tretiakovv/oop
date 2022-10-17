package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.tretyakov.Tree;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.in;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TreeTest {

    // size test

    @Test
    public void getSizeTest() {
        Tree<String> simpleTree = new Tree<>(1, "aaa");
        simpleTree.add("bbb");
        simpleTree.add("ccc");
        assertEquals(simpleTree.size(), 3);
    }

    @Test
    public void defaultConstructorTest() {
        Tree<Integer> tree = new Tree<>();
        assertTrue(tree.isEmpty());
    }

    // adding tests

    @Test
    public void addSingleTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);
        Integer[] tstArray = {1, 2, 3};
        integerTree.add(2);
        integerTree.add(3);
        assertArrayEquals(integerTree.toArray(), tstArray);
    }

    @Test
    public void addAllTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);
        Integer[] tstArray = {1, 2, 3};
        integerTree.addAll(List.of(new Integer[]{2, 3}));
        assertArrayEquals(integerTree.toArray(), tstArray);
    }

    // removing tests

    @Test
    public void removeRootSingleTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);
        integerTree.remove(1);
        assertThrows(ConcurrentModificationException.class, () -> {
            Tree.TreeIterator<Integer> itr = integerTree.iterator();
        });
    }

    @Test
    public void removeAllTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);
        integerTree.addAll(List.of(new Integer[]{2, 3, 4, 5}));
        integerTree.removeAll(List.of(new Integer[]{2, 3, 4, 5}));
        assertEquals(integerTree.toArray()[0], 1);
    }

    @Test
    public void removeElemNotInTreeTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);
        assertFalse(integerTree.remove(5));
    }

    @Test
    public void removeNullTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);
        assertThrows(NullPointerException.class, () -> {
            integerTree.remove(null);
        });
    }

    // contains tests

    @Test
    public void containsTest() {
        Tree<String> simpleTree = new Tree<>(1, "aaa");
        assertTrue(simpleTree.contains("aaa"));
    }

    @Test
    public void notContainsTest() {
        Tree<String> simpleTree = new Tree<>(1, "aaa");
        assertFalse(simpleTree.contains("bbb"));
    }

    @Test
    public void containsNullTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);
        assertThrows(NullPointerException.class, () -> {
            integerTree.contains(null);
        });
    }

    @Test
    public void containsAllTest() {
        Tree<Integer> simpleTree = new Tree<>(1, 1);

        List<Integer> integerList = Stream.of(2, 3, 4, 5).toList();
        List<Integer> tstList = Stream.of(2, 3, 1).toList();

        simpleTree.addAll(integerList);
        assertTrue(simpleTree.containsAll(tstList));
    }

    @Test
    public void notContainsAllTest() {
        Tree<Integer> simpleTree = new Tree<>(1, 1);

        List<Integer> integerList = Stream.of(2, 3, 4, 5).toList();
        List<Integer> tstList = Stream.of(5, 6).toList();

        simpleTree.addAll(integerList);
        assertFalse(simpleTree.containsAll(tstList));
    }


    // retain tests

    @Test
    public void retainAllSimpleTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);

        List<Integer> integerList = Stream.of(2, 3, 4, 5, 6).toList();
        List<Integer> retainList = Stream.of(1, 2, 3).toList();
        Object[] tstArray = Stream.of(1,2,3).toArray();

        integerTree.addAll(integerList);
        integerTree.retainAll(retainList);

        assertArrayEquals(integerTree.toArray(), tstArray);
    }

    @Test
    public void retainAllNullTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);

        List<Integer> integerList = Stream.of(2, 3, 4, 5, 6).toList();
        List<Integer> tstList = Stream.of(2, null).toList();

        integerTree.addAll(integerList);
        assertThrows(NullPointerException.class, () -> {
            integerTree.retainAll(tstList);
        });
    }

    // iterator tests

    @Test
    public void iteratorHasNextTrueTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);
        assertTrue(integerTree.iterator().hasNext());
    }

    @Test
    public void iteratorRemoveRootTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);
        integerTree.remove(1);
        assertTrue(integerTree.isEmpty());
    }

    @Test
    public void iteratorIterateCMETest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);

        List<Integer> tstList = Stream.of(2, 3, 4, 5).toList();

        integerTree.addAll(tstList);

        Iterator<Integer> itr = integerTree.iterator();
        while (itr.hasNext()) {
            itr.next();
        }

        assertThrows(NullPointerException.class, itr::next);
    }

    @Test
    public void nextNodeEmptyQueueTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);
        Tree.TreeIterator<Integer> itr = integerTree.iterator();
        itr.next();
        assertThrows(NullPointerException.class, itr::next);
    }

    @Test
    public void removeEmptyQueueTest() {
        Tree<Integer> integerTree = new Tree<>(1, 1);
        Tree.TreeIterator<Integer> itr = integerTree.iterator();
        itr.next();
        assertThrows(NullPointerException.class, itr::remove);
    }


    // clear tests

    @Test
    public void clearTest() {
        Tree<Integer> simpleTree = new Tree<>(1, 1);

        List<Integer> integerList = Stream.of(2, 3, 4, 5).toList();

        simpleTree.addAll(integerList);
        simpleTree.clear();
        assertTrue(simpleTree.isEmpty());
    }

    // to array test

    @Test
    public void toArray() {
        Tree<Integer> simpleTree = new Tree<>(1, 1);

        List<Integer> integerList = Stream.of(2, 3, 4, 5).toList();

        simpleTree.addAll(integerList);
        Object[] finalArray = simpleTree.toArray();
        System.out.println(Arrays.toString(finalArray));
        assertArrayEquals(finalArray, new Object[]{1, 2, 3, 4, 5});
    }

    @Test
    public void editOldArrayTest() {
        Integer[] oldArray = {1, 2, 3};
        Tree<Integer> simpleTree = new Tree<>(1, 4);

        List<Integer> integerList = Stream.of(5, 6).toList();

        simpleTree.addAll(integerList);
        Object[] finalArray = simpleTree.toArray(oldArray);

        assertArrayEquals(finalArray, new Object[]{1, 2, 3, 4, 5, 6});
    }
}
