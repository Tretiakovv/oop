package ru.nsu.fit.tretyakov;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.*;

public class NotebookTest {

    File jsonFile;
    Gson gson;
    Writer writer;

    @BeforeEach
    public void init() throws IOException {
        jsonFile = new File("note.json");
        gson = new GsonBuilder()
                .setDateFormat("yyyy")
                .enableComplexMapKeySerialization()
                .create();
        writer = new FileWriter(jsonFile);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void serializeNote() throws IOException, ClassNotFoundException {

        ArrayList<Note> notebook = new ArrayList<>();
        Note one = new Note("First note");
        Note two = new Note("Second note");
        notebook.add(one);
        notebook.add(two);
        ObjectOutputStream myWriter = new ObjectOutputStream(new FileOutputStream("notebook.dat"));
        myWriter.writeObject(notebook);
        myWriter.close();

        ObjectInputStream myReader = new ObjectInputStream(new FileInputStream("notebook.dat"));
        ArrayList<Note> tstList = (ArrayList<Note>) myReader.readObject();
        myReader.close();

        for (var note : tstList) {
            note.showNote();
        }
    }

    @Test
    public void testGson() throws IOException {
        Note one = new Note("First note");
        gson.toJson(one, writer);
        writer.flush();
        writer.close();
    }

    @Test
    public void simpleTest() throws IOException {
        Notebook myNotebook = new MyNotebook(new Note("First note"));
        myNotebook.addNote(new Note("Second note"));
        myNotebook.addNote(new Note("Third note"));
        myNotebook.showNotebook();
    }
}
