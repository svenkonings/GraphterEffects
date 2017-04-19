import alice.tuprolog.InvalidLibraryException;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import asrc.ASRCLibrary;
import graphloader.Importer;
import prolog.TuProlog;
import solver.SolveException;
import solver.Solver;
import solver.VisMap;
import svg.SvgDocumentGenerator;
import exceptions.UnknownGraphTypeException;
import graafvis.GraafvisCompiler;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;
import utils.Printer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, UnknownGraphTypeException, InvalidTheoryException, InvalidLibraryException, GraafvisCompiler.SyntaxException, GraafvisCompiler.CheckerException, SolveException {
        assert args.length == 3;
        Graph graph = Importer.graphFromFile(args[0]);
        Printer.pprint(graph);
        List<Term> terms = new GraafvisCompiler().compile(FileUtils.readFromFile(new File(args[1])));
        System.out.println();
        terms.forEach(System.out::println);
        System.out.println();
        Solver solver = new Solver();
        VisMap visMap = solver.solve(graph, terms);
        visMap.getModel().getSolver().printStatistics();
        Document document = SvgDocumentGenerator.generate(visMap.values());
        SvgDocumentGenerator.writeDocument(document, args[2]);
    }
}
