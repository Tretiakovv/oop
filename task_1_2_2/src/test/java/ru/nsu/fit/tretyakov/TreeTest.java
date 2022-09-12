package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.Test;
import ru.nsu.fit.tretyakov.Tree;
import static org.junit.jupiter.api.Assertions.*;

public class TreeTest {

    @Test
    public void getSizeTest(){
        Tree<String> simpleTree = new Tree<>(1,"aaa");
        simpleTree.add("bbb");
        simpleTree.add("ccc");
        assertEquals(simpleTree.size(),3);
    }

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

}
