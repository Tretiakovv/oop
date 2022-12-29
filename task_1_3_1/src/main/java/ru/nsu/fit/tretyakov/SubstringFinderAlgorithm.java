package ru.nsu.fit.tretyakov;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * This interface allows user to create his own substring finding class.
 * It contains one overloaded method which finds a substring in a string.
 */
public interface SubstringFinderAlgorithm {
    /**
     * Main method that finds a substring in a string.
     *
     * @param stream is the required stream of characters. It can be String, File, Stream etc.
     * @param string is the required substring which is wanted to be found.
     * @return the ArrayList of all founded occurrences of substring.
     * @throws IOException if there's an error with a InputStream
     */
    ArrayList<Integer> findSubstring(InputStream stream, String string) throws IOException;

    /**
     * Overload of the main method without parameters.
     *
     * @return the ArrayList of all founded occurrences of substring.
     * @throws IOException if there's an error with a InputStream
     */
    ArrayList<Integer> findSubstring() throws IOException;

    /**
     * Overload of the main method with a single string parameter.
     *
     * @param string is the required substring which is wanted to be found.
     * @return the ArrayList of all founded occurrences of substring.
     * @throws IOException if there's an error with a InputStream
     */
    ArrayList<Integer> findSubString(String string) throws IOException;
}
