package days.day7;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;

public class File implements Comparable<File>, Entity {

    private String name;
    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    @Override
    public int compareTo(@NotNull File o) {
        return Comparator.<Integer>naturalOrder().compare(getSize(), o.getSize());
    }

    @Override
    public String toString() {
        return "File{" +
                "name='" + name + '\'' +
                ", size=" + size +
                '}';
    }
}
