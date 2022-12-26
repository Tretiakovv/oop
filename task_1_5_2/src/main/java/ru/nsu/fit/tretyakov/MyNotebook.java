package ru.nsu.fit.tretyakov;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import ru.nsu.fit.tretyakov.subcommands.Add;
import ru.nsu.fit.tretyakov.subcommands.Edit;
import ru.nsu.fit.tretyakov.subcommands.Remove;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * This class implements Notebook interface, which allows
 * user to add, edit, delete notes and show all notebook
 * by specific keywords
 */
@Command(name = "notebook",
        mixinStandardHelpOptions = true,
        description = "Notebook can add new notes, edit old notes," +
                " delete notes and show notes by it's headers keywords")
public class MyNotebook {

    /**
     * Static serializer of the notebook.
     */
    public static NotebookSerializer serializer;

    protected TreeMap<Date, Note> tempNotebook;

    /**
     * Default constructor with the
     * string filePath parameter.
     */
    public MyNotebook() {
        serializer = new NotebookSerializer();
        tempNotebook = pullData();
    }

    protected Note searchNoteByHeader(String header) {
        for (var note : tempNotebook.values()) {
            if (note.getHeader().contains(header)) {
                return note;
            }
        }
        return null;
    }

    protected TreeMap<Date, Note> pullData() {
        tempNotebook = serializer.getDataFromFile();
        return tempNotebook != null ? tempNotebook : new TreeMap<>();
    }

    protected void pushData(Map<Date, Note> notebook) {
        serializer.updateFile(notebook);
    }
}
