package compiler.asrc;

import alice.tuprolog.*;
import org.graphstream.graph.Graph;


public class ASRCLibrary extends GraphLibrary {


    public ASRCLibrary(Graph g) {
        super(g);
    }

    public boolean label_2 (Term IDTerm, Var label) {
        String ID;
        if (IDTerm instanceof Var) {
            ID = ((Struct)IDTerm.getTerm()).getName();
        } else if (IDTerm instanceof Struct) {
            ID = ((Struct) IDTerm).getName();
        } else {
            ID = IDTerm.toString();
        }
        if (graph.getNode(ID)!=null) {
            return label.unify(getEngine(),new Struct(graph.getNode(ID).getAttribute("label").toString()));
        } else if (graph.getEdge(ID)!=null) {
            return label.unify(getEngine(),new Struct(graph.getEdge(ID).getAttribute("label").toString()));
        } else {
            try {
                System.out.println(ID);
                System.out.println("|" + graph.getEdge(0).getId()+ "|");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean label_2(Var ID, Term label) {
        return false;
    }


}
