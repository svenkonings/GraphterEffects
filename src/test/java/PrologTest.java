import alice.tuprolog.*;
import org.junit.Test;

public class PrologTest {
    @Test
    public void test1() {
        Var varX = new Var("X"), varY = new Var("Y");
        Struct atomP = new Struct("p");
        Struct list = new Struct(atomP, varY); // should be [p|Y]
        System.out.println(list); // prints the list [p|Y]
        Struct fact = new Struct("p", new Struct("a"), new Int(5));
        Struct goal = new Struct("p", varX, new Var("Z"));
        Prolog engine = new Prolog();
        boolean res = goal.unify(engine, fact); // should be X/a, Y/5
        System.out.println(goal); // prints the unified term p(a,5)
        System.out.println(varX); // prints the variable binding X/a
        Var varW = new Var("W");
        res = varW.unify(engine, varY); // should be Z=Y
        System.out.println(varY); // prints just Y, since it is unbound
        System.out.println(varW); // prints the variable binding W / Y
        Struct st = new Struct("q", new Var("Y"), new Var("Y")); // unresolved
        System.out.println(st.getArg(0) == st.getArg(1)); // prints false
        st.resolveTerm(); // now the term is resolved
        res = st.match(new Struct()); // alternatively
        res = st.unify(engine, new Struct()); // alternatively
        System.out.println(st.getArg(0) == st.getArg(1)); // prints true
    }

    @Test
    public void test2() throws MalformedGoalException, NoSolutionException {
        Prolog engine = new Prolog();
        SolveInfo info = engine.solve("append([1],[2,3],X).");
        System.out.println(info.getSolution());
    }

    @Test
    public void test3() throws MalformedGoalException, NoSolutionException, NoMoreSolutionException {
        Prolog engine = new Prolog();
        SolveInfo info = engine.solve("append(X,Y,[1,2]).");
        while (info.isSuccess()) {
            System.out.println("solution: " + info.getSolution() + " - bindings: " + info);
            if (engine.hasOpenAlternatives()) {
                info = engine.solveNext();
            } else {
                break;
            }
        }

    }

    @Test
    public void test4() throws InvalidTheoryException, MalformedGoalException, NoSolutionException, NoMoreSolutionException {
        Struct clause0 = new Struct(":-", new Struct("node", new Struct(new Term[] {new Struct("a"), new Struct("b")}), new Struct("e")), new Struct("true"));
        Struct clause1 = new Struct(":-", new Struct("p", new Var("X")), new Struct("q", new Var("X")));
        Struct clause2 = new Struct(":-", new Struct("q", new Int(0)), new Struct("true"));
        System.out.println(clause0 + " is a clause? " + clause0.isClause());
        System.out.println(clause1 + " is a clause? " + clause1.isClause());
        System.out.println(clause2 + " is a clause? " + clause2.isClause());
        Prolog engine = new Prolog();
        Struct clauseList = new Struct(clause0, new Struct(clause1, new Struct(clause2, new Struct())));
        System.out.println(clauseList + " is a list? " + clauseList.isList());
        Theory t = new Theory(clauseList);
        engine.addTheory(t);
        SolveInfo info = engine.solve(new Struct("p", new Var("X")));
        while (info.isSuccess()) { // taken from the previous example
            System.out.println("solution: " + info.getSolution() + " - bindings: " + info);
            if (engine.hasOpenAlternatives()) {
                info = engine.solveNext();
            } else {
                break;
            }
        }
        info = engine.solve(new Struct("node", new Var("X"), new Var("Y")));
        while (info.isSuccess()) { // taken from the previous example
            System.out.println("solution: " + info.getSolution() + " - bindings: " + info);
            if (engine.hasOpenAlternatives()) {
                info = engine.solveNext();
            } else {
                break;
            }
        }
    }

    // TODO: Write negation test
}
