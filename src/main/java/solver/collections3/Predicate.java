package solver.collections3;

import java.util.*;
import java.util.stream.Collectors;

public class Predicate {

    private final String name;
    private final int arity;
    private final List<List<String>> values;

    public static void main(String[] args) {
        Predicate node = new Predicate("node", 1);
        node.add("a");
        node.add("b");
        node.add("c");
        System.out.println(node);
        System.out.println(node.get("b"));
        System.out.println(node.get("X"));
        System.out.println();

        Predicate edge = new Predicate("edge", 3);
        edge.add("a", "b", "ab");
        edge.add("a", "c", "ac");
        edge.add("b", "c", "bc");
        System.out.println(edge);
        System.out.println();

        System.out.println(edge.get("a", "X", "E"));
        System.out.println(edge.get("N1", "N2", "E"));
        System.out.println(edge.get("X", "b", "ab"));
        System.out.println(edge.get("a", "c", "ac"));
        System.out.println();
    }

    public Predicate(String name, int arity) {
        this(name, arity, new ArrayList<>());
    }

    public Predicate(String name, int arity, List<List<String>> values) {
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
        add(Arrays.stream(args).collect(Collectors.toList()));
    }

    public void add(List<String> args) {
        assert args.size() == arity;
        values.add(args);
    }

    public void addAll(Collection<List<String>> argsCollection) {
        argsCollection.forEach(this::add);
    }

    public List<Map<String, String>> get(String... args) {
        return get(Arrays.stream(args).collect(Collectors.toList()));
    }

    public List<Map<String, String>> get(List<String> args) {
        assert args.size() == arity;
        return values.stream().filter(value -> {
            for (int i = 0; i < arity; i++) {
                String argTerm = args.get(i);
                String valueTerm = value.get(i);
                if (isAtom(argTerm) && !argTerm.equals(valueTerm)) {
                    return false;
                }
            }
            return true;
        }).map(value -> {
            Map<String, String> result = new LinkedHashMap<>(); // Insertion order
            for (int i = 0; i < arity; i++) {
                result.put(args.get(i), value.get(i));
            }
            return result;
        }).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return values.toString();
    }

    private static boolean isAtom(String name) {
        return !isVariable(name);
    }

    private static boolean isVariable(String name) {
        return Character.isUpperCase(name.charAt(0));
    }
}
