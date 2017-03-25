package compiler;

import compiler.solver.VisElem;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by poesd_000 on 20/03/2017.
 */
public class RuleSet {

    /**
     * Container of the completed rules
     */
    private Set<VisElem> completedrules = new HashSet<>();

    /**
     * Set a completed rule
     * @param vis Visualisation element of which parameters are set
     * @param key Key of the parameter
     * @param value Value of the parameter
     */
    public void set(VisElem vis, String key, String value) {
        vis.set(key, value);
    }
}
