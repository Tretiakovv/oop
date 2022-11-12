package ru.nsu.fit.tretyakov;

import java.io.*;
import java.util.ArrayList;

/**
 * This class can find a substring in required string.
 * It supports some variants of a constructor.
 */
public class SubString {

    private File file;
    private String string;

    /**
     * Empty constructor. Needs if user wants to pass his params into
     * function directly.
     */
    public SubString() {
    }

    /**
     * Constructor that gets file from a user.
     *
     * @param file is the required file
     * @throws NullPointerException if passing file is null
     */
    public SubString(File file) throws NullPointerException {
        if (file == null) {
            throw new NullPointerException("File is null");
        }
        this.file = file;
        this.string = null;
    }

    /**
     * Constructor with file and string as a parameters. Used if user
     * don't want to pass any parameter to a function.
     *
     * @param file   is the required file
     * @param string is the required substring that needs to find
     * @throws NullPointerException
     */
    public SubString(File file, String string) throws NullPointerException {
        this(file);
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
     * @throws IOException
     * @throws NullPointerException
     */
    public ArrayList<Integer> findSubString(String string) throws IOException, NullPointerException {
        return findSubstring(this.file, string);
    }

    /**
     * Overload of the main method. Needs if user used a full constructor.
     *
     * @return beginning index of the substring in file if this substring
     * exist. Otherwise, return null.
     * @throws IOException
     * @throws NullPointerException
     */
    public ArrayList<Integer> findSubstring() throws IOException, NullPointerException {
        return findSubstring(this.file, this.string);
    }

    /**
     * Function uses Knuth-Morris-Pratt algorithm to find all begin-indexes of
     * current substring (pattern) in the current file.
     * @param file is the current file where will be started a search.
     * @param string is the pattern that is needed to find
     * @return an ArrayList of all begin-indexes of substring in the current stirng
     * @throws IOException if a BufferReader could not read a file
     * @throws NullPointerException if one of the file or stirng is null
     */
    public ArrayList<Integer> findSubstring(File file, String string)
            throws IOException, NullPointerException {

        if (file == null) {
            throw new NullPointerException("File is null");
        }
        if (string == null) {
            throw new NullPointerException("String is empty");
        }

        ArrayList<Integer> indexList = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));

        while (br.ready()) {
            var nextString = br.readLine();
            indexList.addAll(knuthMorrisPratt(nextString,string));
        }

        br.close();

        if (indexList.isEmpty()) {
            return null;
        }
        return indexList;
    }

    private ArrayList<Integer> knuthMorrisPratt(String string, String pattern) {

        int pl = pattern.length();
        int sl = string.length();
        int[] prefixArray = new int[pattern.length()];
        ArrayList<Integer> result = new ArrayList<>();

        prefixFunction(pattern, prefixArray);

        int i = 0, j = 0;
        while ((sl - i) >= (pl - j)) {
            if (pattern.charAt(j) == string.charAt(i)) {
                i++;
                j++;
            }
            if (j == pl) {
                result.add(i - j);
                j = prefixArray[j - 1];
            } else if (i < sl && pattern.charAt(j) != string.charAt(i)) {
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
