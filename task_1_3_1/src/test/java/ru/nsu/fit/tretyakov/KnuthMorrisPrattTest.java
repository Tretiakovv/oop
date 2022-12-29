package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.util.ArrayList;

public class KnuthMorrisPrattTest {

    InputStream file;

    @BeforeEach
    public void initFile() throws FileNotFoundException {
        this.file = new FileInputStream("./src/test/java/ru/nsu/fit/tretyakov/Substring.txt");
    }

    @Test
    public void emptyConstructorTest() throws IOException {
        SubstringFinderAlgorithm subString = new KnuthMorrisPrattFinder();
        assertEquals(5, subString.findSubstring(file, "aaa").get(0));
    }

    @Test
    public void fileConstructorTest() throws IOException {
        SubstringFinderAlgorithm subString = new KnuthMorrisPrattFinder(file);
        assertEquals(5, subString.findSubString("aaa").get(0));
    }

    @Test
    public void fileStringConstructorTest() throws IOException {
        SubstringFinderAlgorithm subString = new KnuthMorrisPrattFinder(file, "aaa");
        assertEquals(5, subString.findSubstring().get(0));
    }

    @Test
    public void nullFileConstructorTest() throws IOException {
        FileInputStream file = null;
        assertThrows(NullPointerException.class, () -> {
            SubstringFinderAlgorithm subString = new KnuthMorrisPrattFinder(file);
        });
    }

    @Test
    public void nullStringConstructorTest() throws IOException {
        assertThrows(NullPointerException.class, () -> {
            SubstringFinderAlgorithm subString = new KnuthMorrisPrattFinder(file, null);
        });
    }

    @Test
    public void nullFileTest() throws IOException {

        FileInputStream file = null;
        SubstringFinderAlgorithm subString = new KnuthMorrisPrattFinder();

        assertThrows(NullPointerException.class, () -> {
            subString.findSubstring(file, "aaa");
        });
    }

    @Test
    public void nullStringTest() throws IOException {

        SubstringFinderAlgorithm subString = new KnuthMorrisPrattFinder(file);
        assertThrows(NullPointerException.class, () -> {
            subString.findSubstring(file, null);
        });
    }

    @Test
    public void smartSubstringTest() throws IOException {
        SubstringFinderAlgorithm subString = new KnuthMorrisPrattFinder(file, "hello");
        assertEquals(13, subString.findSubstring().get(0));
    }

    @Test
    public void nullSubstringTest() throws IOException {
        SubstringFinderAlgorithm subString = new KnuthMorrisPrattFinder(file, "ja");
        assertNull(subString.findSubstring());
    }

    @Test
    public void stringSubstringTest() throws IOException {
        SubstringFinderAlgorithm subString =
                new KnuthMorrisPrattFinder(new ByteArrayInputStream("hello".getBytes()), "l");
        ArrayList<Integer> arrayList = subString.findSubstring();
        assertEquals(2,arrayList.size());
        assertEquals(2,arrayList.get(0));
        assertEquals(3,arrayList.get(1));
    }
}
