package com.github.meteoorkip.solver;


import com.github.meteoorkip.prolog.PrologException;
import com.github.meteoorkip.svg.SvgDocumentGenerator;
import it.unibo.tuprolog.core.Clause;
import org.dom4j.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.meteoorkip.prolog.TuProlog.*;

public class SolverTest {
    public static void main(String[] args) throws IOException, PrologException {
        Solver solver = new Solver();
        SolveResults results = solver.solve(testData());
        Document document = SvgDocumentGenerator.generate(results.getVisMap().values());
        SvgDocumentGenerator.writeDocument(document, "test.svg");
    }

    private static List<Clause> testData() {
        List<Clause> result = new ArrayList<>();
        result.add(fact(struct("node", atom("a"))));
        result.add(fact(struct("node", atom("b"))));
        result.add(fact(struct("node", atom("c"))));
        result.add(fact(struct("edge", atom("ac"))));
        result.add(fact(struct("edge", atom("bc"))));
        result.add(fact(struct("edge", atom("a"), atom("c"))));
        result.add(fact(struct("edge", atom("a"), atom("c"))));
        result.add(fact(struct("edge", atom("a"), atom("c"), atom("ac"))));
        result.add(fact(struct("edge", atom("b"), atom("c"), atom("bc"))));
        result.add(fact(struct("label", atom("a"), atom("\"Toos\""))));
        result.add(fact(struct("label", atom("b"), atom("\"Els\""))));
        result.add(fact(struct("label", atom("c"), atom("\"Vera\""))));
        result.add(fact(struct("label", atom("ac"), atom("\"dad\""))));
        result.add(fact(struct("label", atom("bc"), atom("\"mom\""))));
        result.add(clause(struct("edge_with_label", var("X"), var("Y"), var("S")), and(
                struct("edge", var("X"), var("Y"), var("E")),
                struct("label", var("E"), var("S"))
        )));
        result.add(clause(struct("female", var("X")), struct("edge_with_label", var("X"), var(), atom("\"mom\""))));
        result.add(clause(struct("male", var("X")), struct("edge_with_label", var("X"), var(), atom("\"dad\""))));
        result.add(clause(struct("shape", var("X"), atom("rectangle")), struct("female", var("X"))));
        result.add(clause(struct("colour", var("X"), atom("green")), struct("female", var("X"))));
        result.add(clause(struct("dimensions", var("X"), intVal(60), intVal(30)), struct("female", var("X"))));
        result.add(clause(struct("left", var("X"), var("Y"), intVal(60)), and(
                struct("female", var("X")),
                struct("male", var("Y"))
        )));
        result.add(clause(struct("shape", var("X"), atom("ellipse")), struct("male", var("X"))));
        result.add(clause(struct("colour", var("X"), atom("red")), struct("male", var("X"))));
        result.add(clause(struct("pos", var("X"), intVal(120), intVal(30), intVal(1)), struct("male", var("X"))));
        result.add(clause(struct("dimensions", var("X"), intVal(60), intVal(30)), struct("male", var("X"))));
        return result;
    }
}
