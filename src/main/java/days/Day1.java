package days;

import input.Cookie;
import template.Template;

import java.util.*;

public class Day1 extends Template {

    public Day1(int day, Cookie cookie) {
        super(day, cookie);
    }

    private List<Integer> findElves(List<String> content) {
        String s = String.join(",", content);
        String[] elves = s.split(",,");
        List<String[]> elf = Arrays.stream(elves)
                .map(calories -> calories.split(","))
                .toList();
        List<Integer> result = new LinkedList<>();
        for (String[] e : elf) {
            result.add(Arrays.stream(e)
                    .map(Integer::parseInt)
                    .reduce(Integer::sum)
                    .orElse(0));
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveFirst() {
        List<String> content = getContent();
        return (T) findElves(content).stream()
                .max(Comparator.naturalOrder())
                .orElse(0);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveSecond() {
        List<String> content = getContent();
        List<Integer> result = new ArrayList<>();
        List<Integer> allElves = findElves(content);
        for (int i = 1; i <= 3; i++) {
            int item = allElves.stream().max(Comparator.naturalOrder()).orElse(0);
            result.add(item);
            allElves.remove((Integer) item);
        }
        return (T)result.stream().reduce(Integer::sum).orElse(0);
    }
}
