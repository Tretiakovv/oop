package ru.nsu.fit.tretyakov;

import java.io.IOException;
import java.util.*;

public class MyNotebook implements Notebook {
    private final NotebookSerializer serializer;

    private Map<Date, Note> tempNotebook;

    public MyNotebook(Note note) throws IOException {
        this();
        addNote(note);
    }

    public MyNotebook() throws IOException {
        serializer = new NotebookSerializer();
        tempNotebook = pullData();
    }

    @Override
    public void addNote(Note note) throws IOException {
        tempNotebook = pullData();
        tempNotebook.put(note.date, note);
        pushData(tempNotebook);
    }

    @Override
    public Note removeNote(Note note) throws IOException {
        tempNotebook = pullData();
        Note removedNote = tempNotebook.remove(note.date);
        pushData(tempNotebook);
        return removedNote;
    }

    @Override
    public void showNotebook() throws IOException {
        tempNotebook = pullData();
        for (var note : tempNotebook.values()) {
            System.out.println("\n---------------------");
            note.showNote();
        }
    }

    @Override
    public void showNotesByKeywords(Collection<String> keywords) throws IOException {

        tempNotebook = pullData();
        Map<Date, Note> sortedNotebook = new TreeMap<>(Date::compareTo);

        for (var keyword : keywords) {
            for (var note : tempNotebook.values()) {
                if (note.getHeader().contains(keyword)) {
                    sortedNotebook.put(note.date, note);
                    System.out.println();
                }
            }
        }

        for (var note : sortedNotebook.values()) {
            note.showNote();
        }
    }

    private Map<Date, Note> pullData() {
        tempNotebook = serializer.getDataFromFile();
        return tempNotebook != null ? tempNotebook : new TreeMap<>();
    }

    private void pushData(Map<Date, Note> notebook) {
        serializer.updateFile(notebook);
    }
}
