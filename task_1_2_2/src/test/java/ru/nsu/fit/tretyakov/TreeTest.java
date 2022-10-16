package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.tretyakov.Tree;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TreeTest {

    // size test

    @Test
    public void getSizeTest(){
        Tree<String> simpleTree = new Tree<>(1,"aaa");
        simpleTree.add("bbb");
        simpleTree.add("ccc");
        assertEquals(simpleTree.size(),3);
    }

    // adding tests

    @Test
    public void addSingleTest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        Integer[] tstArray = {1,2,3};
        integerTree.add(2);
        integerTree.add(3);
        assertArrayEquals(integerTree.toArray(),tstArray);
    }

    @Test
    public void addAllTest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        Integer[] tstArray = {1,2,3};
        integerTree.addAll(List.of(new Integer[]{2,3}));
        assertArrayEquals(integerTree.toArray(),tstArray);
    }

    // removing tests

    @Test
    public void removeRootSingleTest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        integerTree.remove(1);
        assertFalse(integerTree.iterator().hasNext());
    }

    @Test
    public void removeNodeSingleWithChangingRootTest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        integerTree.addAll(List.of(new Integer[]{2,3,4,5}));
        integerTree.remove(2);
        integerTree.remove(3);
        integerTree.remove(4);
        integerTree.remove(1);
        assertEquals(integerTree.toArray()[0],5);
    }

    @Test
    public void removeAllTest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        integerTree.addAll(List.of(new Integer[]{2,3,4,5}));
        integerTree.removeAll(List.of(new Integer[]{2,3,4,5}));
        assertEquals(integerTree.toArray()[0],1);
    }

    @Test
    public void removeElemNotInTreeTest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        assertFalse(integerTree.remove(5));
    }

    @Test
    public void removeNullTest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        assertThrows(NullPointerException.class,()-> {
            integerTree.remove(null);
        });
    }

    // contains tests

    @Test
    public void containsTest(){
        Tree<String> simpleTree = new Tree<>(1,"aaa");
        assertTrue(simpleTree.contains("aaa"));
    }

    @Test
    public void notContainsTest(){
        Tree<String> simpleTree = new Tree<>(1,"aaa");
        assertFalse(simpleTree.contains("bbb"));
    }

    @Test
    public void containsNullTest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        assertThrows(NullPointerException.class,()-> {
            integerTree.contains(null);
        });
    }

    // retain tests

    @Test
    public void retainAllSimpleTest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        integerTree.addAll(Stream.of(2,3,4,5,6).toList());
        integerTree.retainAll(Stream.of(2,3).toList());
        assertArrayEquals(integerTree.toArray(),Stream.of(2,3).toArray());
    }

    @Test
    public void retainAllNullTest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        integerTree.addAll(Stream.of(2,3,4,5,6).toList());
        assertThrows(NullPointerException.class,()-> {
            integerTree.retainAll(Stream.of(2,null).toList());
        });
    }

    // iterator tests

    @Test
    public void iteratorHasNextTrueTest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        assertTrue(integerTree.iterator().hasNext());
    }

    @Test
    public void iteratorHasNextFalseTest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        integerTree.remove(1);
        assertFalse(integerTree.iterator().hasNext());
    }

    @Test
    public void iteratorIterateCMETest(){
        Tree<Integer> integerTree = new Tree<>(1,1);
        integerTree.addAll(Stream.of(2,3,4,5).toList());

        Iterator<Integer> itr = integerTree.iterator();
        while(itr.hasNext()){
            itr.next();
        }

        assertThrows(ConcurrentModificationException.class, itr::next);
    }

    // clear tests

    @Test
    public void clearTest(){
        Tree<Integer> simpleTree = new Tree<>(1,1);
        simpleTree.addAll(Stream.of(2,3,4,5).toList());
        simpleTree.clear();
        assertThat(simpleTree.toArray()).isEmpty();
        assertTrue(simpleTree.isEmpty());
    }

    // to array test

    @Test
    public void toArray(){
        Tree<Integer> simpleTree = new Tree<>(1,1);
        simpleTree.addAll(Stream.of(2,3,4,5).toList());
        Object[] finalArray = simpleTree.toArray();
        assertArrayEquals(finalArray,new Object[]{1,2,4,3,5});
    }

    @Test
    public void toArrayGeneric(){
        Tree<Integer> simpleTree = new Tree<>(1,1);
        simpleTree.addAll(Stream.of(2,3,4,5).toList());
        Integer[] finalArray = simpleTree.to
        assertArrayEquals(finalArray,new Object[]{1,2,4,3,5});
    }


}
