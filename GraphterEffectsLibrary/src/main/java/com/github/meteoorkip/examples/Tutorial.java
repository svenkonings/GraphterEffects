//package com.github.meteoorkip.examples;
//
//import alice.tuprolog.Term;
//import com.github.meteoorkip.graafvis.GraafvisCompiler;
//import com.github.meteoorkip.graphloader.Importer;
//import com.github.meteoorkip.solver.SolveResults;
//import com.github.meteoorkip.solver.Solver;
//import com.github.meteoorkip.svg.SvgDocumentGenerator;
//import org.dom4j.Document;
//import org.graphstream.graph.Graph;
//import org.xml.sax.SAXException;
//
//import java.io.IOException;
//import java.util.List;
//
//public class Tutorial {
//
//    public static void main(String[] args) {
//        GraafvisCompiler compiler = new GraafvisCompiler();
//        Solver solver = new Solver();
//        try {
//            //Compiles Graafvis
//            List<Term> terms = compiler.compile("graphLibrary(mylibrary).\ntriangle(X,Y,Z) -> shape(X,ellipse).");
//
//            //Loads a graph from a file
//            Graph graph = Importer.graphFromFile("mygraph.dot");
//
//            //add a library using its string key, and the constructor of the library class
//            solver.putGraphLibraryLoader("mylibrary", MyLibrary::new);
//
//            //Solves for visualization elements
//            SolveResults results = solver.solve(graph, terms);
//
//            //Yields a Dom4j Document that can be edited as a tree structure.
//            Document svgDocument = SvgDocumentGenerator.generate(results.getVisMap().values());
//
//            //Converts the Document to XML (and thus SVG)
//            String svgString = svgDocument.asXML();
//
//            System.out.println(svgString);
//
//
//        } catch (GraafvisCompiler.SyntaxException | GraafvisCompiler.CheckerException | IOException | SAXException e) {
//            //Thrown when the Graafvis script contains syntax errors.
//            e.printStackTrace();
//        }
//    }
//}
