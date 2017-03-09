package solver;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.constraints.extension.Tuples;
import org.chocosolver.solver.variables.IntVar;
import org.chocosolver.solver.variables.SetVar;
import org.graphstream.graph.implementations.MultiGraph;

import java.util.*;

public class Solver {
    private final Model model;
    private final Map<String, List<Set<Integer>>> predicates;

    private final MultiGraph graph;
    private final IdMap idMap;

    public static void main(String[] args) {
        Solver solver = new Solver();
        solver.label("A", "Karel");
        solver.label("A", "Kees");
        solver.solve();
    }

    public Solver() {
        model = new Model();
        predicates = new HashMap<>();

        graph = new MultiGraph("ABC");
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");

        idMap = new IdMap();

        demo();
    }

    private void demo() {
        int[] arr1 = new int[]{4, 1, 8};
        int[] arr2 = new int[]{6, 9, 10};
        int[] arr3 = new int[]{11, 14, 18};

        IntVar var1 = model.intVar(arr1);
        IntVar var2 = model.intVar(arr2);
        IntVar var3 = model.intVar(arr3);
        Tuples tuples = new Tuples(trasposeMatrix(new int[][] {arr1, arr2, arr3}), true);
//        tuples.add(new int[]{4, 6, 11}, new int[]{1, 9, 14}, new int[]{8, 10, 18});
//        model.table(var1, var2, tuples).post();
        model.table(new IntVar[]{var1, var2, var3}, tuples).post();
        while (model.getSolver().solve()) {
            System.out.println(var1);
            System.out.println(var2);
            System.out.println(var3);
            System.out.println();
        }
    }

    private static int[][] trasposeMatrix(int[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        int[][] trasposedMatrix = new int[columns][rows];
        for(int x = 0; x < columns; x++) {
            for(int y = 0; y < rows; y++) {
                trasposedMatrix[x][y] = matrix[y][x];
            }
        }
        return trasposedMatrix;
    }

    public void label(String node, String name) {
        List<Set<Integer>> values = predicates.computeIfAbsent("label", key -> emptyList(2));
        values.get(0).add(idMap.getId(graph.getNode(node)));
        values.get(1).add(idMap.getId(name));
    }

    public void solve() {

    }

    private Map<String, SetVar[]> convertMap(Map<String, List<Set<Integer>>> map) {
        Map<String, SetVar[]> result = new HashMap<>();
        map.entrySet().forEach(entry -> {
            String key = entry.getKey();
            SetVar[] value = convertList(entry.getValue());
            result.put(key, value);
        });
        return result;
    }

    private SetVar[] convertList(List<Set<Integer>> list) {
        return list.stream()
                .map(this::convertSet)
                .toArray(SetVar[]::new);
    }

    private SetVar convertSet(Set<Integer> set) {
        int[] values = set.stream().mapToInt(Integer::intValue).toArray();
        return model.setVar(values);
    }

    private static List<Set<Integer>> emptyList(int size) {
        List<Set<Integer>> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(new HashSet<>());
        }
        return list;
    }
}
