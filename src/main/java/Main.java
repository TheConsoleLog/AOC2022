import input.Auth;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import template.Template;
import days.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.function.Supplier;

public class Main {

    public static void main(String[] args) {
        Auth authentication = new Auth(new File("src/main/resources/session.ser"));
        Template assignment = new Day7(getCurrentDay().get(), authentication.getCookie());
        System.out.println("RESULT 1: " + assignment.<Integer>solveFirst());
        System.out.println("RESULT 2: " + assignment.<Integer>solveSecond());
    }

    @Contract(pure = true)
    private static @NotNull Supplier<Integer> getCurrentDay() {
        return () -> {
            LocalDateTime day = LocalDateTime.now();
            return Math.min((day.getHour() < 6 && day.getDayOfMonth() > 1 ?
                            day.getDayOfMonth() - 1 :
                            day.getDayOfMonth()), 25);
        };
    }
}
