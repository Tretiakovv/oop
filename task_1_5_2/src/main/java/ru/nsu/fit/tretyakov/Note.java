package ru.nsu.fit.tretyakov;

import java.io.Serializable;
import java.util.*;

/**
 * This is the class of the note, which contains note header,
 * date of the creation of the note and note's description.
 */
public class Note implements Serializable {
    public final Date date;
    private String header;
    private String body;

    /**
     * Overload of the default constructor of the Note with
     * additional body parameter.
     *
     * @param header is the required header of the note
     * @param body   is the required body of the note
     */
    public Note(String header, String body) {
        this(header);
        this.body = body;
    }

    /**
     * Default constructor of the Note with the
     * header parameter.
     *
     * @param header is the required header of the note
     */
    public Note(String header) {
        this.header = header;
        this.date = new Date();
    }

    /**
     * Getter of the header of the note
     *
     * @return header of the note
     */
    public String getHeader() {
        return this.header;
    }

    /**
     * Setter of the header of the note
     *
     * @param header is the required header of the note
     */
    public void setHeader(String header) {
        this.header = header;
    }

    /**
     * Setter of the body of the note
     *
     * @param body is the required body of the note
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     * This method shows all note information.
     */
    public void showNote() {

        System.out.printf("Date the note was created: %s\n\n", date);
        System.out.printf("==== %s ====\n\n", this.header);

        if (body != null) {
            System.out.println(this.body);
        } else System.out.println("This note is empty");
    }
}
