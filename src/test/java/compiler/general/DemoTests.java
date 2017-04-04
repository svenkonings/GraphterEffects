package compiler.general;

import compiler.graphloader.Importer;
import compiler.prolog.TuProlog;
import compiler.solver.Solver;
import compiler.solver.VisElem;
import compiler.svg.SvgDocumentGenerator;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.junit.Test;
import utils.FileUtils;

import java.util.List;

import static compiler.asrc.GraphRuleTests.generateGraphProlog;
import static compiler.prolog.TuProlog.*;

public final class DemoTests {
    @Test
    public void demo1() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("demo/demo1.dot"));
        //Printer.pprint(graph);
        TuProlog prolog = generateGraphProlog(graph);
        prolog.addTheory(
                //Display nodes as circles
                clause(struct("shape", var("N"), term("ellipse")), struct("node", var("N"))),
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
                clause(struct("text", var("N"), var("L")), struct("attribute", term("label"), var("X"), var("L")))
        );
        Solver solver = new Solver(prolog);
        List<VisElem> visElemList = solver.solve();
        Document svg = SvgDocumentGenerator.generate(visElemList);
        SvgDocumentGenerator.writeDocument(svg, "demo1.svg");
    }

    @Test
    public void demo2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("demo/demo2.dot"));
        //Printer.pprint(graph);
        TuProlog prolog = generateGraphProlog(graph);
        prolog.addTheory(
                //Display nodes as circles
                clause(struct("shape", var("N"), term("ellipse")), struct("node", var("N"))),
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
                clause(struct("text", var("N"), var("L")), struct("attribute", term("label"), var("X"), var("L"))),

                //Colour the nodes depending on the number of neighbours
                //Yellow = 1
                clause(struct("colour", var("N"), term("green")), struct("neighbourcount", var("N"), intVal(1))),

                //Orange = 2
                clause(struct("colour", var("N"), term("yellow")), struct("neighbourcount", var("N"), intVal(2))),

                //Red = 3
                clause(struct("colour", var("N"), term("orange")), struct("neighbourcount", var("N"), intVal(3))),

                //Dark red= 4
                clause(struct("colour", var("N"), term("red")), struct("neighbourcount", var("N"), intVal(4)))
        );
        Solver solver = new Solver(prolog);
        List<VisElem> visElemList = solver.solve();
        Document svg = SvgDocumentGenerator.generate(visElemList);
        SvgDocumentGenerator.writeDocument(svg, "demo2.svg");
    }

    @Test
    public void demo3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("demo/demo3.dot"));
        //Printer.pprint(graph);
        TuProlog prolog = generateGraphProlog(graph);
        prolog.addTheory(
                //Display nodes as circles
                clause(struct("shape", var("N"), term("ellipse")), struct("node", var("N"))),
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
                clause(struct("stroke", var("N"), term("red")), struct("node", var("N"))),

                //colour all the edges which are in the mst in red
                clause(struct("stroke", list(var("N1"), var("N2")), term("red")), and(struct("inmst", var("E")), struct("edge", var("N1"), var("N2"), var("E"))))
        );
        Solver solver = new Solver(prolog);
        List<VisElem> visElemList = solver.solve();
        Document svg = SvgDocumentGenerator.generate(visElemList);
        SvgDocumentGenerator.writeDocument(svg, "demo3.svg");
    }

    @Test
    public void demo4() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("demo/start.gst"));
        //Printer.pprint(graph);
        TuProlog prolog = generateGraphProlog(graph);
        prolog.addTheory(
                //Shows the wolf image
                clause(struct("image", var("N"), struct(FileUtils.fromResources("demo/images/wolf.png").getAbsolutePath())), struct("attribute", term("label"), var("N"), term("wolf"))),
                clause(struct("pos", var("N"), intVal(50), intVal(85)), struct("attribute", term("label"), var("N"), term("wolf"))),
                clause(struct("dimensions", var("N"), intVal(128), intVal(128)), struct("attribute", term("label"), var("N"), term("wolf"))),

                //Shows the sheep image
                clause(struct("image", var("N"), struct(FileUtils.fromResources("demo/images/sheep.png").getAbsolutePath())), struct("attribute", term("label"), var("N"), term("type:Goat"))),
                clause(struct("pos", var("N"), intVal(200), intVal(45)), struct("attribute", term("label"), var("N"), term("type:Goat"))),
                clause(struct("dimensions", var("N"), intVal(128), intVal(128)), struct("attribute", term("label"), var("N"), term("type:Goat"))),

                //Shows the cabbage image
                clause(struct("image", var("N"), struct(FileUtils.fromResources("demo/images/cabbage.png").getAbsolutePath())), struct("attribute", term("label"), var("N"), term("type:Cabbage"))),
                clause(struct("pos", var("N"), intVal(350), intVal(5)), struct("attribute", term("label"), var("N"), term("type:Cabbage"))),
                clause(struct("dimensions", var("N"), intVal(128), intVal(128)), struct("attribute", term("label"), var("N"), term("type:Cabbage"))),

                //Shows the boat image
                clause(struct("image", var("N"), struct(FileUtils.fromResources("demo/images/boat.png").getAbsolutePath())), struct("attribute", term("label"), var("N"), term("type:Boat"))),
                clause(struct("pos", var("N"), intVal(400), intVal(220)), struct("attribute", term("label"), var("N"), term("type:Boat"))),
                clause(struct("dimensions", var("N"), intVal(450), intVal(190)), struct("attribute", term("label"), var("N"), term("type:Boat"))),

                //Shows the river image
                struct("background-image", struct(FileUtils.fromResources("demo/images/river.png").getAbsolutePath()))
        );
        Solver solver = new Solver(prolog);
        List<VisElem> visElemList = solver.solve();
        Document svg = SvgDocumentGenerator.generate(visElemList);
        SvgDocumentGenerator.writeDocument(svg, "demo4.svg");
    }
}
