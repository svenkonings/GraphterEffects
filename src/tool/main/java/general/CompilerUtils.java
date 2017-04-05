package general;

import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.asrc.AbstractSyntaxRuleConverter;
import compiler.graphloader.Importer;
import compiler.solver.Solver;
import compiler.solver.VisElem;
import compiler.svg.SvgDocumentGenerator;
import exceptions.UnknownGraphTypeException;
import graafvis.RuleGenerator;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class CompilerUtils {

    public static Document compile(File scriptFile, File graphFile) throws IOException, SAXException, UnknownGraphTypeException, InvalidTheoryException {
        Graph graph = Importer.graphFromFile(graphFile);
        List<Term> terms = AbstractSyntaxRuleConverter.convertToRules(graph);
        terms.addAll(RuleGenerator.generate(FileUtils.readFromFile(scriptFile)));
        System.out.println();
        terms.forEach(System.out::println);
        System.out.println();
        Solver solver = new Solver(terms);
        List<VisElem> visElems = solver.solve();
        return SvgDocumentGenerator.generate(visElems);
    }
}
