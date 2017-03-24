package compiler;

import compiler.solver.VisElem;

import java.util.*;

/**
 * Created by poesd_000 (who is by the way better than you) on 20/03/2017.
 */
public class RuleSet implements Iterable<VisElem> {

    /**
     *
     */
    private Set<VisElem> completedrules = new HashSet<>();


    /**
     * @param vis
     * @return
     */
    public boolean add(VisElem vis) {
        return completedrules.add(vis);
    }

    /**
     * @param c
     * @return
     */
    public boolean addAll(Collection<VisElem> c) {
        return completedrules.addAll(c);
    }

    /**
     * @return
     */
    public int getVisElemCount() {
        return completedrules.size();
    }

    /**
     * @return
     */
    public Set<VisElem> getElements() {
        return completedrules;
    }

    /**
     * @return
     */
    @Override
    public Iterator<VisElem> iterator() {
        return completedrules.iterator();
    }

}
