package externalapp;


import compiler.ConstraintSet;
import compiler.api.Graafvis;
import exceptions.UnknownFormatException;
import compiler.graphloader.Importer;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;
import utils.FileUtils;

import java.io.IOException;

public class ExternalApp {
    public static void main(String[] args) throws UnknownFormatException, IOException, SAXException {
        Graafvis gv = new Graafvis();
        Graph g = Importer.graphFromFile(FileUtils.fromResources("dinges.dot"));
        ConstraintSet cs = Graafvis.importGraafvis(FileUtils.fromResources("dsl.vis"));
        System.out.println(gv.getSVG(g, cs));
    }
}
