package solver.collections2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Predicate {

    private final String name;
    private final int arity;
    private final List<String[]> values;

    public static void main(String[] args) {
        Predicate node = new Predicate("node", 1);
        node.add("a");
        node.add("b");
        node.add("c");
        System.out.println(node);
        System.out.println();

        node.get("b").forEach(value -> System.out.println(Arrays.toString(value)));
        System.out.println();
        node.get("X").forEach(value -> System.out.println(Arrays.toString(value)));
        System.out.println();

        Predicate edge = new Predicate("edge", 3);
        edge.add("a", "b", "ab");
        edge.add("a", "c", "ac");
        edge.add("b", "c", "bc");
        System.out.println(edge);
        System.out.println();

        edge.get("a", "X", "E").forEach(value -> System.out.println(Arrays.toString(value)));
        System.out.println();
        edge.get("N1", "N2", "E").forEach(value -> System.out.println(Arrays.toString(value)));
        System.out.println();
        edge.get("X", "b", "ab").forEach(value -> System.out.println(Arrays.toString(value)));
        System.out.println();
        edge.get("a", "c", "ac").forEach(value -> System.out.println(Arrays.toString(value)));
        System.out.println();
    }

    public Predicate(String name, int arity) {
        this(name, arity, new ArrayList<>());
    }

    public Predicate(String name, int arity, List<String[]> values) {
        assert arity > 0;
        this.name = name;
        this.arity = arity;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public int getArity() {
        return arity;
    }

    public void add(String... args) {
        assert args.length == arity;
        values.add(args);
    }

    public void addAll(Collection<String[]> argsCollection) {
        for (String[] args : argsCollection) {
            add(args);
        }
    }

    public List<String[]> get(String... args) {
        assert args.length == arity;
        return values.stream().filter(value -> {
            for (int i = 0; i < arity; i++) {
                if (isAtom(args[i]) && !args[i].equals(value[i])) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return values.stream()
                .map(Arrays::toString)
                .collect(Collectors.joining());
    }

    private static boolean isAtom(String name) {
        return Character.isLowerCase(name.charAt(0));
    }

    private static boolean isVariable(String name) {
        return Character.isUpperCase(name.charAt(0));
    }
}
