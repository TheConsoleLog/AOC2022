package template;

import input.Cookie;
import input.Request;
import log.AocLogger;

import java.util.List;
import java.util.logging.Level;

public abstract class Template {

    private final Cookie cookie;
    private final int day;
    private List<String> content;

    public int getDay() {
        return day;
    }

    public List<String> getContent() {
        return List.of(content.toArray(String[]::new));
    }

    public Template(int day, Cookie cookie) {
        this.day = day;
        this.cookie = cookie;
        loadContent();
    }

    private void loadContent() {
        Request request = new Request(day, cookie);
        AocLogger.log(Level.INFO, "Content of day " + day + " is being requested!");
        this.content = request.getContent();
    }
    public abstract <T> T solveFirst();
    public abstract <T> T solveSecond();
}
