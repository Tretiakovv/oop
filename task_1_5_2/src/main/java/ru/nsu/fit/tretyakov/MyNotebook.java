package ru.nsu.fit.tretyakov;

import picocli.CommandLine;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Command;

import java.util.*;

/**
 * This class implements Notebook interface, which allows
 * user to add, edit, delete notes and show all notebook
 * by specific keywords
 */
@Command(name = "notebook", mixinStandardHelpOptions = true,
        description = "Notebook can add new notes, edit old notes," +
                " delete notes and show notes by it's headers keywords")
public class MyNotebook implements Notebook {

    private static final int EXIT_FAILURE = 1;
    private final NotebookSerializer serializer;
    private TreeMap<Date, Note> tempNotebook;
    private static Calendar calendar;

    /**
     * Default constructor of the class, which
     * initializes NotebookSerializer and pulled data from
     * the external json file
     */
    public MyNotebook() {
        serializer = new NotebookSerializer();
        tempNotebook = pullData();
    }

    /**
     * Main method which parses command line arguments and do specific method
     *
     * @param args is the arguments which are passed to the command line
     */
    public static void main(String[] args) {
        initCalendar();
        final CommandLine cmd = new CommandLine(new MyNotebook());
        int exitCode = cmd.execute(args);
        System.exit(exitCode);
    }

    /**
     * This method adds specific note to the notebook
     * with passed header and body parameters.
     *
     * @param header is the required header of the new note
     * @param body   is the optional body of the new note
     */
    @Command(name = "add", description = "Add a new note to the notebook", mixinStandardHelpOptions = true)
    @Override
    public void addNote(
            @Parameters(paramLabel = "<header>",
                    description = "Header of the note") String header,
            @Option(names = {"-b", "--body"},
                    paramLabel = "<body>",
                    description = "Main body of the notes") String body) {
        Note tmpNote = new Note(header, body);
        tempNotebook = pullData();
        tempNotebook.put(tmpNote.date, tmpNote);
        pushData(tempNotebook);
    }

    /**
     * This method removes first occurrence of the specific note from the notebook by its header.
     *
     * @param header is the required header of the note
     * @return removed note from the notebook
     */
    @Command(name = "rm", description = "Remove note by its header", mixinStandardHelpOptions = true)
    @Override
    public Note removeNote(
            @Parameters(paramLabel = "<header>",
                    description = "Header of the note") String header) {
        tempNotebook = pullData();
        if (!tempNotebook.isEmpty()) {
            Note removedNote = searchNoteByHeader(header);
            if (removedNote != null) {
                tempNotebook.remove(removedNote.date);
                pushData(tempNotebook);
                System.out.println("Removed note:\n");
                removedNote.showNote();
                return removedNote;
            } else printErrorMessage("This note isn't contained" +
                    "in the notebook");
        } else {
            printErrorMessage("Notebook is empty");
        }
        return null;
    }

    /**
     * This method shows all notes in the notebook or shows all notes
     * which headers are suiting for the passed keywords
     *
     * @param keywords is a specific collection of keywords
     *                 for which the notebook will be displayed
     */
    @Command(name = "show", description = "Show all notes in the notebook" +
            " which is sorted by time of creation", mixinStandardHelpOptions = true)
    @Override
    public void showNotebook(
            @Parameters(paramLabel = "keywords set", description = "Keywords that will be" +
                    "contained in the result set")
            Collection<String> keywords) {

        tempNotebook = pullData();
        if (tempNotebook.isEmpty()) {
            printErrorMessage("The notebook is empty");
        }

        if (keywords == null) {
            for (var note : tempNotebook.values()) {
                System.out.println("\n---------------------");
                note.showNote();
            }
        } else {
            Map<Date, Note> sortedNotebook = new TreeMap<>();
            for (var keyword : keywords) {
                for (var note : tempNotebook.values()) {
                    if (note.getHeader().equals(keyword)) {
                        sortedNotebook.put(note.date, note);
                    }
                }
            }
            if (!sortedNotebook.isEmpty()) {
                for (var note : sortedNotebook.values()) {
                    System.out.println("\n---------------------");
                    note.showNote();
                }
            } else {
                printErrorMessage("None of the notes are matched keywords");
            }
        }
    }

    /**
     * This method changing existing note in the notebook by its header.
     * It can also change body of the note.
     *
     * @param oldHeader is the old header of the changing note,
     *                  by which this note will be founded
     * @param newHeader is the new header of the changing note
     * @param body      is the optional body of the new note
     */
    @Command(name = "edit", description = "Edit of the current note", mixinStandardHelpOptions = true)
    @Override
    public void editNote(
            @Parameters(paramLabel = "<oldHeader>",
                    description = "Old header of the note") String oldHeader,
            @Parameters(paramLabel = "<newHeader>",
                    description = "New header of the note") String newHeader,
            @Option(names = {"-b", "--body"},
                    description = "Body of the note",
                    defaultValue = Option.NULL_VALUE) String body) {

        tempNotebook = pullData();
        Note newNode = removeNote(oldHeader);

        if (newNode == null) {
            printErrorMessage("This note isn't contained" +
                    " in the notebook");
        } else {
            newNode.setHeader(newHeader);
            if (body != null) newNode.setBody(body);
            newNode.showNote();
            tempNotebook.put(newNode.date, newNode);
            pushData(tempNotebook);
        }
    }

    private static void initCalendar(){
        calendar = Calendar.getInstance();
        calendar.setTime(new Date());
    }

    private void printErrorMessage(String message) {
        System.out.println(message);
        System.exit(EXIT_FAILURE);
    }

    private Note searchNoteByHeader(String header) {
        for (var note : tempNotebook.values()) {
            if (note.getHeader().contains(header)) {
                return note;
            }
        }
        return null;
    }

    private TreeMap<Date, Note> pullData() {
        tempNotebook = serializer.getDataFromFile();
        return tempNotebook != null ? tempNotebook : new TreeMap<>();
    }

    private void pushData(Map<Date, Note> notebook) {
        serializer.updateFile(notebook);
    }
}
