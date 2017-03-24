package compiler.api;


import compiler.graphloader.Importer;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class Graafvis {

    private Graph graph;
    private Constraintset constraints;


    public static Graph importGraph(File file) throws UnknownFormatException, IOException, SAXException {
        return Importer.graphFromFile(file);
    }

    public static Constraintset importGraafvis(String path) {
        return null;
    }

    public static Constraintset getGraafvisFromString(String script) {
        return null;
    }

    public String getSVG(Graph g, Constraintset s) {
        return null;
    }
}
