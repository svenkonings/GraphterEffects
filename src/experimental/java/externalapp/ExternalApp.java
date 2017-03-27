package externalapp;


import compiler.Constraintset;
import compiler.api.Graafvis;
import compiler.api.UnknownFormatException;
import compiler.graphloader.Importer;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;

import java.io.IOException;

public class ExternalApp {
    public static void main(String[] args) throws UnknownFormatException, IOException, SAXException {
        Graafvis gv = new Graafvis();
        Graph g = Importer.graphFromFile(FileUtils.fromResources("dinges.dot"));
        Constraintset cs = Graafvis.importGraafvis(FileUtils.fromResources("dsl.vis"));
        System.out.println(gv.getSVG(g, cs));
    }
}
