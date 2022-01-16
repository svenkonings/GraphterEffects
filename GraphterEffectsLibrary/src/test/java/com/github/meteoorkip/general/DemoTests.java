package com.github.meteoorkip.general;

import com.github.meteoorkip.graphloader.Importer;
import com.github.meteoorkip.solver.SolveResults;
import com.github.meteoorkip.solver.Solver;
import com.github.meteoorkip.svg.SvgDocumentGenerator;
import com.github.meteoorkip.utils.FileUtils;
import it.unibo.tuprolog.core.Clause;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static com.github.meteoorkip.prolog.TuProlog.*;

public final class DemoTests {
    @Test
    public void demo1() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("tool/demo1.dot"));
        List<Clause> terms = Arrays.asList(
                //Display nodes as circles
                clause(struct("shape", var("N"), atom("ellipse")), struct("node", var("N"))),
                clause(struct("noOverlap", var("N1"), var("N2")), and(struct("node", var("N1")), struct("node", var("N2")))),
                clause(struct("width", var("N"), intVal(10)), struct("node", var("N"))),
                clause(struct("height", var("N"), intVal(10)), struct("node", var("N"))),

                //If possible, display arrows// FIX edge target/source
                //clause(struct("arrow", "T", "S", "E"), struct("edge", "T", "S", "E"))

                //If that isn't possible, display edges as lines
                clause(struct("line", var("N1"), var("N2")), struct("edge", var("N1"), var("N2"), var())),
                clause(struct("behind", list(var("N1"), var("N2")), var("N1")), struct("edge", var("N1"), var("N2"), var())),
                clause(struct("behind", list(var("N1"), var("N2")), var("N2")), struct("edge", var("N1"), var("N2"), var())),

                //Display node id as a label of the shape.
                //Display the weight of the edges as label of the lines
                clause(struct("text", var("N"), var("L")), struct("attribute", atom("label"), var("X"), var("L")))
        );
        Solver solver = new Solver();
        SolveResults results = solver.solve(graph, terms);
        Document svg = SvgDocumentGenerator.generate(results.getVisMap().values());
        SvgDocumentGenerator.writeDocument(svg, "demo1.svg");
    }

    @Test
    public void demo2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("tool/demo2.dot"));
        List<Clause> terms = Arrays.asList(
                //Display nodes as circles
                clause(struct("shape", var("N"), atom("ellipse")), struct("node", var("N"))),
                clause(struct("noOverlap", var("N1"), var("N2")), and(struct("node", var("N1")), struct("node", var("N2")))),
                clause(struct("width", var("N"), intVal(10)), struct("node", var("N"))),
                clause(struct("height", var("N"), intVal(10)), struct("node", var("N"))),

                //If possible, display arrows// FIX edge target/source
                //clause(struct("arrow", "T", "S", "E"), struct("edge", "T", "S", "E"))

                //If that isn't possible, display edges as lines
                clause(struct("line", var("N1"), var("N2")), struct("edge", var("N1"), var("N2"), var())),
                clause(struct("behind", list(var("N1"), var("N2")), var("N1")), struct("edge", var("N1"), var("N2"), var())),
                clause(struct("behind", list(var("N1"), var("N2")), var("N2")), struct("edge", var("N1"), var("N2"), var())),

                //Display node id as a label of the shape.
                //Display the weight of the edges as label of the lines
                clause(struct("text", var("N"), var("L")), struct("attribute", atom("label"), var("X"), var("L"))),

                //Colour the nodes depending on the number of neighbours
                //Yellow = 1
                clause(struct("colour", var("N"), atom("green")), struct("neighbourcount", var("N"), intVal(1))),

                //Orange = 2
                clause(struct("colour", var("N"), atom("yellow")), struct("neighbourcount", var("N"), intVal(2))),

                //Red = 3
                clause(struct("colour", var("N"), atom("orange")), struct("neighbourcount", var("N"), intVal(3))),

                //Dark red= 4
                clause(struct("colour", var("N"), atom("red")), struct("neighbourcount", var("N"), intVal(4)))
        );
        Solver solver = new Solver();
        SolveResults results = solver.solve(graph, terms);
        Document svg = SvgDocumentGenerator.generate(results.getVisMap().values());
        SvgDocumentGenerator.writeDocument(svg, "demo2.svg");
    }

    @Test
    public void demo3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("tool/demo3.dot"));
        List<Clause> terms = Arrays.asList(
                //Display nodes as circles
                clause(struct("shape", var("N"), atom("ellipse")), struct("node", var("N"))),
                clause(struct("noOverlap", var("N1"), var("N2")), and(struct("node", var("N1")), struct("node", var("N2")))),
                clause(struct("width", var("N"), intVal(10)), struct("node", var("N"))),
                clause(struct("height", var("N"), intVal(10)), struct("node", var("N"))),

                //If possible, display arrows// FIX edge target/source
                //clause(struct("arrow", "T", "S", "E"), struct("edge", "T", "S", "E"))

                //If that isn't possible, display edges as lines
                clause(struct("line", var("N1"), var("N2")), struct("edge", var("N1"), var("N2"), var())),
                clause(struct("behind", list(var("N1"), var("N2")), var("N1")), struct("edge", var("N1"), var("N2"), var())),
                clause(struct("behind", list(var("N1"), var("N2")), var("N2")), struct("edge", var("N1"), var("N2"), var())),

                //Display all the nodes with a red border
                clause(struct("stroke", var("N"), atom("red")), struct("node", var("N"))),

                //colour all the edges which are in the mst in red
                clause(struct("stroke", list(var("N1"), var("N2")), atom("red")), and(struct("inmst", var("E")), struct("edge", var("N1"), var("N2"), var("E"))))
        );
        Solver solver = new Solver();
        SolveResults results = solver.solve(graph, terms);
        Document svg = SvgDocumentGenerator.generate(results.getVisMap().values());
        SvgDocumentGenerator.writeDocument(svg, "demo3.svg");
    }

