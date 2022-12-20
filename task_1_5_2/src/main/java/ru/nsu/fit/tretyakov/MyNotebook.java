package ru.nsu.fit.tretyakov;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class MyNotebook implements Notebook{
    private final NotebookSerializer serializer;

    public MyNotebook(Note note) throws IOException {
        this();
        Map<Date,Note> tempNotebook = pullData();
        tempNotebook.put(note.date,note);
        pushData(tempNotebook);
    }

    public MyNotebook() throws IOException {
        serializer = new NotebookSerializer();
    }

    @Override
    public void addNote(Note note) throws FileNotFoundException {
        Map<Date,Note> tempNotebook = pullData();
        pushData(tempNotebook);
    }

    @Override
    public Note removeNote(Note note) throws FileNotFoundException {
        Map<Date,Note> tempNotebook = pullData();
        Note removedNote = tempNotebook.remove(note.date);
        pushData(tempNotebook);
        return removedNote;
    }

    @Override
    public void showNotebook() throws FileNotFoundException {
        Map<Date,Note> tempNotebook = pullData();
        for (var note: tempNotebook.values()){
            note.showNote();
        }
    }

    @Override
    public void showNotesByKeywords(Collection<String> keywords) throws FileNotFoundException {

        Map<Date, Note> tempNotebook = pullData();
        Map<Date, Note> sortedNotebook = new TreeMap<>(Date::compareTo);

        for (var keyword: keywords){
            for (var note: tempNotebook.values()){
                if (note.getHeader().contains(keyword)){
                    sortedNotebook.put(note.date,note);
                }
            }
        }

        for (var note: sortedNotebook.values()){
            note.showNote();
        }
    }

    private Map<Date,Note> pullData() throws FileNotFoundException {
        Map<Date,Note> tempNotebook = serializer.getDataFromFile();
        return tempNotebook != null ? tempNotebook : new TreeMap<>(Date::compareTo);
    }

    private void pushData(Map<Date,Note> notebook) throws FileNotFoundException{
        serializer.updateFile((Serializable) notebook);
    }
}
