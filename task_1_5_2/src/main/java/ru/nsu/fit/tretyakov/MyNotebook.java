package ru.nsu.fit.tretyakov;

import com.sun.source.tree.Tree;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import ru.nsu.fit.tretyakov.subcommands.Add;
import ru.nsu.fit.tretyakov.subcommands.Edit;
import ru.nsu.fit.tretyakov.subcommands.Remove;
import ru.nsu.fit.tretyakov.subcommands.Show;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * This allows user to add, edit, delete notes
 * and show all notebook by specific keywords
 */
@Command(name = "notebook",
        mixinStandardHelpOptions = true,
        description = "Notebook can add new notes, edit old notes," +
                " delete notes and show notes by it's headers keywords")
public class MyNotebook {

    /**
     * Static serializer of the notebook.
     */
    private static NotebookSerializer serializer;
    private TreeMap<Date, Note> tempNotebook;

    /**
     * Default constructor with the
     * string filePath parameter.
     */
    public MyNotebook() {
        serializer = new NotebookSerializer();
        tempNotebook = pullData();
    }

    /**
     * Getter of the notebook serializer.
     *
     * @return current notebook serializer
     */
    public static NotebookSerializer getSerializer() {
        return serializer;
    }

    /**
     * This method searches note by its header.
     *
     * @param header is the required header by
     *               which note will be searched
     * @return specific note with this header
     */
    public Note searchNoteByHeader(String header) {
        for (var note : tempNotebook.values()) {
            if (note.getHeader().contains(header)) {
                return note;
            }
        }
        return null;
    }

    /**
     * This method pulls data from the file.
     *
     * @return taken notebook from the file
     */
    public TreeMap<Date, Note> pullData() {
        tempNotebook = serializer.getDataFromFile();
        return tempNotebook != null ? tempNotebook : new TreeMap<>();
    }

    /**
     * This method pushes data to the file.
     *
     * @param notebook is the current notebook which
     *                 will be added to the file
     */
    public void pushData(Map<Date, Note> notebook) {
        serializer.updateFile(notebook);
    }
}
