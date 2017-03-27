import za.co.wstoop.jatalog.DatalogException;
import za.co.wstoop.jatalog.Jatalog;

import static za.co.wstoop.jatalog.Expr.expr;

/**
 * Created by Lindsay on 27-Mar-17.
 */
public class RuleGenerator {
    public static void main(String[] args) throws DatalogException {
        Jatalog jl = new Jatalog();
        jl
            .fact("node", "a")
            .fact("label", "b", "\"Els\"")
            .rule(expr("shape", "X", "square"), expr("node", "X"))
            .rule(expr("shape", "X", "square"), expr("node", "X"), expr("label", "\"wolf\""))
            .rule(expr("shape", "X", "square"), expr("node", "X"), expr("label", "\"wolf\""))
            .rule(expr("shape", "X", "square"), expr("node", "X"), expr("label", "_"))
            .rule(expr("shape", "X", "square"), expr("node", "X"), expr("label", "A**&"))
        ;
        System.out.println(jl);
        System.out.println(jl.query(expr("shape", "X", "square")));
    }


}
