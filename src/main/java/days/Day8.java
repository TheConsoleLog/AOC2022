package days;

import input.Cookie;
import template.Template;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Day8 extends Template {
    private final Supplier<List<String>> getTestInput = () -> List.of(
            "30373",
            "25512",
            "65332",
            "33549",
            "35390"
    );
    private final Function<Character, Integer> convertToNum = (c) -> Integer.parseInt(String.valueOf(c));

    public Day8(int day, Cookie cookie) {
        super(day, cookie);
    }

    private int countEdgeTrees(List<String> input) {
        return (input.get(0).length() * 2) + ((input.size() - 2) * 2);
    }

    private int countVisibleInteriors(List<String> input) {
        int sum = 0;
        // height
        for (int i = 1; i < input.size() - 1; i++) {
            String line = input.get(i);
            // width
            for (int w = 1; w < line.length() - 1; w++) {
                final int val = convertToNum.apply(input.get(i).charAt(w));

                AtomicInteger height = new AtomicInteger(i);
                AtomicInteger width = new AtomicInteger(w);

                boolean hasUp = IntStream.range(0, i)
                        .mapToObj(up -> input.get(up).charAt(width.get()))
                        .map(convertToNum)
                        .anyMatch(h -> h >= val);

                boolean hasDown = IntStream.range(i + 1, input.size())
                        .mapToObj(down -> input.get(down).charAt(width.get()))
                        .map(convertToNum)
                        .anyMatch(h -> h >= val);

                boolean hasLeft = IntStream.range(0, w)
                        .mapToObj(left -> input.get(height.get()).charAt(left))
                        .map(convertToNum)
                        .anyMatch(wi -> wi >= val);

                boolean hasRight = IntStream.range(w + 1, line.length())
                        .mapToObj(right -> input.get(height.get()).charAt(right))
                        .map(convertToNum)
                        .anyMatch(wi -> wi >= val);

                if (!(hasUp && hasDown && hasLeft && hasRight))
                    sum++;

            }
        }
        return sum;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveFirst() {
        final List<String> input = getContent();
        final int sum = countEdgeTrees(input) + countVisibleInteriors(input);
        return (T) Integer.valueOf(sum);
    }

    private int searchTrees(List<String> input) {
        List<Integer> sizes = new LinkedList<>();
        for (int i = 1; i < input.size() - 1; i++) {
            String line = input.get(i);
            // width
            for (int w = 1; w < line.length() - 1; w++) {
                final int val = convertToNum.apply(input.get(i).charAt(w));

                AtomicInteger height = new AtomicInteger(i);
                AtomicInteger width = new AtomicInteger(w);

                List<Integer> up = new java.util.ArrayList<>(IntStream.range(0, i)
                        .mapToObj(u -> input.get(u).charAt(width.get()))
                        .map(convertToNum)
                        .toList());
                List<Integer> down = new java.util.ArrayList<>(IntStream.range(i + 1, input.size())
                        .mapToObj(d -> input.get(d).charAt(width.get()))
                        .map(convertToNum)
                        .toList());
                List<Integer> left = new java.util.ArrayList<>(IntStream.range(0, w)
                        .mapToObj(l -> input.get(height.get()).charAt(l))
                        .map(convertToNum)
                        .toList());
                List<Integer> right = new java.util.ArrayList<>(IntStream.range(w + 1, line.length())
                        .mapToObj(r -> input.get(height.get()).charAt(r))
                        .map(convertToNum)
                        .toList());

                long product = getSight(up, val) *
                        getSight(down, val) *
                        getSight(left, val) *
                        getSight(right, val);

                sizes.add((int) product);
            }
        }
        return sizes.stream()
                .max(Comparator.naturalOrder())
                .orElse(-1);
    }

    private long getSight(List<Integer> line, int height) {
        return line.stream()
                .sequential()
                .filter(n -> n < height)
                .count() + 1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveSecond() {
        final List<String> input = getTestInput.get();
        return (T) Integer.valueOf(searchTrees(input));
    }
}
