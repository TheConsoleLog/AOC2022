package days;

import input.Cookie;
import template.Template;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Supplier;

public class Day5 extends Template {
    public Day5(int day, Cookie cookie) {
        super(day, cookie);
    }

    private List<Integer[]> getMoves(List<String> input) {
        /*List<String[]> moves =  input.stream()
                .map(str -> str.substring(6))
                .peek(System.out::println)
                .map(String::trim)
                .map(str -> str.split("from|to"))
                .peek(Arrays::toString)
                .toList();*/
        /*
        List<Integer[]> result = new LinkedList<>();
        for (String[] move : moves) {
            Integer[] arr = new Integer[3];
            for (int i = 0; i < move.length; i++) {
                System.out.println(move[i]);
                arr[i] = Integer.parseInt(move[i].trim());
            }
            result.add(arr);
        }
         */
        return null; //result;
    }

    private final Supplier<List<String>> getTestInput = () -> List.of(
                "move 1 from 2 to 1" +
                    "move 3 from 1 to 3" +
                    "move 2 from 2 to 1" +
                    "move 1 from 1 to 2"
    );

    @Override
    public <T> T solveFirst() {
        List<Integer[]> moves = getMoves(getTestInput.get());
        moves.forEach(arr -> System.out.println(Arrays.toString(arr)));
        return null;
    }

    @Override
    public <T> T solveSecond() {
        return null;
    }
}
