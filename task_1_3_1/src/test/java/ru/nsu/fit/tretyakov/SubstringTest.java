package ru.nsu.fit.tretyakov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.fit.tretyakov.SubString;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubstringTest {

    File file;

    @BeforeEach
    public void initFile() {
        this.file = new File("./src/test/java/ru/nsu/fit/tretyakov/Substring.txt");
    }

    @Test
    public void emptyConstructorTest() throws IOException {
        SubString subString = new SubString();
        assertEquals(5, subString.findSubstring(file, "aaa").get(0));
    }

    @Test
    public void fileConstructorTest() throws IOException {
        SubString subString = new SubString(file);
        assertEquals(5, subString.findSubString("aaa").get(0));
    }

    @Test
    public void fileStringConstructorTest() throws IOException {
        SubString subString = new SubString(file, "aaa");
        assertEquals(5, subString.findSubstring().get(0));
    }

    @Test
    public void nullFileConstructorTest() throws IOException {
        File file = null;
        assertThrows(NullPointerException.class, () -> {
            SubString subString = new SubString(file);
        });
    }

    @Test
    public void nullStringConstructorTest() throws IOException {
        assertThrows(NullPointerException.class, () -> {
            SubString subString = new SubString(file, null);
        });
    }

    @Test
    public void nullFileTest() throws IOException {
        File file = null;
        SubString subString = new SubString();
        assertThrows(NullPointerException.class, () -> {
            subString.findSubstring(file, "aaa");
        });
    }

    @Test
    public void nullStringTest() throws IOException {
        SubString subString = new SubString(file);
        assertThrows(NullPointerException.class, () -> {
            subString.findSubstring(file, null);
        });
    }

    // fix the test
    @Test
    public void manyTest() throws IOException {
        SubString subString = new SubString(file, "aaa");
        SubString subString1 = new SubString(file, "b");
        System.out.println(subString.findSubstring());
        System.out.println(subString1.findSubstring());
    }

    @Test
    public void smartSubstringTest() throws IOException {
        SubString subString = new SubString(file, "hello");
        assertEquals(13, subString.findSubstring().get(0));
    }

    @Test
    public void nullSubstringTest() throws IOException {
        SubString subString = new SubString(file, "ja");
        assertNull(subString.findSubstring());
    }
}
