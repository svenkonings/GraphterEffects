package com.github.meteoorkip.system;

import com.github.meteoorkip.graafvis.GraafvisCompiler;
import com.github.meteoorkip.graphloader.Importer;
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
import java.net.URL;
import java.util.List;

/**
 * Created by Hans on 16-11-2017.
 */
public class TestHelper {

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
        String script = FileUtils.fromResourcesAsString(scriptname);
        List<Clause> terms = compiler.compile(script);

        SolveResults results;
        if (graphFileName != null) {
            URL resource = this.getClass().getClassLoader().getResource(graphFileName);
            if (resource == null) {
                throw new IOException("Could not find graph file");
            }
            Graph graph = Importer.graphFromFile(new File(resource.getFile()));
            results = solver.solve(graph, terms);
        } else {
            results = solver.solve(terms);
        }
        return results;
    }

}
