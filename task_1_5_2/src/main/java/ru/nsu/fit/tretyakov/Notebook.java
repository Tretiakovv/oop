package ru.nsu.fit.tretyakov;

import ru.nsu.fit.tretyakov.Note;

import java.util.*;

public class Notebook {
    private Map<Date, Note> notebook;

    public Notebook(Note note) {
        this();
        notebook.put(note.date, note);
    }

    public Notebook() {
        this.notebook = new TreeMap<>(Date::compareTo);
    }

    public void showNote() {

    }
}
