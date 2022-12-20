package ru.nsu.fit.tretyakov;

import ru.nsu.fit.tretyakov.Note;

import java.io.FileNotFoundException;
import java.util.*;

public interface Notebook {
    public void addNote(Note note) throws FileNotFoundException;
    public Note removeNote(Note note) throws FileNotFoundException;
    public void showNotebook() throws FileNotFoundException;
    public void showNotesByKeywords(Collection<String> keywords) throws FileNotFoundException;
}
