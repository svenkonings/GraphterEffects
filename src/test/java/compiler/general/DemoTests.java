package compiler.general;

import compiler.graphloader.Importer;
import compiler.solver.Solver;
import compiler.solver.VisElem;
import compiler.svg.SvgDocumentGenerator;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.junit.Test;
import utils.FileUtils;
import za.co.wstoop.jatalog.Jatalog;

import java.util.List;

import static compiler.asrc.GraphRuleTests.generateGraphJatalog;
import static za.co.wstoop.jatalog.Expr.expr;

public final class DemoTests {

    @Test
    public void demo1() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("demo/demo1.dot"));
        //Printer.pprint(graph);
        Jatalog jatalog = generateGraphJatalog(graph)

                //Display nodes as circles
                .rule(expr("_shape_1", "N", "ellipse"), expr("node", "N"))
                .rule(expr("_width_1", "N", "10"), expr("node", "N"))
                .rule(expr("_height_1", "N", "10"), expr("node", "N"))

                //If possible, display arrows// FIX edge target/source
                //.rule(expr("arrow", "T", "S", "E"), expr("edge", "T", "S", "E"))

                //If that isn't possible, display edges as lines
                .rule(expr("_line_2_1_1", "N1", "N2", "N1", "N2"), expr("edge", "N1", "N2", "E"))
                .rule(expr("_before_2_1", "N1", "N2", "N1"), expr("edge", "N1", "N2", "E"))
                .rule(expr("_before_2_1", "N1", "N2", "N2"), expr("edge", "N1", "N2", "E"))

                //Display node id as a label of the shape.
                .rule(expr("text", "L"), expr("node", "N"), expr("attribute", "label", "N", "L"))

                //Display the weight of the edges as label of the lines
                .rule(expr("text", "L"), expr("edge", "T", "S", "E"), expr("attribute", "label", "E", "L"));

        Solver solver = new Solver(jatalog);

        List<VisElem> visElemList = solver.solve();
        Document svg = SvgDocumentGenerator.generate(visElemList);

        SvgDocumentGenerator.writeDocument(svg, "demo1.svg");
    }

    @Test
    public void demo2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("demo/demo2.dot"));
        //Printer.pprint(graph);
        Jatalog jatalog = generateGraphJatalog(graph)

                //Display nodes as circles
                .rule(expr("_shape_1", "N", "ellipse"), expr("node", "N"))
                .rule(expr("_width_1", "N", "10"), expr("node", "N"))
                .rule(expr("_height_1", "N", "10"), expr("node", "N"))

                //If that isn't possible, display edges as lines
                .rule(expr("_line_2_1_1", "N1", "N2", "N1", "N2"), expr("edge", "N1", "N2", "E"))
                .rule(expr("_before_2_1", "N1", "N2", "N1"), expr("edge", "N1", "N2", "E"))
                .rule(expr("_before_2_1", "N1", "N2", "N2"), expr("edge", "N1", "N2", "E"))

                //Display node id as a label of the shape.
                .rule(expr("text", "L"), expr("node", "N"), expr("attribute", "label", "N", "L"))

                //Display the weight of the edges as label of the lines
                .rule(expr("text", "L"), expr("edge", "T", "S", "E"), expr("attribute", "label", "E", "L"))

                //Colour the nodes depending on the number of neighbours
                //Yellow = 1
                .rule(expr("colour", "N", "green"), expr("neighbourcount", "N", "1"))

                //Orange = 2
                .rule(expr("colour", "N", "yellow"), expr("neighbourcount", "N", "2"))

                //Red = 3
                .rule(expr("colour", "N", "orange"), expr("neighbourcount", "N", "3"))

                //Dark red= 4
                .rule(expr("colour", "N", "red"), expr("neighbourcount", "N", "4"));

        Solver solver = new Solver(jatalog);

        List<VisElem> visElemList = solver.solve();
        Document svg = SvgDocumentGenerator.generate(visElemList);

        SvgDocumentGenerator.writeDocument(svg, "demo2.svg");
    }

    @Test
    public void demo3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("demo/demo3.dot"));
        //Printer.pprint(graph);
        Jatalog jatalog = generateGraphJatalog(graph)

                //Display nodes as circles
                .rule(expr("_shape_1", "N", "ellipse"), expr("node", "N"))
                .rule(expr("_width_1", "N", "10"), expr("node", "N"))
                .rule(expr("_height_1", "N", "10"), expr("node", "N"))

                //If that isn't possible, display edges as lines
                .rule(expr("_line_2_1_1", "N1", "N2", "N1", "N2"), expr("edge", "N1", "N2", "E"))
                .rule(expr("_before_2_1", "N1", "N2", "N1"), expr("edge", "N1", "N2", "E"))
                .rule(expr("_before_2_1", "N1", "N2", "N2"), expr("edge", "N1", "N2", "E"))

                //Display all the nodes with a red border
                .rule(expr("border-colour", "N", "red"), expr("node", "N"))

                //colour all the edges which are in the mst in red
                .rule(expr("_border-colour_2", "N1", "N2", "red"), expr("inmst", "N1", "N2", "E"));

        Solver solver = new Solver(jatalog);

        List<VisElem> visElemList = solver.solve();
        Document svg = SvgDocumentGenerator.generate(visElemList);

        SvgDocumentGenerator.writeDocument(svg, "demo3.svg");
    }

    @Test
    public void demo4() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("demo/start.gst"));
        //Printer.pprint(graph);
        Jatalog jatalog = generateGraphJatalog(graph);

        //Shows the wolf image
        jatalog.rule(expr("image", "N", "demo/images/wolf.png"), expr("attribute", "label", "N", "wolf"));
        jatalog.rule(expr("posX", "N", "50"), expr("attribute", "label", "N", "wolf"));
        jatalog.rule(expr("posY", "N", "85"), expr("attribute", "label", "N", "wolf"));

        //Shows the sheep image
        jatalog.rule(expr("image", "N", "demo/images/sheep.png"), expr("attribute", "label", "N", "type:Goat"));
        jatalog.rule(expr("posX", "N", "200"), expr("attribute", "label", "N", "\"type:Goat\""));
        jatalog.rule(expr("posY", "N", "45"), expr("attribute", "label", "N", "type:Goat"));

        //Shows the cabbage image
        jatalog.rule(expr("image", "N", "demo/images/cabbage.png"), expr("attribute", "label", "N", "type:Cabbage"));
        jatalog.rule(expr("posX", "N", "350"), expr("attribute", "label", "N", "type:Cabbage"));
        jatalog.rule(expr("posY", "N", "5"), expr("attribute", "label", "N", "type:Cabbage"));

        //Shows the boat image
        jatalog.rule(expr("image", "N", "demo/images/boat.png"), expr("attribute", "label", "N", "type:Boat"));
        jatalog.rule(expr("posX", "N", "400"), expr("attribute", "label", "N", "type:Boat"));
        jatalog.rule(expr("posY", "N", "220"), expr("attribute", "label", "N", "type:Boat"));

        //Shows the river image
        jatalog.fact(expr("background-image", "demo/images/river.png"));

        Solver solver = new Solver(jatalog);

        List<VisElem> visElemList = solver.solve();
        Document svg = SvgDocumentGenerator.generate(visElemList);

        SvgDocumentGenerator.writeDocument(svg, "demo4.svg");
    }
}
