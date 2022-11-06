package ru.nsu.fit.tretyakov;

import java.io.*;

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
    public Integer findSubString(String string) throws IOException, NullPointerException {
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
    public Integer findSubstring() throws IOException, NullPointerException {
        return findSubstring(this.file, this.string);
    }

    /**
     * Main method of finding a substring in required string. It uses a BufferReader,
     * which uses safe-stream methods and reads a file char by char.
     *
     * @param file   is the required file in which this method
     *               will try to find a substring.
     * @param string is the required substring that needs to find.
     * @return index of the beginning of the substring in file if this substring
     * exist. Otherwise, return null.
     * @throws IOException          if BufferReader cannot open the file
     * @throws NullPointerException if the file or a string is null
     */
    public Integer findSubstring(File file, String string) throws IOException, NullPointerException {

        if (file == null) {
            throw new NullPointerException("File is null");
        }
        if (string == null) {
            throw new NullPointerException("String is empty");
        }

        BufferedReader br = new BufferedReader(new FileReader(file));

        char[] charString = string.toCharArray();
        int idx = 0, fileIdx = 0;
        boolean hasStart = false;
        Integer position = null;

        while (br.ready()) {

            int curChar = br.read();

            if (charString[idx++] == curChar) {
                if (!hasStart) {
                    position = fileIdx;
                    hasStart = true;
                }
                if (charString.length == idx) {
                    break;
                }
            } else {
                idx = 0;
                if (charString[idx] == curChar) {
                    position = fileIdx;
                    idx++;
                } else {
                    hasStart = false;
                    position = null;
                }
            }
            fileIdx++;
        }
        br.close();
        return position;
    }
}
