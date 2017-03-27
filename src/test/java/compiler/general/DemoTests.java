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
import utils.Printer;
import za.co.wstoop.jatalog.Jatalog;

import java.util.List;

import static compiler.asrc.GraphRuleTests.generateGraphJatalog;
import static za.co.wstoop.jatalog.Expr.expr;

public final class DemoTests {

    @Test
    public void demo1() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph3.dot"));
        Printer.pprint(graph);

        Jatalog jatalog = generateGraphJatalog(graph);

                //Display nodes as circles
        jatalog.rule(expr("shape", "N", "circle"), expr("node", "N"))

                //If possible, display arrows// FIX edge target/source
                .rule(expr("arrow", "E","T","S"), expr("edge","E","T","S"))

                //If that isn't possible, display edges as lines
                //.rule(expr("line", "E"), expr("edge","E"))

                //Display node id as a label of the shape.
                .rule(expr("text", "L"), expr("node", "N"), expr("attribute","label","N","L"))

                //Display the weight of the edges as label of the lines
                .rule(expr("text", "L"), expr("edge", "E","T","S"), expr("attribute","label","E","L"));


        Solver solver = new Solver(jatalog);

        List<VisElem> visElemList = solver.solve();
        Document svg = SvgGenerator.generate(visElemList);

        Util.writeDocument(svg, "demo1.svg");
    }

    @Test
    public void demo2() throws Exception {
        Graph graph = Importer.graphFromFile(FileUtils.fromResources("asrc_testgraphs/graph3.dot"));
        Printer.pprint(graph);

        Jatalog jatalog = generateGraphJatalog(graph);

        //Display nodes as circles
        jatalog.rule(expr("shape", "N", "circle"), expr("node", "N"))

                //If that isn't possible, display edges as lines
                .rule(expr("line", "E"), expr("edge","E","T","S"))

                //Display node id as a label of the shape.
                .rule(expr("text", "L"), expr("node", "N"), expr("attribute","label","N","L"))

                //Display the weight of the edges as label of the lines
                .rule(expr("text", "L"), expr("edge", "E","T","S"), expr("attribute","label","E","L"))

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



}