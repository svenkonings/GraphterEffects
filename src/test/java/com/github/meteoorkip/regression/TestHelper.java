package com.github.meteoorkip.regression;

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

    public TestHelper(){

    }

    public String compileFile(String scriptName,String dotFileName) throws IOException, GraafvisCompiler.SyntaxException, GraafvisCompiler.CheckerException, SAXException {
        GraafvisCompiler compiler = new GraafvisCompiler();
        Solver solver = new Solver();
        String script = new String(Files.readAllBytes(new File(this.getClass().getClassLoader().getResource(scriptName).getFile()).toPath()));
        List<Term> terms = compiler.compile(script);

        Graph graph = Importer.graphFromFile(new File(this.getClass().getClassLoader().getResource(dotFileName).getFile()));

        SolveResults results = solver.solve(graph, terms);

        Document document = SvgDocumentGenerator.generate(results.getVisMap().values());
        String svgString = document.asXML();
        return svgString;
        //return Input.fromFile(new File(this.getClass().getResource("a.txt").getFile())).build();
    }
}
