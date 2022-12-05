package log;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AocLogger {

    private static final boolean APPEND = true;
    private static final File FOLDER = new File("src/main/resources/logs/log.xml");
    private static final Logger logger;

    static {
        try {
            FileHandler handler = new FileHandler(FOLDER.toPath().toString(), APPEND);
            handler.setLevel(Level.INFO);
            logger = Logger.getLogger("AOC2022");
            logger.addHandler(handler);
            logger.setLevel(Level.ALL);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void log(Level level, String msg) {
        logger.log(level, msg);
    }
}
