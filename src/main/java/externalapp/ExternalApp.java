package externalapp;

import api.Constraintset;
import api.Graafvis;
import api.UnknownFormatException;
import org.graphstream.graph.Graph;

public class ExternalApp {
    public static void main(String[] args) throws UnknownFormatException {
        Graafvis gv = new Graafvis();
        Graph g = Graafvis.importGraph("dinges.DOT");
        Constraintset cs = Graafvis.importGraafvis("dsl.vis");
        System.out.println(gv.getSVG(g, cs));
    }
}
