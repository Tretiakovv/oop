package ru.nsu.fit.tretyakov.subcommands;

import picocli.CommandLine;
import ru.nsu.fit.tretyakov.MyNotebook;

/**
 * This class changes old path to
 * the output file to custom user's file
 */
@CommandLine.Command(name = "file", description = "sets user's path to the file" +
        " where's notebook will be placed", mixinStandardHelpOptions = true)
public class FilePath implements Runnable {

    /**
     * This field is the required path to the new file.
     */
    @CommandLine.Parameters(paramLabel = "<path>",
            description = "Path to the user's file",
            defaultValue = CommandLine.Option.NULL_VALUE)
    private static String filePath;

    /**
     * Runnable method that runs main
     * method of this class.
     */
    @Override
    public void run() {
        MyNotebook.serializer.setNewPathToFile(filePath);
    }
}
