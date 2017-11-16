package com.github.meteoorkip;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.graphloader.Importer;
import com.github.meteoorkip.solver.ElementException;
import com.github.meteoorkip.solver.SolveResults;
import com.github.meteoorkip.solver.Solver;
import com.github.meteoorkip.svg.SvgDocumentGenerator;
import com.github.meteoorkip.utils.FileUtils;
import com.github.meteoorkip.utils.Printer;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class GraphterEffects {
    public static void main(String[] args) throws IOException, SAXException, GraafvisCompiler.SyntaxException, GraafvisCompiler.CheckerException, InvalidTheoryException {
        if (args.length != 3) {
            System.out.println("Usage: <graph path> <script path> <output path>");
            return;
        }
        Graph graph = Importer.graphFromFile(args[0]);
        Printer.pprint(graph);
        List<Term> terms = new GraafvisCompiler().compile(FileUtils.readFromFile(new File(args[1])));
        System.out.println();
        terms.forEach(System.out::println);
        System.out.println();
        Solver solver = new Solver();
        SolveResults results = solver.solve(graph, terms);
        results.getModel().getSolver().printStatistics();
        if (!results.isSucces()) {
            throw new ElementException("Couldn't solve constraints");
        }
        Document document = SvgDocumentGenerator.generate(results.getVisMap().values());
        SvgDocumentGenerator.writeDocument(document, args[2]);
    }
}
