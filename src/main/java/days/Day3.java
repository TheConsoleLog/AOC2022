package days;

import input.Cookie;
import template.Template;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class Day3 extends Template {

    public Day3(int day, Cookie cookie) {
        super(day, cookie);
    }

    private String[] splitCompartments(String rucksack) {
        int compartmentLength = rucksack.length() / 2;
        return new String[] { rucksack.substring(0, compartmentLength), rucksack.substring(compartmentLength) };
    }

    private final Function<String, List<Character>> mapToCharList = s -> s
            .chars()
            .mapToObj(c -> (char) c)
            .toList();

    private char findCommon(String[] compartments) {
        List<Character> firstCompartment = mapToCharList.apply(compartments[0]);
        List<Character> secondCompartment = mapToCharList.apply(compartments[1]);
        return firstCompartment.stream()
                .filter(secondCompartment::contains)
                .findFirst()
                .orElse('a');
    }

    private int findValue(char c) {
        final int startCodeLo = 'a';
        final int startCodeUp = 'A';
        if(!Character.isUpperCase(c)) {
            return (int)c - startCodeLo + 1;
        }
        return (int)c - startCodeUp + 27;
    }

    private final Supplier<List<String>> getTestInput = () -> List.of(
            "vJrwpWtwJgWrhcsFMMfFFhFp",
            "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL",
            "PmmdzqPrVvPwwTWBwg",
            "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn",
            "ttgJtRGJQctTZtZT",
            "CrZsJsPPZsGzwwsLwLmpwMDw"
    );

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveFirst() {
        List<String> input = getContent();
        return (T) input.stream()
                .map(this::splitCompartments)
                .map(this::findCommon)
                .map(this::findValue)
                .reduce(Integer::sum)
                .orElse(-1);
    }

    private List<String[]> divideByGroup(List<String> input) {
        List<String[]> result = new LinkedList<>();
        for(int i = 0; (i + 3) <= input.size(); i += 3) {
            result.add(new String[]{
                    input.get(i),
                    input.get(i + 1),
                    input.get(i + 2)
            });
        }
        return result;
    }

    private char findBadges(String[] rucksack) {
        List<Character> firstElf = mapToCharList.apply(rucksack[0]);
        List<Character> secondElf = mapToCharList.apply(rucksack[1]);
        List<Character> thirdElf = mapToCharList.apply(rucksack[2]);
        return firstElf.stream()
                .filter(c -> secondElf.contains(c) && thirdElf.contains(c))
                .findFirst()
                .orElse('a');
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveSecond() {
        List<String[]> groups = divideByGroup(getContent());
        return (T) groups.stream()
                .map(this::findBadges)
                .map(this::findValue)
                .reduce(Integer::sum)
                .orElse(-1);
    }
}