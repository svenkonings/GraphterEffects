package solver;

import org.chocosolver.solver.Model;
import prolog.TuProlog;

public class SolveResults {

    private final boolean succes;
    private final TuProlog prolog;
    private final Model model;
    private final VisMap visMap;

    public SolveResults(boolean succes, TuProlog prolog, Model model, VisMap visMap) {
        this.succes = succes;
        this.prolog = prolog;
        this.model = model;
        this.visMap = visMap;
    }

    public boolean isSucces() {
        return succes;
    }

    public TuProlog getProlog() {
        return prolog;
    }

    public Model getModel() {
        return model;
    }

    public VisMap getVisMap() {
        return visMap;
    }
}
