package ru.nsu.fit.tretyakov.subcommands;

import picocli.CommandLine;
import ru.nsu.fit.tretyakov.MyNotebook;
import ru.nsu.fit.tretyakov.Note;

import java.util.Date;
import java.util.TreeMap;

/**
 * This class removes current note from the notebook by its header.
 */
@CommandLine.Command(name = "rm", description = "Remove note by its header", mixinStandardHelpOptions = true)
public class Remove implements Runnable {

    @CommandLine.ParentCommand
    private static MyNotebook notebook;
    /**
     * This field is the required header of the note.
     */
    @CommandLine.Parameters(paramLabel = "<header>",
            description = "Header of the note")
    private String header;

    /**
     * Default constructor of the class.
     */
    public Remove() {

    }

    /**
     * Overload of the main constructor with
     * the string header parameter.
     *
     * @param header is the required header of the note
     *               which will be added to the notebook
     */
    public Remove(String header) {
        this.header = header;
    }

    /**
     * Overload of the default constructor of the
     * class with the current notebook state parameter.
     *
     * @param newNotebook is the current state of the notebook
     * @return removed note from the notebook
     */
    public Note removeNote(MyNotebook newNotebook) {
        notebook = newNotebook;
        return removeNote();
    }

    /**
     * This method removes first occurrence of the specific note from the notebook by its header.
     *
     * @return removed note from the notebook
     * @throws IllegalStateException if note with required header isn't in notebook
     */
    public Note removeNote()
            throws IllegalStateException {

        TreeMap<Date, Note> tempNotebook = notebook.pullData();

        if (!tempNotebook.isEmpty()) {
            Note removedNote = notebook.searchNoteByHeader(header);
            if (removedNote != null) {
                tempNotebook.remove(removedNote.date);
                notebook.pushData(tempNotebook);
                System.out.println("Removed note:\n");
                removedNote.showNote();
                return removedNote;
            } else throw new IllegalStateException("This note isn't contained" +
                    "in the notebook");
        } else {
            System.out.println("Notebook is empty");
            return null;
        }
    }

    /**
     * Runnable method that runs main
     * method of this class.
     */
    @Override
    public void run() {
        removeNote();
    }
}
