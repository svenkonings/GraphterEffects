import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import graafvis.GraafvisCompiler;
import graphloader.Importer;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import solver.SolveResults;
import solver.Solver;
import svg.SvgDocumentGenerator;
import utils.FileUtils;
import utils.Printer;

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
        Document document = SvgDocumentGenerator.generate(results.getVisMap().values());
        SvgDocumentGenerator.writeDocument(document, args[2]);
    }
}
