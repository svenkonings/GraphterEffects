import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.asrc.AbstractSyntaxRuleConverter;
import compiler.graphloader.Importer;
import compiler.solver.Solver;
import compiler.solver.VisElem;
import compiler.svg.SvgDocumentGenerator;
import compiler.asrc.UnknownGraphTypeException;
import graafvis.RuleGenerator;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;
import utils.Printer;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, SAXException, UnknownGraphTypeException, InvalidTheoryException {
        assert args.length == 3;
        Graph graph = Importer.graphFromFile(args[0]);
        Printer.pprint(graph);
        List<Term> terms = AbstractSyntaxRuleConverter.convertToRules(graph);
        terms.addAll(RuleGenerator.generate(FileUtils.readLines(args[1])));
        System.out.println();
        terms.forEach(System.out::println);
        System.out.println();
        Solver solver = new Solver(terms);
        List<VisElem> visElems = solver.solve();
        Document document = SvgDocumentGenerator.generate(visElems);
        SvgDocumentGenerator.writeDocument(document, args[2]);
    }
}
