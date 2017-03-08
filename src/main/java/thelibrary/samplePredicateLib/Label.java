package thelibrary.samplePredicateLib;

import thelibrary.*;

import java.util.LinkedList;
import java.util.List;

public class Label extends Predicate{

    public Label() {
        expected.add(Node.class);
        expected.add(String.class);
    }

    @Override
    public List<Object> solveForNull(List<Object> input) throws MultipleNullException, InvalidSizeException, NoSolutionException {
        if (input.size()!=expected.size()) {
            throw new InvalidSizeException(expected.size(), input.size());
        }
        List<Object> res = new LinkedList<>();
        Graph g = Graafvis.getGraph();
        if (input.get(0)==null && input.get(1)!=null) {
            for (Node n : g.getNodes()) {
                if (n.getProp("label").equals(input.get(1))) {
                    res.add(n);
                    res.add(input.get(1));
                    break;
                }
            }
        } else if (input.get(0)!=null && input.get(1)==null) {
            res.add(input.get(0));
            res.add(((Node)input.get(0)).getProp("label"));
        } else if (input.get(0)!=null && input.get(1)!=null) {
            res.addAll(input);
        }

        if (res.get(0)==null || res.get(1)==null){
            throw new NoSolutionException();
        }

        return res;

    }
}
