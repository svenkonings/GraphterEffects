package general;

import alice.tuprolog.InvalidLibraryException;
import alice.tuprolog.InvalidTheoryException;
import alice.tuprolog.Term;
import compiler.asrc.ASRCLibrary;
import compiler.graphloader.Importer;
import compiler.solver.Solver;
import compiler.solver.VisElem;
import compiler.svg.SvgDocumentGenerator;
import exceptions.UnknownGraphTypeException;
import general.files.DocumentModel;
import graafvis.RuleGenerator;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CompilerUtils {

    public static void compile(Path scriptFile, Path graphFile) throws IOException, SAXException, UnknownGraphTypeException, InvalidTheoryException, InvalidLibraryException {
        //Compiles the scriptFile and graphFile to an SVG
        Graph graph = Importer.graphFromFile(graphFile.toFile());
        List<Term> terms = RuleGenerator.generate(FileUtils.readFromFile(scriptFile.toFile()));
        System.out.println();
        terms.forEach(System.out::println);
        System.out.println();
        Solver solver = new Solver(terms);
        solver.addLibrary(new ASRCLibrary(graph));
        List<VisElem> visElems = solver.solve();
        Document document = SvgDocumentGenerator.generate(visElems);

        //Sets the name of this SVG to the name of the dot.
        String svgFileName = graphFile.getFileName().toString().split("\\.")[0];
        int counter = DocumentModel.getInstance().generateSVGCounter(svgFileName);
        if (counter != 0){
            svgFileName += "(" + counter + ")";
        }
        document.setName(svgFileName);

        String svgxml = document.asXML();
        List<String> svgxmltext = new ArrayList<>();
        svgxmltext.add(svgxml);

        new File("temp/compiled/").mkdirs();

        Path file = Paths.get("temp/compiled/",document.getName() + ".svg");
        try {
            Files.write(file, svgxmltext, Charset.forName("UTF-8"));
            DocumentModel.getInstance().addGeneratedSVG(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
