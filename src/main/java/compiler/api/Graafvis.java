package compiler.api;


import compiler.ConstraintSet;
import compiler.graphloader.Importer;
import org.graphstream.graph.Graph;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class Graafvis {

    private Graph graph;
    private ConstraintSet constraints;


    public static Graph importGraph(File file) throws UnknownFormatException, IOException, SAXException {
        return Importer.graphFromFile(file);
    }

    public static ConstraintSet importGraafvis(String path) {
        return null;
    }

    public static ConstraintSet importGraafvis(File file) {
        return importGraafvis(file.getAbsolutePath());
    }

    public static ConstraintSet getGraafvisFromString(String script) {
        return null;
    }

    public String getSVG(Graph g, ConstraintSet s) {
        return null;
    }
}
