package days;

import input.Cookie;
import template.Template;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class Day4 extends Template {

    public Day4(int day, Cookie cookie) {
        super(day, cookie);
    }

    private final Supplier<List<String>> getTestInput = () -> List.of(
            "2-4,6-8",
            "2-3,4-5",
            "5-7,7-9",
            "2-8,3-7",
            "6-6,4-6",
            "2-6,4-8"
    );

    private List<String[]> divideToPairs(List<String> input) {
        return input.stream()
                .map(s -> s.split(","))
                .toList();
    }

    private boolean containsFully(String[] pair) {
        int[] p1 = IntStream.range(
                Integer.parseInt(pair[0].split("-")[0]),
                (Integer.parseInt(pair[0].split("-")[1]) + 1))
                .toArray();
        int[] p2 = IntStream.range(
                 Integer.parseInt(pair[1].split("-")[0]),
                 (Integer.parseInt(pair[1].split("-")[1]) + 1))
                .toArray();
        List<Integer> l1 = Arrays.stream(p1).boxed().toList();
        List<Integer> l2 = Arrays.stream(p2).boxed().toList();
        return new HashSet<>(l1).containsAll(l2) || new HashSet<>(l2).containsAll(l1);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveFirst() {
        List<String[]> pairs = divideToPairs(getContent());
        long l = pairs.stream()
                .map(this::containsFully)
                .filter(b -> b)
                .count();
        return (T) (String.valueOf(l));
    }

    private boolean isOverlapping(String[] pair) {
        int[] p1 = IntStream.range(
                        Integer.parseInt(pair[0].split("-")[0]),
                        (Integer.parseInt(pair[0].split("-")[1]) + 1))
                .toArray();
        int[] p2 = IntStream.range(
                        Integer.parseInt(pair[1].split("-")[0]),
                        (Integer.parseInt(pair[1].split("-")[1]) + 1))
                .toArray();
        List<Integer> l1 = Arrays.stream(p1).boxed().toList();
        List<Integer> l2 = Arrays.stream(p2).boxed().toList();
        return l1.stream().anyMatch(l2::contains);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveSecond() {
        List<String[]> pairs = divideToPairs(getContent());
        long l = pairs.stream()
                .map(this::isOverlapping)
                .filter(b -> b)
                .count();
        return (T) (String.valueOf(l));
    }
}
