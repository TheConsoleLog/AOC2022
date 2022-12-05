package input;

import kong.unirest.Unirest;
import log.AocLogger;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

public class Request {

    private final int day;
    private final Cookie sessionID;
    private static Long lastRequest;
    private final File folder;

    public Request(int day, Cookie sessionID) {
        this.day = day;
        this.sessionID = sessionID;
        folder = new File("src/main/resources/days/");
    }

    public List<String> getContent() {
        AocLogger.log(Level.INFO, "Content of day " + day + " is called up");
        String path = folder + "/day" + day + ".txt";
        if(new File(path).length() == 0) {
            AocLogger.log(Level.INFO, "File is empty, request will be sent");
            List<String> input = sendRequest();
            if(input != null) {
                InputReadWriter.writeInput(input, new File(path));
            }
            return input;
        } else {
            AocLogger.log(Level.INFO, "Content is saved in file already!");
            return InputReadWriter.readInput(new File(path));
        }
    }

    private List<String> sendRequest() {
        AocLogger.log(Level.WARNING, "You are sending a request to AdventOfCode\n" +
                "Last request was " + lastRequest);
        if(lastRequest == null) lastRequest = System.currentTimeMillis();
        else if(System.currentTimeMillis() - lastRequest < 180000) {
            System.err.println("Sorry, du schickst zu viele Anfragen!");
            AocLogger.log(Level.WARNING, "Sry, but there are too many request right now!");
            return null;
        }
        AocLogger.log(Level.INFO, "Request is being sent now!");
        String response = Unirest.get("https://adventofcode.com/2022/day/" + day + "/input")
                .header("Cookie", "session=" + sessionID.getSessionCookie())
                .asString()
                .getBody();
        return List.of(response);
    }
}
