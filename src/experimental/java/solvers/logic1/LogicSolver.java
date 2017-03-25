package solvers.logic1;

import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Jatalog;

import java.util.Collection;
import java.util.Map;

import static za.co.wstoop.jatalog.Expr.expr;

public class LogicSolver {
    public static void main(String[] args) throws DatalogException {
        Jatalog jatalog = new Jatalog()
                .fact("node", "a")
                .fact("node", "b")
                .fact("node", "c")
                .fact("node", "d")
                .fact("node", "e")
                .fact("node", "f")
                .fact("node", "g")
                .fact("node", "h")
                .fact("edge", "a", "c", "ac")
                .fact("edge", "b", "c", "bc")
                .fact("label", "a", "\"Toos\"")
                .fact("label", "b", "\"Els\"")
                .fact("label", "c", "\"Vera\"")
                .fact("label", "ac", "\"dad\"")
                .fact("label", "bc", "\"mom\"")
                .rule(expr("edge_with_label", "X", "Y", "S"), expr("edge", "X", "Y", "E"), expr("label", "E", "S"))
                .rule(expr("female", "X"), expr("edge_with_label", "X", "Y", "\"mom\""))
                .rule(expr("male", "X"), expr("edge_with_label", "X", "Y", "\"dad\""))
                .rule(expr("test", "A", "B", "C", "D", "E", "F", "G", "H"), expr("node", "A"), expr("node", "B"), expr("node", "C"), expr("node", "D"), expr("node", "E"), expr("node", "F"), expr("node", "G"), expr("node", "H"))
                .rule(expr("test2", "X"), expr("node", "X"), expr("test", "A", "B", "C", "D", "E", "F", "G", "H"));

        print(jatalog.query(expr("node", "X")));
        print(jatalog.query(expr("edge", "N1", "N2", "E")));
        print(jatalog.query(expr("label", "X", "S")));
        print(jatalog.query(expr("edge_with_label", "X", "Y", "S")));
        print(jatalog.query(expr("female", "X")));
        print(jatalog.query(expr("male", "X")));
//        print(jatalog.query(expr("test", "A", "B", "C", "D", "E", "F", "G", "H")));
//        print(jatalog.query(expr("test2", "X")));
    }

    private static void print(Collection<Map<String, String>> collection) {
        collection.stream().map(Map::entrySet).forEach(System.out::print);
        System.out.println();
    }
}
