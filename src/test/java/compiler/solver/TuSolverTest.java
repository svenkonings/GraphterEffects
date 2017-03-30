package compiler.solver;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.svg.SvgDocumentGenerator;
import org.dom4j.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static compiler.prolog.TuProlog.*;

public class TuSolverTest {
    public static void main(String[] args) throws InvalidTheoryException, IOException {
        Solver solver = new Solver(testData());
        List<VisElem> visElems = solver.solve();
        Document document = SvgDocumentGenerator.generate(visElems);
        SvgDocumentGenerator.writeDocument(document, "test.svg");
    }

    private static List<Term> testData() {
        List<Term> result = new ArrayList<>();
        result.add(struct("node", struct("a")));
        result.add(struct("node", struct("b")));
        result.add(struct("node", struct("c")));
        result.add(struct("edge", struct("ac")));
        result.add(struct("edge", struct("bc")));
        result.add(struct("edge", struct("a"), struct("c")));
        result.add(struct("edge", struct("a"), struct("c")));
        result.add(struct("edge", struct("a"), struct("c"), struct("ac")));
        result.add(struct("edge", struct("b"), struct("c"), struct("bc")));
        result.add(struct("label", struct("a"), struct("\"Toos\"")));
        result.add(struct("label", struct("b"), struct("\"Els\"")));
        result.add(struct("label", struct("c"), struct("\"Vera\"")));
        result.add(struct("label", struct("ac"), struct("\"dad\"")));
        result.add(struct("label", struct("bc"), struct("\"mom\"")));
        result.add(clause(struct("edge_with_label", var("X"), var("Y"), var("S")), and(
                struct("edge", var("X"), var("Y"), var("E")),
                struct("label", var("E"), var("S"))
        )));
        result.add(clause(struct("female", var("X")), struct("edge_with_label", var("X"), var(), struct("\"mom\""))));
        result.add(clause(struct("male", var("X")), struct("edge_with_label", var("X"), var(), struct("\"dad\""))));
        result.add(clause(struct("shape", var("X"), struct("rectangle")), struct("female", var("X"))));
        result.add(clause(struct("colour", var("X"), struct("green")), struct("female", var("X"))));
        result.add(clause(struct("dimensions", var("X"), intVal(60), intVal(30)), struct("female", var("X"))));
        result.add(clause(struct("left", var("X"), var("Y"), intVal(60)), and(
                struct("female", var("X")),
                struct("male", var("Y"))
        )));
        result.add(clause(struct("shape", var("X"), struct("ellipse")), struct("male", var("X"))));
        result.add(clause(struct("colour", var("X"), struct("red")), struct("male", var("X"))));
        result.add(clause(struct("pos", var("X"), intVal(60), intVal(30), intVal(1)), struct("male", var("X"))));
        result.add(clause(struct("dimensions", var("X"), intVal(60), intVal(30)), struct("male", var("X"))));
        return result;
    }
}
