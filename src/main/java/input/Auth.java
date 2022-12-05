package input;

import log.AocLogger;

import java.io.*;
import java.util.Optional;
import java.util.logging.Level;

public final class Auth {

    private final File sessionFile;
    private final Cookie cookie;

    public Auth(File sessionFile, String sessionId) {
        this.sessionFile = sessionFile;
        this.cookie = new Cookie(sessionId);
        if(!existsAndHasContent()) {
            AocLogger.log(Level.INFO, "Cookie file doesn't exist yet");
            writeObjectFile(cookie);
        }
    }

    public Auth(File sessionFile) {
        this.sessionFile = sessionFile;
        this.cookie = readObjectFile().orElseThrow(NullPointerException::new);
    }

    public Cookie getCookie() {
        return cookie;
    }

    private boolean existsAndHasContent() {
        return sessionFile.exists() && readObjectFile().isPresent();
    }

    private Optional<Cookie> readObjectFile() {
        AocLogger.log(Level.INFO, "Cookie file is being read from");
        Optional<Cookie> result = Optional.empty();
        try (FileInputStream fis = new FileInputStream(sessionFile);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            Cookie c;
            if((c = (Cookie) ois.readObject()) != null) {
                result = Optional.of(c);
            }
        } catch (EOFException eof) {
            AocLogger.log(Level.INFO, "EndOfFile in ObjectInputStream reached");
            return result;
        } catch (Exception e) {
            AocLogger.log(Level.SEVERE, "Error while reading cookie file");
            e.printStackTrace();
        }
        return result;
    }

    private void writeObjectFile(Cookie cookie) {
        AocLogger.log(Level.INFO, "Cookie file is being created and written to");
        try(FileOutputStream fos = new FileOutputStream(sessionFile.getPath());
            ObjectOutputStream oos = new ObjectOutputStream(fos)){
            oos.writeObject(cookie);
            oos.flush();
        } catch (IOException io) {
            AocLogger.log(Level.SEVERE, "Error while writing cookie file");
            io.printStackTrace();
        }
    }
}
