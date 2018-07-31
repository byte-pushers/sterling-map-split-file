package software.bytepushers.util;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class SterlingSplitter {
    private static final String POSITIVE_INTEGER_ERROR_MSG = "Second argument must be a positive integer";

    private int recordsPerFile;
    private Path file;

    public SterlingSplitter(Path filepath, int recordsPerFile) {
        this.recordsPerFile = recordsPerFile;
        this.file = filepath;
    }

    public void run() {
        String[] nameExt = file.getFileName().toString().split("\\.");

        Path pwd = Paths.get("");
        try {
            int fileId = 0;
            Path f = pwd.resolve(nextFileName(nameExt, fileId++));
            List<String> lines = Files.readAllLines(file);

            List<String> lineBuffer = new ArrayList<>();
            for (String str : lines) {
                lineBuffer.add(str);
                if (lineBuffer.size() == recordsPerFile) {
                    Files.write(f, lineBuffer, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
                    f = pwd.resolve(nextFileName(nameExt, fileId++));
                    lineBuffer.clear();
                }
            }
            if (lineBuffer.size() > 0) {
                Files.write(f, lineBuffer, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while writing files.");
            System.exit(1);
        }
    }

    public static String nextFileName(String[] nameExt, int n) {
        return nameExt[0] + '-' + n + '.' + nameExt[1];
    }

    public static void main(String[] args) {
        if(args.length != 2) {
            printUsage();
            System.exit(1);
        }

        Path filepath = null;
        try {
            filepath = Paths.get(args[0]);
        } catch (InvalidPathException e) {
            printMessageAndExit("Invalid path: " + args[0]);
        }

        int recordsPerFile = 0;
        try {
            recordsPerFile = Integer.valueOf(args[1]);
        } catch (NumberFormatException e) {
            printMessageAndExit(POSITIVE_INTEGER_ERROR_MSG);
        }

        if (recordsPerFile <= 0) {
            printMessageAndExit(POSITIVE_INTEGER_ERROR_MSG);
        }

        if (!Files.isWritable(Paths.get(""))) {
            printMessageAndExit("Could not write to current working directory: "
                    + Paths.get("").toAbsolutePath().toString());
        }

        new SterlingSplitter(filepath, recordsPerFile).run();
    }



    private static void printUsage() {
        System.out.println("usage: java -jar stersplit.jar [path] [n]");
    }

    private static void printMessageAndExit(String msg) {
        System.out.println(msg);
        System.exit(1);
    }
}
