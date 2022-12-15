import input.Cookie;
import log.AocLogger;
import org.burningwave.core.classes.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import template.Template;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class Day {

    private final int day;
    private Cookie cookie;
    private Class<?> c;
    private Object instance;

    public Day() {
        this(getCurrentDay().get());
    }

    public Day(int day) {
        this.day = Math.min(day, 25);
        searchClass();
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

    public Day setCookie(Cookie cookie) {
        this.cookie = cookie;
        return this;
    }

    public Day runCode() {
        invokeMethods();
        return this;
    }

    private void searchClass() {
        try {
            c = Class.forName("days.Day" + day);
        } catch (ClassNotFoundException classNotFoundException) {
            createAndLoadClass();
        }
        try {
            instance = c.getDeclaredConstructors()[0].newInstance(day, cookie);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void invokeMethods() {
        Set<Method> methods = Arrays.stream(c.getDeclaredMethods())
                .filter(m -> m.getName().equals("solveFirst") || m.getName().equals("solveSecond"))
                .collect(Collectors.toSet());
        AtomicInteger ai = new AtomicInteger(1);
        methods.forEach(m -> {
            try {
                System.out.println("Solve " + ai.getAndIncrement() + ": " + m.invoke(instance));
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void createAndLoadClass() {
        UnitSourceGenerator dayClass = UnitSourceGenerator.create("days").addClass(
                ClassSourceGenerator.create(
                        TypeDeclarationSourceGenerator.create("Day" + day)
                ).addModifier(
                        Modifier.PUBLIC
                ).addConstructor(
                        FunctionSourceGenerator.create("Day" + day)
                                .addModifier(Modifier.PUBLIC)
                                .addBodyCodeLine("super(day, cookie);")
                                .useType(ZoneId.class)
                                .addParameter(VariableSourceGenerator.create("day").setElementPrefix("int "))
                                .addParameter(VariableSourceGenerator.create("cookie").setElementPrefix("input.Cookie "))
                ).addMethod(
                        FunctionSourceGenerator.create("solveFirst")
                                .setReturnType(
                                        TypeDeclarationSourceGenerator.create("<T> T")
                                ).addModifier(Modifier.PUBLIC)
                                .addAnnotation(AnnotationSourceGenerator.create(SuppressWarnings.class)
                                        .addParameter(VariableSourceGenerator.create("\"unchecked\"")))
                                .addBodyCodeLine("return null;")
                ).addMethod(
                        FunctionSourceGenerator.create("solveSecond")
                                .setReturnType(
                                        TypeDeclarationSourceGenerator.create("<T> T")
                                ).addModifier(Modifier.PUBLIC)
                                .addAnnotation(AnnotationSourceGenerator.create(SuppressWarnings.class)
                                        .addParameter(VariableSourceGenerator.create("\"unchecked\"")))
                                .addBodyCodeLine("return null;")
                ).expands(Template.class)
        );
        dayClass.storeToClassPath("src/main/java/");
        try {
            c = Class.forName("days.Day" + day);
        } catch (ClassNotFoundException cnfe) {
            AocLogger.log(Level.SEVERE, "Error while loading class");
        }
        AocLogger.log(Level.INFO, "Class Day" + day + " successfully created and saved!");
    }
}
