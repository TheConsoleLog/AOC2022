import days.Day8;
import input.Auth;
import template.Template;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        Auth authentication = new Auth(new File("src/main/resources/session.ser"));
        Template assignment = new Day8(8, authentication.getCookie());
        /*
        Day day = new Day()
                .setCookie(authentication.getCookie())
                .runCode();
        */
        System.out.println("RESULT 1: " + assignment.<Integer>solveFirst());
        System.out.println("RESULT 2: " + assignment.<Integer>solveSecond());
    }
}
