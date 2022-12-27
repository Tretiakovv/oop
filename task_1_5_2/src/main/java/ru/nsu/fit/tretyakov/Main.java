package ru.nsu.fit.tretyakov;

import picocli.CommandLine;
import ru.nsu.fit.tretyakov.subcommands.*;

public class Main {
    /**
     * Main method which parses command line arguments and do specific method
     *
     * @param args is the arguments which are passed to the command line
     * @throws IllegalStateException if any of the commands throws IllegalStateException
     */
    public static void main(String[] args) throws IllegalStateException {
        final CommandLine cmd = new CommandLine(new MyNotebook());
        cmd.execute(args);
    }
}
