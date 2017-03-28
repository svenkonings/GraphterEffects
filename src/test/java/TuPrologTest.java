import alice.tuprolog.*;
import org.junit.Test;

import static prolog.TuProlog.*;

public class TuPrologTest {
    @Test
    public void test4() throws InvalidTheoryException, MalformedGoalException, NoSolutionException, NoMoreSolutionException {
        Struct clause0 = fact(compound("node", list(atom("a"), atom("b")), atom("e")));
        Struct clause1 = clause(compound("p", var("X")), compound("q", var("X")));
        Struct clause2 = fact(compound("q", intVal(0)));

        System.out.println(clause0 + " is a clause? " + clause0.isClause());
        System.out.println(clause1 + " is a clause? " + clause1.isClause());
        System.out.println(clause2 + " is a clause? " + clause2.isClause());

        Struct clauseList = list(clause0, clause1, clause2);
        System.out.println(clauseList + " is a list? " + clauseList.isList());

        Prolog engine = new Prolog();
        Theory t = new Theory(clauseList);
        engine.addTheory(t);

        SolveInfo info = engine.solve(compound("p", var("X")));
        while (info.isSuccess()) { // taken from the previous example
            System.out.println("solution: " + info.getSolution() + " - bindings: " + info);
            if (engine.hasOpenAlternatives()) {
                info = engine.solveNext();
            } else {
                break;
            }
        }

        info = engine.solve(compound("node", var("X"), var("Y")));
        while (info.isSuccess()) { // taken from the previous example
            System.out.println("solution: " + info.getSolution() + " - bindings: " + info);
            if (engine.hasOpenAlternatives()) {
                info = engine.solveNext();
            } else {
                break;
            }
        }
    }

    @Test
    public void test5() throws InvalidTheoryException, MalformedGoalException, NoSolutionException, NoMoreSolutionException {
        Struct clause0 = fact(compound("node", atom("a")));
        Struct clause1 = fact(compound("node", atom("b")));
        Struct clause2 = fact(compound("node", atom("c")));
        Struct clause3 = fact(compound("edge", atom("a"), atom("b")));
        Struct clause4 = clause(compound("test", var("N1"), var("N2")), and(
                compound("node", var("N1")),
                compound("node", var("N2")),
                compound("not", compound("edge", var("N1"), var("N2")))
        ));

        System.out.println(clause0 + " is a clause? " + clause0.isClause());
        System.out.println(clause1 + " is a clause? " + clause1.isClause());
        System.out.println(clause2 + " is a clause? " + clause2.isClause());
        System.out.println(clause3 + " is a clause? " + clause3.isClause());
        System.out.println(clause4 + " is a clause? " + clause4.isClause());

        Struct clauseList = list(clause0, clause1, clause2, clause3, clause4);
        System.out.println(clauseList + " is a list? " + clauseList.isList());

        Prolog engine = new Prolog();
        Theory t = new Theory(clauseList);
        engine.addTheory(t);

        SolveInfo info = engine.solve(compound("test", var("N1"), var("N2")));
        while (info.isSuccess()) { // taken from the previous example
            System.out.println("solution: " + info.getSolution() + " - bindings: " + info);
            if (engine.hasOpenAlternatives()) {
                info = engine.solveNext();
            } else {
                break;
            }
        }
    }

    @Test
    public void test6() throws InvalidTheoryException, MalformedGoalException, NoSolutionException, NoMoreSolutionException {
        Struct clause0 = fact(compound("mom", atom("a"), atom("c")));
        Struct clause1 = fact(compound("dad", atom("b"), atom("c")));
        Struct clause2 = clause(compound("parent", var("X")), or(
                compound("mom", var("X"), var()),
                compound("dad", var("X"), var())
        ));
        Struct clause3 = clause(compound("child", var("X")), or(
                compound("mom", var(), var("X")),
                compound("dad", var(), var("X"))
        ));

        System.out.println(clause0 + " is a clause? " + clause0.isClause());
        System.out.println(clause1 + " is a clause? " + clause1.isClause());
        System.out.println(clause2 + " is a clause? " + clause2.isClause());
        System.out.println(clause3 + " is a clause? " + clause3.isClause());

        Struct clauseList = list(clause0, clause1, clause2, clause3);
        System.out.println(clauseList + " is a list? " + clauseList.isList());

        Prolog engine = new Prolog();
        Theory t = new Theory(clauseList);
        engine.addTheory(t);

        SolveInfo info = engine.solve(compound("parent", var("X")));
        while (info.isSuccess()) { // taken from the previous example
            System.out.println("solution: " + info.getSolution() + " - bindings: " + info);
            if (engine.hasOpenAlternatives()) {
                info = engine.solveNext();
            } else {
                break;
            }
        }

        info = engine.solve(compound("child", var("X")));
        while (info.isSuccess()) { // taken from the previous example
            System.out.println("solution: " + info.getSolution() + " - bindings: " + info);
            if (engine.hasOpenAlternatives()) {
                info = engine.solveNext();
            } else {
                break;
            }
        }
    }
}
