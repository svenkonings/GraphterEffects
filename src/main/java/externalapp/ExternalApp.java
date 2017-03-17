package externalapp;

import Graphstream.Graph;
import api.Constraintset;
import api.Graafvis;
import api.UnknownFormatException;

/**
 * Created by poesd_000 on 17/03/2017.
 */
public class ExternalApp {
    public static void main(String[] args) throws UnknownFormatException {
        Graafvis gv = new Graafvis();
        Graph g = Graafvis.importGraph("dinges.DOT");
        Constraintset cs = Graafvis.importGraafvis("dsl.vis");
        System.out.println(gv.getSVG(g, cs));
    }
}
