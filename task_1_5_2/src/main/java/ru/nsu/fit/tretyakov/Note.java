package ru.nsu.fit.tretyakov;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;

public class Note implements Serializable {
    protected final Date date;
    private String header;
    private ArrayList<String> noteLines;

    public Note(String header) {
        this.header = header;
        this.date = new Date();
    }

    public String getHeader(){
        return this.header;
    }

    public void addNote(String note) {
        this.noteLines.add(note);
    }

    public void addNotes(Collection<String> notes) {
        this.noteLines.addAll(notes);
    }

    public void changeNote(String newHeader, Map<Integer,String> newLines) {
        this.header = newHeader;
        changeNote(newLines);
    }

    public void changeNote(Map<Integer,String> newLines){
        for (var lineIdx: newLines.keySet()){
            noteLines.set(lineIdx,newLines.get(lineIdx));
        }
    }

    public void showNote() {

        System.out.printf("Date the note was created: %s\n\n", date);
        System.out.printf("==== %s ====\n\n", this.header);

        if (noteLines != null) {
            for (int i = 1; i <= noteLines.size(); i++) {
                System.out.printf("#%d %s\n", i, noteLines.get(i));
            }
        } else System.out.println("This note is empty");
    }
}
