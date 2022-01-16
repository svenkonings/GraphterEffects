package com.github.meteoorkip.system;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.graphloader.Importer;
import com.github.meteoorkip.prolog.PrologException;
import com.github.meteoorkip.solver.SolveResults;
import com.github.meteoorkip.solver.Solver;
import com.github.meteoorkip.svg.SvgDocumentGenerator;
import com.github.meteoorkip.utils.FileUtils;
import it.unibo.tuprolog.core.Clause;
import org.dom4j.Document;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Hans on 16-11-2017.
 */
public class TestHelper {

    private String script;
    private Graph graph;

    public TestHelper() {}

    public String compileFile(String scriptName, String graphFileName) throws IOException, GraafvisCompiler.SyntaxException, GraafvisCompiler.CheckerException, SAXException, PrologException {
        SolveResults results = solve(scriptName, graphFileName);
        String svgString = null;
        if(results.isSucces()) {
            Document document = SvgDocumentGenerator.generate(results.getVisMap().values());
            svgString = document.asXML();
        }
        return svgString;
    }

    public boolean checkIfSolutionExists(String scriptName, String graphFileName) throws SAXException, GraafvisCompiler.CheckerException, GraafvisCompiler.SyntaxException, IOException {
        try {
            return this.solve(scriptName,graphFileName).isSucces();
        } catch (PrologException e) {
            e.printStackTrace();
            return false;
        }
    }

    private SolveResults solve(String scriptname, String graphFileName) throws GraafvisCompiler.SyntaxException, GraafvisCompiler.CheckerException, IOException, SAXException, PrologException {
        GraafvisCompiler compiler = new GraafvisCompiler();
        Solver solver = new Solver();
        script = FileUtils.fromResourcesAsString(scriptname);
        List<Clause> terms = compiler.compile(script);

        SolveResults results;
        if (graphFileName != null) {
            graph = Importer.graphFromFile(new File(this.getClass().getClassLoader().getResource(graphFileName).getFile()));
            results = solver.solve(graph, terms);
        } else {
            results = solver.solve(terms);
        }
        return results;
    }

    public Graph getGraph() {
        return graph;
    }

    public String getScript() {
        return script;
    }
}
