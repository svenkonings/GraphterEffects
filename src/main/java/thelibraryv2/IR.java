package thelibraryv2;
import za.co.wstoop.jatalog.Expr;
import za.co.wstoop.jatalog.Rule;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by poesd_000 on 20/03/2017.
 */
public class IR {


    public static void main(String[] args) {
        List<Rule> rulelist = new LinkedList<>();
        //fact
        rulelist.add(new Rule(new Expr("label", Arrays.asList("X", "hello"))));

        //rule
        rulelist.add(new Rule(new Expr("shape", Arrays.asList("X", "circle")), new Expr("label", Arrays.asList("X", "hello"))));
        System.out.println(rulelist);
    }

    public IR(){

    }
}
