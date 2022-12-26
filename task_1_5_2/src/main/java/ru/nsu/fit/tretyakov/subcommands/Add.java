package ru.nsu.fit.tretyakov.subcommands;

import picocli.CommandLine;
import ru.nsu.fit.tretyakov.MyNotebook;
import ru.nsu.fit.tretyakov.Note;

import java.util.Date;
import java.util.TreeMap;

/**
 * This class adds a new note to the current notebook
 */
@CommandLine.Command(name = "add", description = "Add a new note to the notebook", mixinStandardHelpOptions = true)
public class Add extends MyNotebook implements Runnable {
    /**
     * This field is the required header of the new note.
     */
    @CommandLine.Parameters(paramLabel = "<header>",
            description = "Header of the note")
    private String header;

    /**
     * This field is the optional body of the new note.
     */
    @CommandLine.Option(names = {"-b", "--body"},
            paramLabel = "<body>",
            description = "Main body of the notes",
            defaultValue = CommandLine.Option.NULL_VALUE)
    private String body;

    /**
     * This method adds specific note to the notebook
     * with passed header and body parameters.
     */
    public void addNote() {
        Note tmpNote = new Note(header, body);
        TreeMap<Date, Note> tempNotebook = pullData();
        tempNotebook.put(tmpNote.date, tmpNote);
        pushData(tempNotebook);
    }

    /**
     * Runnable method that runs main
     * method of this class.
     */
    @Override
    public void run() {
        addNote();
    }
}
