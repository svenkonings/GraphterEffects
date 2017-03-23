package solver.collections2;

import java.util.*;

// All values shouldn't be null and Strings shouldn't be empty
public class PredicateOld {

    private final String name;
    private final int arity;
    private final List<List<String>> values;

    public static void main(String[] args) {
        PredicateOld node = new PredicateOld("node", 1);
        node.add("a");
        node.add("b");
        node.add("c");
        System.out.println(node.get("b"));
        System.out.println(node.get("X"));
        System.out.println();

        PredicateOld edge = new PredicateOld("edge", 3);
        edge.add("a", "b", "ab");
        edge.add("a", "c", "ac");
        edge.add("b", "c", "bc");
        System.out.println(edge.get("a", "X", "E"));
        System.out.println(edge.get("N1", "N2", "E"));
        System.out.println(edge.get("X", "b", "ab"));
        System.out.println(edge.get("a", "c", "ac"));
        System.out.println();
    }

    public PredicateOld(String name, int arity) {
        assert arity > 0;
        this.name = name;
        this.arity = arity;
        this.values = emptyMatrix(arity);
    }

    public PredicateOld(String name, List<List<String>> values) {
        assert values.size() > 0;
        this.name = name;
        this.arity = values.size();
        this.values = Collections.unmodifiableList(values);
    }

    public String getName() {
        return name;
    }

    public int getArity() {
        return arity;
    }

    public void add(String... args) {
        assert args.length == arity;
        for (int i = 0; i < arity; i++) {
            values.get(i).add(args[i]);
        }
    }

    // All collections should have equal length
    public void addAll(Collection<String>... argsCollections) {
        assert argsCollections.length == arity;
        for (int i = 0; i < arity; i++) {
            values.get(i).addAll(argsCollections[i]);
        }
    }

    public Map<String, List<String>> get(String... args) {
        assert args.length == arity;
        Map<String, List<String>> result = new HashMap<>();

        loop:
        for (int row = 0; row < values.get(0).size(); row++) {
            for (int column = 0; column < arity; column++) {
                if (isAtom(args[column]) && !args[column].equals(values.get(column).get(row))) {
                    continue loop;
                }
            }
            for (int column = 0; column < arity; column++) {
                List<String> value = result.computeIfAbsent(args[column], key -> new ArrayList<>());
                value.add(values.get(column).get(row));
            }
        }
        return result;
    }

    private static List<List<String>> emptyMatrix(int size) {
        List<List<String>> lists = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            lists.add(new ArrayList<>());
        }
        return Collections.unmodifiableList(lists);
    }

    private static boolean isAtom(String name) {
        return Character.isLowerCase(name.charAt(0));
    }

    private static boolean isVariable(String name) {
        return Character.isUpperCase(name.charAt(0));
    }
}
