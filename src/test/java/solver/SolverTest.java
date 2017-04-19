package solver;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import prolog.TuProlog;
import svg.SvgDocumentGenerator;
import org.dom4j.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static prolog.TuProlog.*;

public class SolverTest {
    public static void main(String[] args) throws InvalidTheoryException, IOException {
        Solver solver = new Solver();
        TuProlog prolog = new TuProlog(testData());
        VisMap visMap = solver.solve(prolog);
        Document document = SvgDocumentGenerator.generate(visMap.values());
        SvgDocumentGenerator.writeDocument(document, "test.svg");
    }

    private static List<Term> testData() {
        List<Term> result = new ArrayList<>();
        result.add(struct("node", term("a")));
        result.add(struct("node", term("b")));
        result.add(struct("node", term("c")));
        result.add(struct("edge", term("ac")));
        result.add(struct("edge", term("bc")));
        result.add(struct("edge", term("a"), term("c")));
        result.add(struct("edge", term("a"), term("c")));
        result.add(struct("edge", term("a"), term("c"), term("ac")));
        result.add(struct("edge", term("b"), term("c"), term("bc")));
        result.add(struct("label", term("a"), term("\"Toos\"")));
        result.add(struct("label", term("b"), term("\"Els\"")));
        result.add(struct("label", term("c"), term("\"Vera\"")));
        result.add(struct("label", term("ac"), term("\"dad\"")));
        result.add(struct("label", term("bc"), term("\"mom\"")));
        result.add(clause(struct("edge_with_label", var("X"), var("Y"), var("S")), and(
                struct("edge", var("X"), var("Y"), var("E")),
                struct("label", var("E"), var("S"))
        )));
        result.add(clause(struct("female", var("X")), struct("edge_with_label", var("X"), var(), term("\"mom\""))));
        result.add(clause(struct("male", var("X")), struct("edge_with_label", var("X"), var(), term("\"dad\""))));
        result.add(clause(struct("shape", var("X"), term("rectangle")), struct("female", var("X"))));
        result.add(clause(struct("colour", var("X"), term("green")), struct("female", var("X"))));
        result.add(clause(struct("dimensions", var("X"), intVal(60), intVal(30)), struct("female", var("X"))));
        result.add(clause(struct("left", var("X"), var("Y"), intVal(60)), and(
                struct("female", var("X")),
                struct("male", var("Y"))
        )));
        result.add(clause(struct("shape", var("X"), term("ellipse")), struct("male", var("X"))));
        result.add(clause(struct("colour", var("X"), term("red")), struct("male", var("X"))));
        result.add(clause(struct("pos", var("X"), intVal(120), intVal(30), intVal(1)), struct("male", var("X"))));
        result.add(clause(struct("dimensions", var("X"), intVal(60), intVal(30)), struct("male", var("X"))));
        return result;
    }
}
