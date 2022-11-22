package ru.nsu.fit.tretyakov;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Collections.fill;
import static java.util.Collections.max;

/**
 * This class uses Z-Function to find all occurrences of substring in a string.
 * Has linear time-complexity.
 */
public class ZFinder implements SubstringFinderAlgorithm {

    /**
     * Size of a buffer which will be used in a BufferedReader
     */
    private static final int BUFSIZE = 1024;
    /**
     * Current stream, which will be used as a character-stream
     */
    private InputStream stream;
    /**
     * Current substring which is wanted to be found
     */
    private String string;

    /**
     * Empty constructor. Needs if user wants to pass his params into
     * function directly.
     */
    public ZFinder() {
    }

    /**
     * Constructor that gets file from a user.
     *
     * @param stream is the required stream
     * @throws NullPointerException if passing file is null
     */
    public ZFinder(InputStream stream) throws NullPointerException {
        if (stream == null) {
            throw new NullPointerException("InputStream is null");
        }
        this.stream = stream;
        this.string = null;
    }

    /**
     * Constructor with file and string as a parameters. Used if user
     * don't want to pass any parameter to a function.
     *
     * @param stream is the required file
     * @param string is the required substring that needs to find
     * @throws NullPointerException
     */
    public ZFinder(InputStream stream, String string)
            throws NullPointerException {
        this(stream);
        if (string == null) {
            throw new NullPointerException("String is null");
        }
        this.string = string;
    }

    /**
     * Main method that finds a substring in a string.
     * @param stream is the required stream of characters. It can be String, File, Stream etc.
     * @param string is the required substring which is wanted to be found.
     * @return the ArrayList of all founded occurrences of substring.
     * @throws IOException if there's an error with a InputStream
     */
    @Override
    public ArrayList<Integer> findSubstring(InputStream stream, String string) throws IOException {
        if (stream == null) {
            throw new NullPointerException("File is null");
        }
        if (string == null) {
            throw new NullPointerException("String is empty");
        }

        ArrayList<Integer> occurList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        char[] buffer = new char[BUFSIZE];
        int i = 0;

        while (br.ready()) {
            var size = br.read(buffer, 0, BUFSIZE);
            ArrayList<Integer> bufList = processText(buffer, string.toCharArray(), i);
            occurList.addAll(bufList);
        }
        if (!occurList.isEmpty()) return occurList;
        return null;
    }

    /**
     * Overload of the main method without parameters.
     * @return the ArrayList of all founded occurrences of substring.
     * @throws IOException if there's an error with a InputStream
     */
    @Override
    public ArrayList<Integer> findSubstring() throws IOException {
        return findSubstring(this.stream, this.string);
    }

    /**
     * Overload of the main method with a single string parameter.
     * @param string is the required substring which is wanted to be found.
     * @return the ArrayList of all founded occurrences of substring.
     * @throws IOException if there's an error with a InputStream
     */
    @Override
    public ArrayList<Integer> findSubString(String string) throws IOException {
        return findSubstring(this.stream, string);
    }

    private void getZarr(char[] str, Integer[] Z) {

        int n = str.length;
        int left = 0, right = 0;

        for (int i = 1; i < n; ++i) {
            if (i > right) {
                left = right = i;
                while (right < n && str[right - left] == str[right]) {
                    right++;
                }
                Z[i] = right - left;
                right--;
            } else {
                int k = i - left;
                if (Z[k] < right - i + 1) Z[i] = Z[k];
                else {
                    left = i;
                    while (right < n && str[right - left] == str[right])
                        right++;
                    Z[i] = right - left;
                    right--;
                }
            }
        }
    }

    private char[] createPSTArray(char[] text, char[] pattern){
        char[] newString = new char[text.length + pattern.length + 1];
        System.arraycopy(pattern,0,newString,0,pattern.length);
        newString[pattern.length] = '$';
        System.arraycopy(text,0,newString,pattern.length+1,text.length);
        return newString;
    }

    private ArrayList<Integer> processText(char[] text, char[] pattern, int i) {

        // making pattern + '$' + text string
        char[] newString = createPSTArray(text,pattern);

        Integer[] zf = new Integer[newString.length];
        getZarr(newString, zf);
        ArrayList<Integer> result = new ArrayList<>();

        while (newString[i] != 0){
            if (zf[i] != null && zf[i] == pattern.length) {
                result.add(i - pattern.length - 1);
            }
            i++;
        }
        return result;
    }
}
