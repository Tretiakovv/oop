package ru.nsu.fit.tretyakov;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class is the serializer of the notebook.
 * It updates and get data from the JSON file which is linked to it.
 */
public class NotebookSerializer {
    private String filePath;
    private final Type mapType = new TypeToken<TreeMap<Date, Note>>() {
    }.getType();
    private final File file;
    private final Gson gson;

    /**
     * Overload of the default constructor of the class
     * with the user's file path.
     * @param filePath is the user's file path
     */
    public NotebookSerializer(String filePath) {
        this();
        this.filePath = filePath;
    }

    /**
     * Default constructor of the class.
     * Creates or connects with notebook.json file.
     */
    public NotebookSerializer() {
        filePath = "./notebook.json";
        file = new File(filePath);
        gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    /**
     * This method updates current JSON file by passed notebook map.
     *
     * @param files is the current notebook which is
     *              taken from the file
     */
    public void updateFile(Map<Date, Note> files) {
        try (Writer writer = new FileWriter(file)) {
            gson.toJson(files, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method gets the whole notebook.
     *
     * @return taken notebook map which is contained in the JSON file
     */
    public TreeMap<Date, Note> getDataFromFile() {
        try (FileReader reader = new FileReader(file)) {
            return gson.fromJson(reader, mapType);
        } catch (IOException e) {
            return null;
        }
    }
}
