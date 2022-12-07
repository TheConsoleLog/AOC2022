package days;

import days.day7.File;
import days.day7.Node;
import input.Cookie;
import template.Template;

import java.util.*;
import java.util.function.Supplier;

public class Day7 extends Template {

    public Day7(int day, Cookie cookie) {
        super(day, cookie);
    }

    private final Supplier<List<String>> getTestInput = () -> List.of(
              "$ cd /",
              "$ ls",
              "dir a",
              "14848514 b.txt",
              "8504156 c.dat",
              "dir d",
              "$ cd a",
              "$ ls",
              "dir e",
              "29116 f",
              "2557 g",
              "62596 h.lst",
              "$ cd e",
              "$ ls",
              "584 i",
              "$ cd ..",
              "$ cd ..",
              "$ cd d",
              "$ ls",
              "4060174 j",
              "8033020 d.log",
              "5626152 d.ext",
              "7214296 k"
    );

    private List<String[]> subtractCommands(List<String> input) {
        List<String[]> result = new LinkedList<>();
        List<String> currentCommand = new LinkedList<>();
        for (String s : input) {
            if(s.startsWith("$") && currentCommand.size() > 0) {
                result.add(currentCommand.toArray(String[]::new));
                currentCommand.clear();
            }
            currentCommand.add(s);
        }
        if(!currentCommand.isEmpty()) result.add(currentCommand.toArray(String[]::new));
        return result;
    }

    private final Node root = new Node("/", null);
    private Node currentDirectory = root;

    private void executeCommand(String[] command) {
        String cmd = command[0];
        if(cmd.contains("$ cd")) {
            if(cmd.endsWith("/")) this.currentDirectory = root;
            if(cmd.endsWith("..")) {
                currentDirectory = currentDirectory.getParent();
                return;
            }
            String dirName = cmd.split(" ")[2];
            Optional<Node> getDir = currentDirectory.getChildren().stream()
                    .filter(node -> node.getName().equals(dirName))
                    .findAny();
            if(getDir.isEmpty()) throw new NullPointerException("Can't find dir!");
            currentDirectory = getDir.get();
        } else if(cmd.contains("$ ls")) {
            Arrays.stream(command)
                    .skip(1)
                    .filter(str -> str.startsWith("dir"))
                    .forEach(str -> createNode(currentDirectory, str));
            Arrays.stream(command)
                    .skip(1)
                    .filter(str -> !str.startsWith("dir"))
                    .forEach(str -> createFile(currentDirectory, str));
        }
    }

    private void createNode(Node parent, String line) {
        new Node(line.split(" ")[1], parent);
    }

    private void createFile(Node parent, String line) {
        final String[] data = line.split(" ");
        final int size = Integer.parseInt(data[0]);
        final String name = data[1];
        File file = new File(name, size);
        parent.addFile(file);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveFirst() {
        List<String> input = getContent();
        List<String[]> commands = subtractCommands(input);
        commands.stream().skip(1).forEach(this::executeCommand);

        Integer sum = root.getNodesUnder100k().stream()
                .map(Node::getSize)
                .reduce(Integer::sum)
                .orElse(0);

        return (T) sum;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T solveSecond() {
        final long neededSpace = 30000000;
        final long freeSpace = 70000000 - root.getSize();

        List<Node> fitting = root.getFitting(neededSpace - freeSpace);
        return (T) (Integer) fitting.stream()
                .min(Comparator.comparing(Node::getSize))
                .orElse(new Node("none", null))
                .getSize();
    }
}
