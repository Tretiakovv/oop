package ru.nsu.fit.tretyakov;

import java.io.*;
import java.util.ArrayList;

/**
 * This class can find a substring in required string.
 * It supports some variants of a constructor.
 */
public class KnuthMorrisPrattFinder implements SubstringFinderAlgorithm {

    private InputStream stream;
    private String string;

    private static final int BUFSIZE = 1024;

    /**
     * Empty constructor. Needs if user wants to pass his params into
     * function directly.
     */
    public KnuthMorrisPrattFinder() {
    }

    /**
     * Constructor that gets file from a user.
     *
     * @param stream is the required stream
     * @throws NullPointerException if passing file is null
     */
    public KnuthMorrisPrattFinder(InputStream stream) throws NullPointerException {
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
     * @param stream   is the required file
     * @param string is the required substring that needs to find
     * @throws NullPointerException
     * @throws IOException
     */
    public KnuthMorrisPrattFinder(InputStream stream, String string)
            throws NullPointerException, IOException{
        this(stream);
        if (string == null) {
            throw new NullPointerException("String is null");
        }
        this.string = string;
    }

    /**
     * Overload of main method. Needs if user used constructor with file.
     *
     * @param string is the required substring that need to find.
     * @return beginning index of the substring in file if this substring
     * exist. Otherwise, return null.
     * @throws NullPointerException
     * @throws IOException
     */
    @Override
    public ArrayList<Integer> findSubString(String string) throws NullPointerException, IOException{
        return findSubstring(this.stream, string);
    }

    /**
     * Overload of the main method. Needs if user used a full constructor.
     *
     * @return beginning index of the substring in file if this substring
     * exist. Otherwise, return null.
     * @throws NullPointerException
     * @throws IOException
     */
    @Override
    public ArrayList<Integer> findSubstring() throws NullPointerException, IOException{
        return findSubstring(this.stream, this.string);
    }

    /**
     * Function uses Knuth-Morris-Pratt algorithm to find all begin-indexes of
     * current substring (pattern) in the current file.
     * @param stream is the current file where will be started a search.
     * @param string is the pattern that is needed to find
     * @return an ArrayList of all begin-indexes of substring in the current stirng
     * @throws NullPointerException if one of the file or stirng is null
     */
    @Override
    public ArrayList<Integer> findSubstring(InputStream stream, String string)
            throws NullPointerException, IOException {

        if (stream == null) {
            throw new NullPointerException("File is null");
        }
        if (string == null) {
            throw new NullPointerException("String is empty");
        }

        ArrayList<Integer> indexList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        char[] buffer = new char[BUFSIZE];
        int[] prefixArray = new int[string.length()];
        int i = 0, j = 0;

        prefixFunction(string, prefixArray);

        while (br.ready()) {
            var size = br.read(buffer,0,BUFSIZE);
            indexList.addAll(knuthMorrisPratt(buffer,string, prefixArray,i, j));
        }

        br.close();

        if (indexList.isEmpty()) {
            return null;
        }
        return indexList;
    }

    private ArrayList<Integer> knuthMorrisPratt(char[] string, String pattern, int[] prefixArray, int i, int j) {

        int pl = pattern.length();
        int sl = string.length;
        ArrayList<Integer> result = new ArrayList<>();

        while ((sl - i) >= (pl - j)) {
            if (pattern.charAt(j) == string[i]) {
                i++;
                j++;
            }
            if (j == pl) {
                result.add(i - j);
                j = prefixArray[j - 1];
            } else if (i < sl && pattern.charAt(j) != string[i]) {
                if (j != 0) {
                    j = prefixArray[j - 1];
                } else {
                    i = i + 1;
                }
            }
        } return result;
    }

    private void prefixFunction(String pattern, int[] prefixArray) {

        prefixArray[0] = 0;

        for (int i = 1; i < pattern.length(); i++) {
            int index = prefixArray[i - 1];
            while (index > 0 && pattern.charAt(i) != pattern.charAt(index)) {
                index = prefixArray[index - 1];
            }
            if (pattern.charAt(i) == pattern.charAt(index)) index++;
            prefixArray[i] = index;
        }
    }
}
