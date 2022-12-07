package days.day7;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Node {

    private final List<File> files;
    private final String name;
    private final Node parent;
    private final List<Node> children;

    public Node(String name, Node parent) {
        this.name = name;
        this.parent = parent;
        if(parent != null) this.parent.children.add(this);
        this.files = new LinkedList<>();
        this.children = new LinkedList<>();
    }

    public void addFile(File file) {
        this.files.add(file);
    }

    public void addFiles(List<File> files) {
        this.files.addAll(files);
    }

    public List<File> getFiles() {
        return files;
    }

    public String getName() {
        return name;
    }

    public Node getParent() {
        if(parent == null) return this;
        return parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public int getSize() {
        return files.stream()
                .map(File::getSize)
                .reduce(Integer::sum)
                .orElse(0) + getChildren().stream()
                    .map(Node::getSize)
                    .reduce(Integer::sum)
                    .orElse(0);
    }

    private long space;

    public List<Node> getFitting(long space) {
        this.space = space;
        return getFittingNodes();
    }

    public List<Node> getFittingNodes() {
        List<Node> result = new LinkedList<>();
        this.children.stream()
                .filter(p -> p.getSize() > space)
                .forEach(result::add);
        this.children.stream()
                .map(Node::getFittingNodes)
                .flatMap(List::stream)
                .filter(p -> p.getSize() > space)
                .forEach(result::add);
        return result;
    }

    public List<Node> getNodesUnder100k() {
        List<Node> result = new LinkedList<>();
        this.children.stream()
                .filter(p -> p.getSize() < 100000)
                .forEach(result::add);
        this.children.stream()
                .map(Node::getNodesUnder100k)
                .forEach(result::addAll);
        return result;
    }

    @Override
    public String toString() {
        return "=== Node ===" +
                "\n> name='" + name + '\'' +
                "\n> size=" + getSize() +
                "\n> parent=" + Optional.ofNullable(parent).orElse(new Node("PARENT", null)).getName() +
                "\n> files=" + files +
                "\n> children \t" + children;
    }
}
