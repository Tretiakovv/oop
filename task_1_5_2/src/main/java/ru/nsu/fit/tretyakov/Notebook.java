package ru.nsu.fit.tretyakov;

import java.io.IOException;
import java.util.*;

public interface Notebook {
    void addNote(Note note) throws IOException;
    Note removeNote(Note note) throws IOException;
    void showNotebook() throws IOException;
    void showNotesByKeywords(Collection<String> keywords) throws IOException;
}
