package com.github.meteoorkip.system;

import alice.tuprolog.Term;
import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.graphloader.Importer;
import com.github.meteoorkip.solver.SolveResults;
import com.github.meteoorkip.solver.Solver;
import com.github.meteoorkip.svg.SvgDocumentGenerator;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

/**
 * Created by Hans on 16-11-2017.
 */
public class TestHelper {

    public TestHelper() {

    }

    public String compileFile(String scriptName, String graphFileName) throws IOException, GraafvisCompiler.SyntaxException, GraafvisCompiler.CheckerException, SAXException {
        SolveResults results = solve(scriptName, graphFileName);
        String svgString = null;
        if(results.isSucces()) {
            Document document = SvgDocumentGenerator.generate(results.getVisMap().values());
            svgString = document.asXML();
        }
        return svgString;
    }

    public boolean checkIfSolutionExists(String scriptName, String graphFileName) throws SAXException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, IOException {
        return this.solve(scriptName,graphFileName).isSucces();
    }

    private SolveResults solve(String scriptname, String graphFileName) throws GraafvisCompiler.SyntaxException, GraafvisCompiler.CheckerException, IOException, SAXException {
        GraafvisCompiler compiler = new GraafvisCompiler();
        Solver solver = new Solver();
        String script = new String(Files.readAllBytes(new File(this.getClass().getClassLoader().getResource(scriptname).getFile()).toPath()));
        List<Term> terms = compiler.compile(script);

        SolveResults results;
        if (graphFileName != null) {
            Graph graph = Importer.graphFromFile(new File(this.getClass().getClassLoader().getResource(graphFileName).getFile()));
            results = solver.solve(graph, terms);
        } else {
            results = solver.solve(terms);
        }
        return results;
    }
}
