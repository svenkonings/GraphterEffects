package compiler.general;

import compiler.graphloader.Importer;
import compiler.solver.Solver;
import compiler.solver.SvgGenerator;
import compiler.solver.Util;
import compiler.solver.VisElem;
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

        Jatalog jatalog = generateGraphJatalog(graph);

                //Display nodes as circles
        jatalog.rule(expr("shape", "N", "circle"), expr("node", "N"))

                //If possible, display arrows// FIX edge target/source
                .rule(expr("arrow", "T","S","E"), expr("edge","T","S","E"))

                //If that isn't possible, display edges as lines
                //.rule(expr("line", "E"), expr("edge","E"))

                //Display node id as a label of the shape.
                .rule(expr("text", "L"), expr("node", "N"), expr("attribute","label","N","L"))

                //Display the weight of the edges as label of the lines
                .rule(expr("text", "L"), expr("edge", "T","S","E"), expr("attribute","label","E","L"));


        Solver solver = new Solver(jatalog);

        List<VisElem> visElemList = solver.solve();
        Document svg = SvgGenerator.generate(visElemList);

        Util.writeDocument(svg, "demo1.svg");
    }

    @Test
    public void demo2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("demo/demo2.dot"));
        //Printer.pprint(graph);

        Jatalog jatalog = generateGraphJatalog(graph);

        //Display nodes as circles
        jatalog.rule(expr("shape", "N", "circle"), expr("node", "N"))

                //If that isn't possible, display edges as lines
                .rule(expr("line", "T", "S"), expr("edge","T","S","E"))

                //Display node id as a label of the shape.
                .rule(expr("text", "L"), expr("node", "N"), expr("attribute","label","N","L"))

                //Display the weight of the edges as label of the lines
                .rule(expr("text", "L"), expr("edge", "T","S","E"), expr("attribute","label","E","L"))

                //Colour the nodes depending on the number of neighbours
                //Yellow = 1
                .rule(expr("colour", "N", "yellow"), expr("neighbourcount","N","1"))

                //Orange = 2
                .rule(expr("colour","N", "orange"), expr("neighbourcount","N","2"))

                //Red = 3
                .rule(expr("colour","N", "red"), expr("neighbourcount","N","3"))

                //Dark red= 4
                .rule(expr("colour","N", "darkred"), expr("neighbourcount","N","4"));

        Solver solver = new Solver(jatalog);

        List<VisElem> visElemList = solver.solve();
        Document svg = SvgGenerator.generate(visElemList);

        Util.writeDocument(svg, "demo2.svg");
    }

    @Test
    public void demo3() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("demo/demo3.dot"));
        //Printer.pprint(graph);

        Jatalog jatalog = generateGraphJatalog(graph);

        //Display nodes as circles
        jatalog.rule(expr("shape", "N", "circle"), expr("node", "N"))

                //Display the edges as lines
                .rule(expr("line", "T", "S"), expr("edge","T","S","E"))

                //Display all the nodes with a red border
                .rule(expr("border-colour","N","red"), expr("node","N"))

                //colour all the edges which are in the mst in red
                .rule(expr("colour","E"), expr("edge","T","S","E"), expr("inmst","E"));

        Solver solver = new Solver(jatalog);

        List<VisElem> visElemList = solver.solve();
        Document svg = SvgGenerator.generate(visElemList);

        Util.writeDocument(svg, "demo3.svg");
    }

    @Test
    public void demo4() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("demo/start.gst"));
        //Printer.pprint(graph);

        Jatalog jatalog = generateGraphJatalog(graph);

        //Shows the wolf image
        jatalog.rule(expr("image", "N","demo/images/wolf.png"), expr("attribute","label","N","wolf"));
        jatalog.rule(expr("xpos", "N","50"), expr("attribute","label","N","wolf"));
        jatalog.rule(expr("ypos", "N","85"), expr("attribute","label","N","wolf"));

        //Shows the sheep image
        jatalog.rule(expr("image", "N","demo/images/sheep.png"), expr("attribute","label","N","type:Goat"));
        jatalog.rule(expr("xpos", "N","200"), expr("attribute","label","N","type:Goat"));
        jatalog.rule(expr("ypos", "N","45"), expr("attribute","label","N","type:Goat"));

        //Shows the cabbage image
        jatalog.rule(expr("image", "N","demo/images/cabbage.png"), expr("attribute","label","N","type:Cabbage"));
        jatalog.rule(expr("xpos", "N","350"), expr("attribute","label","N","type:Cabbage"));
        jatalog.rule(expr("ypos", "N","5"), expr("attribute","label","N","type:Cabbage"));

        //Shows the boat image
        jatalog.rule(expr("image", "N","demo/images/boat.png"), expr("attribute","label","N","type:Boat"));
        jatalog.rule(expr("xpos", "N","400"), expr("attribute","label","N","type:Boat"));
        jatalog.rule(expr("ypos", "N","220"), expr("attribute","label","N","type:Boat"));

        //Shows the river image
        jatalog.fact(expr("background-image","demo/images/river.png"));

        Solver solver = new Solver(jatalog);

        List<VisElem> visElemList = solver.solve();
        Document svg = SvgGenerator.generate(visElemList);

        Util.writeDocument(svg, "demo4.svg");

    }
}