package ru.nsu.fit.tretyakov.subcommands;

import com.sun.source.tree.Tree;
import picocli.CommandLine;
import ru.nsu.fit.tretyakov.MyNotebook;
import ru.nsu.fit.tretyakov.Note;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * This class shows notes by theirs keywords,
 * and it can also sort notes by time.
 */
@CommandLine.Command(name = "show", description = "Show all notes in the notebook" +
        " which is sorted by time of creation", mixinStandardHelpOptions = true)
public class Show implements Runnable {
    /**
     * This field is the optional parameter of the
     * starting date of the notebook display.
     */
    @CommandLine.Option(
            names = {"-s", "--start"},
            paramLabel = "start date",
            description = "From this date notebook will be showed",
            defaultValue = CommandLine.Option.NULL_VALUE)
    private String startDate;

    /**
     * This field is the optional parameter of the
     * ending date of the notebook display.
     */
    @CommandLine.Option(
            names = {"-e", "--end"},
            paramLabel = "end date",
            description = "Till this date notebook will be showed",
            defaultValue = CommandLine.Option.NULL_VALUE)
    private String endDate;
    @CommandLine.Parameters(paramLabel = "keywords set", description = "Keywords that will be" +
            "contained in the result set")
    private Collection<String> keywords;

    @CommandLine.ParentCommand
    private MyNotebook notebook;

    /**
     * This method shows notebook by its keywords, starting date or ending date.
     *
     * @throws IllegalStateException if none of the keywords are matched any
     *                               header of the note in the notebook
     * @throws ParseException        if any of start date of end date is incorrect form
     */
    public void showNotebook() throws IllegalStateException, ParseException {

        TreeMap<Date, Note> tempNotebook = notebook.pullData();

        if (tempNotebook.isEmpty()) {
            System.out.println("The notebook is empty");
            return;
        }

        if (keywords == null) {
            showNotebookFromTimeToTime(tempNotebook);
        } else {
            TreeMap<Date, Note> sortedNotebook = new TreeMap<>();
            for (var keyword : keywords) {
                for (var note : tempNotebook.values()) {
                    if (note.getHeader().equals(keyword)) {
                        sortedNotebook.put(note.date, note);
                    }
                }
            }
            if (!sortedNotebook.isEmpty()) {
                showNotebookFromTimeToTime(sortedNotebook);
            } else {
                throw new IllegalStateException("None of the notes are matched keywords");
            }
        }
    }

    /**
     * This method filters notebook map from time to time.
     *
     * @param map is the required notebook
     * @throws ParseException if any of start date of end date is incorrect form
     */
    private void showNotebookFromTimeToTime(TreeMap<Date, Note> map) throws ParseException {

        var tempStream = map.entrySet().stream();

        if (startDate != null) {
            Date start = parseDate(startDate);
            tempStream = tempStream.filter(x -> x.getKey().after(start));
        }
        if (endDate != null) {
            Date end = parseDate(endDate);
            tempStream = tempStream.filter(x -> x.getKey().before(end));
        }

        TreeMap<Date, Note> newNotebook = new TreeMap<>(tempStream.collect(
                Collectors.toMap(Map.Entry::getKey,
                        Map.Entry::getValue)));

        for (var note : newNotebook.values()) {
            System.out.println("\n---------------------");
            note.showNote();
        }
    }

    /**
     * This method parses string value of the date to the Date object.
     *
     * @param date is the required string representation of the date
     * @return new Date object that was parsed from the string
     * @throws ParseException if the string is incorrect from
     */
    private Date parseDate(String date) throws ParseException {
        SimpleDateFormat dt = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        return dt.parse(date);
    }

    /**
     * Runnable method that runs main
     * method of this class.
     */
    @Override
    public void run() {
        try {
            showNotebook();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
