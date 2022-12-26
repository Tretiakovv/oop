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
    private static final String notebookFile = "./notebook.txt";
    private static File file;
    private final Type mapType = new TypeToken<TreeMap<Date, Note>>() {
    }.getType();
    private final Gson gson;

    /**
     * Default constructor of the class.
     * Creates or connects with notebook.json file.
     */
    public NotebookSerializer() {
        file = getPathToFile();
        gson = new GsonBuilder()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
    }

    /**
     * This method gets path to the ".json" file from the file.
     *
     * @return new File with the taken path to the
     * notebook ".json" file
     */
    private File getPathToFile() {
        try (BufferedReader br = new BufferedReader(
                new FileReader(notebookFile))) {
            return new File(br.readLine());
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Setter of the new path to the output ".json" file.
     *
     * @param newFilePath is the required new path to the ".json" file
     */
    public void setNewPathToFile(String newFilePath) {
        try (BufferedWriter br = new BufferedWriter(
                new FileWriter(notebookFile))) {
            br.write(newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
