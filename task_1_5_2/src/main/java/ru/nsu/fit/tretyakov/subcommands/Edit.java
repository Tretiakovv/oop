package ru.nsu.fit.tretyakov.subcommands;

import picocli.CommandLine;
import ru.nsu.fit.tretyakov.MyNotebook;
import ru.nsu.fit.tretyakov.Note;

import java.util.Date;
import java.util.TreeMap;

/**
 * This class edits current note in the notebook
 * by its header and optional body.
 */
@CommandLine.Command(name = "edit", description = "Edit of the current note", mixinStandardHelpOptions = true)
public class Edit extends MyNotebook implements Runnable {
    /**
     * This field is the old header of the changing note,
     * by which this note will be founded.
     */
    @CommandLine.Parameters(paramLabel = "<oldHeader>",
            description = "Old header of the note")
    private String oldHeader;
    /**
     * This field is the new header of the changing note.
     */
    @CommandLine.Parameters(paramLabel = "<newHeader>",
            description = "New header of the note")
    private String newHeader;
    /**
     * This is the optional body of the new note.
     */
    @CommandLine.Option(names = {"-b", "--body"},
            description = "Body of the note",
            defaultValue = CommandLine.Option.NULL_VALUE)
    private String body;

    /**
     * This method changing existing note in the notebook by its header.
     * It can also change body of the note.
     *
     * @throws IllegalStateException if note with required header isn't in notebook
     */
    public void editNote() throws IllegalStateException {

        TreeMap<Date, Note> tempNotebook = pullData();

        Note newNode = new Remove(oldHeader).removeNote();

        if (newNode == null) {
            throw new IllegalStateException("This note isn't contained" +
                    " in the notebook");
        } else {
            newNode.setHeader(newHeader);
            if (body != null) newNode.setBody(body);
            newNode.showNote();
            tempNotebook.put(newNode.date, newNode);
            pushData(tempNotebook);
        }
    }

    /**
     * Runnable method that runs main
     * method of this class.
     */
    @Override
    public void run() {
        editNote();
    }
}
