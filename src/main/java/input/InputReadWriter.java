package input;

import log.AocLogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class InputReadWriter {

    public static List<String> readInput(File file) {
        AocLogger.log(Level.INFO, "Trying to read from file");
        try (BufferedReader bw = new BufferedReader(Files.newBufferedReader(file.toPath()))) {
            return bw.lines().collect(Collectors.toList());
        } catch (IOException e) {
            AocLogger.log(Level.SEVERE, "Error while reading file <" + file.getName() + ">");
            e.printStackTrace();
        }
        return null;
    }

    public static void writeInput(List<String> content, File file) {
        AocLogger.log(Level.INFO, "Trying to write to file");
        try (BufferedWriter bw = new BufferedWriter(Files.newBufferedWriter(file.toPath()))) {
            content.forEach(line -> {
                try {
                    bw.write(line);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            bw.flush();
        } catch (IOException e) {
            AocLogger.log(Level.SEVERE, "Error while writing file <" + file.getName() + ">");
            e.printStackTrace();
        }
    }
}
