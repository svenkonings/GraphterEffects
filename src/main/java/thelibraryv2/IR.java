package thelibraryv2;

import utils.Pair;
import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Rule;

import java.util.*;
import org.graphstream.graph.implementations.*;


public class IR {


    public static void main(String[] args) {
        Set<String> imports = new HashSet<>();
        imports.add("ext1.mincut.");
        imports.add("ext1.maxflow.");
        List<Rule> rulelist = new LinkedList<>();
        Pair<Set<String>, List<Rule>> res = new Pair<>(imports, rulelist);

        //fact
        rulelist.add(new Rule(new Expr("label", Arrays.asList("X", "hello"))));

        //rule
        rulelist.add(new Rule(new Expr("shape", Arrays.asList("X", "circle")), new Expr("label", Arrays.asList("X", "hello"))));
        System.out.println(res);

    }

    public IR(){

    }
}
