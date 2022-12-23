package ru.nsu.fit.tretyakov;

import java.util.*;

/**
 * Interface of the notebook. Allows user to add,
 * remove, change and show notes.
 */
public interface Notebook {
    /**
     * This method adds specific note to the notebook
     * with passed header and body parameters.
     *
     * @param header is the required header of the new note
     * @param body   is the optional body of the new note
     */
    void addNote(String header, String body);

    /**
     * This method removes first occurrence of the specific note from the notebook by its header.
     *
     * @param header is the required header of the note
     * @return removed note from the notebook
     */
    Note removeNote(String header);

    /**
     * This method shows all notes in the notebook or shows all notes
     * which headers are suiting for the passed keywords
     *
     * @param keywords is a specific collection of keywords
     *                 for which the notebook will be displayed
     */
    void showNotebook(Collection<String> keywords);

    /**
     * This method changing existing note in the notebook by its header.
     * It can also change body of the note.
     *
     * @param oldHeader is the old header of the changing note,
     *                  by which this note will be founded
     * @param newHeader is the new header of the changing note
     * @param body      is the optional body of the new note
     */
    void editNote(String oldHeader, String newHeader, String body);
}
