package ru.nsu.fit.tretyakov;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

public class NotebookSerializer {
    private final static String filePath = "tst.json";
    private final Type mapType = new TypeToken<Map<Date, Note>>() {
    }.getType();
    private final File file;
    private final Gson gson;

    public NotebookSerializer() {
        file = new File(filePath);
        gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }
    public void updateFile(Map<Date, Note> files){
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(files, writer);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public Map<Date, Note> getDataFromFile() {
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, mapType);
        } catch (IOException e) {
            return null;
        }
    }
}