//    @Test
//    public void demo4() throws Exception {
//        Graph graph = Importer.graphFromFile(FileUtils.fromResources("tool/start.gst"));
//        Printer.pprint(graph);
//        List<Term> terms = Arrays.asList(
//                //Shows the wolf image
//                clause(struct("image", var("N"), struct(FileUtils.fromResources("demo/images/wolf.png").getAbsolutePath())), struct("attribute", term("label"), var("N"), term("wolf"))),
//                clause(struct("pos", var("N"), intVal(50), intVal(85)), struct("attribute", term("label"), var("N"), term("wolf"))),
//                clause(struct("dimensions", var("N"), intVal(128), intVal(128)), struct("attribute", term("label"), var("N"), term("wolf"))),
//
//                //Shows the sheep image
//                clause(struct("image", var("N"), struct(FileUtils.fromResources("demo/images/sheep.png").getAbsolutePath())), struct("attribute", term("label"), var("N"), term("type:Goat"))),
//                clause(struct("pos", var("N"), intVal(200), intVal(45)), struct("attribute", term("label"), var("N"), term("type:Goat"))),
//                clause(struct("dimensions", var("N"), intVal(128), intVal(128)), struct("attribute", term("label"), var("N"), term("type:Goat"))),
//
//                //Shows the cabbage image
//                clause(struct("image", var("N"), struct(FileUtils.fromResources("demo/images/cabbage.png").getAbsolutePath())), struct("attribute", term("label"), var("N"), term("type:Cabbage"))),
//                clause(struct("pos", var("N"), intVal(350), intVal(5)), struct("attribute", term("label"), var("N"), term("type:Cabbage"))),
//                clause(struct("dimensions", var("N"), intVal(128), intVal(128)), struct("attribute", term("label"), var("N"), term("type:Cabbage"))),
//
//                //Shows the boat image
//                clause(struct("image", var("N"), struct(FileUtils.fromResources("demo/images/boat.png").getAbsolutePath())), struct("attribute", term("label"), var("N"), term("type:Boat"))),
//                clause(struct("pos", var("N"), intVal(400), intVal(220)), struct("attribute", term("label"), var("N"), term("type:Boat"))),
//                clause(struct("dimensions", var("N"), intVal(450), intVal(190)), struct("attribute", term("label"), var("N"), term("type:Boat"))),
//
//                //Shows the river image
//                struct("background-image", struct(FileUtils.fromResources("demo/images/river.png").getAbsolutePath()))
//        );
//        Solver solver = new Solver();
//        SolveResults results = solver.solve(graph, terms);
//        Document svg = SvgDocumentGenerator.generate(results.getVisMap().values());
//        SvgDocumentGenerator.writeDocument(svg, "demo4.svg");
//    }
}
