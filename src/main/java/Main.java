import alice.tuprolog.InvalidLibraryException;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.asrc.ASRCLibrary;
import compiler.graphloader.Importer;
import compiler.prolog.TuProlog;
import compiler.solver.Solver;
import compiler.solver.VisElem;
import compiler.solver.VisMap;
import compiler.svg.SvgDocumentGenerator;
import exceptions.UnknownGraphTypeException;
import graafvis.RuleGenerator;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;
import utils.Printer;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, UnknownGraphTypeException, InvalidTheoryException, InvalidLibraryException {
        assert args.length == 3;
        Graph graph = Importer.graphFromFile(args[0], true);
        Printer.pprint(graph);
        List<Term> terms = RuleGenerator.generate(FileUtils.readFromFile(new File(args[1])));
        System.out.println();
        terms.forEach(System.out::println);
        System.out.println();
        TuProlog prolog = new TuProlog(terms);
        Solver solver = new Solver();
        prolog.loadLibrary(new ASRCLibrary(graph));
        VisMap visMap = solver.solve(prolog);
        Document document = SvgDocumentGenerator.generate(visMap.values());
        SvgDocumentGenerator.writeDocument(document, args[2]);
    }
}
