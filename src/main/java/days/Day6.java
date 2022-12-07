package days;

import input.Cookie;
import template.Template;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Day6 extends Template {
    public Day6(int day, Cookie cookie) {
        super(day, cookie);
    }

    private final Supplier<List<String>> getTestMarker = () -> List.of(
            "mjqjpqmgbljsphdztnvjfqwrcgsmlb",
            "bvwbjplbgvbhsrlpgdmjqwftvncz",
            "nppdvjthqldpwncqszvftbrmjlhg",
            "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg",
            "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"
    );

    private int findPos(String marker) {
        char[] chars = marker.toCharArray();
        for(int pos = 0; pos + 3 < chars.length; pos++) {
            Set<String> characterSet = Arrays.stream(marker.substring(pos, pos + 4)
                    .split(""))
                    .collect(Collectors.toSet());
            if(characterSet.size() == 4) return pos + 4;
        }
        return -1;
    }

    private int findStartOfMessageMarker(String marker) {
        char[] chars = marker.toCharArray();
        for(int pos = 0; pos + 14 < chars.length; pos++) {
            Set<String> characterSet = Arrays.stream(marker.substring(pos, pos + 15)
                            .split(""))
                    .collect(Collectors.toSet());
            if(characterSet.size() == 14) return pos + 15;
        }
        return -1;
    }

    private void testProblemOne() {
        List<String> input = getTestMarker.get();
        input.stream()
                .map(this::findPos)
                .forEach(System.out::println);
    }

    private void testProblemTwo() {
        List<String> input = getTestMarker.get();
        input.stream()
                .map(this::findStartOfMessageMarker)
                .forEach(System.out::println);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveFirst() {
        return (T) Integer.valueOf(findPos(getContent().get(0)));
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveSecond() {
        return (T) Integer.valueOf(findStartOfMessageMarker(getContent().get(0)));
    }
}
