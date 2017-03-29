package compiler.prolog;

import alice.tuprolog.*;
import org.junit.Test;

import static compiler.prolog.TuProlog.*;

public class TuPrologTest {
    @Test
    public void test4() throws InvalidTheoryException, MalformedGoalException, NoSolutionException, NoMoreSolutionException {
        Struct clause0 = struct("node", list(struct("a"), struct("b")), struct("e"));
        Struct clause1 = clause(struct("p", var("X")), struct("q", var("X")));
        Struct clause2 = struct("q", intVal(0));

        System.out.println(clause0 + " is a clause? " + clause0.isClause());
        System.out.println(clause1 + " is a clause? " + clause1.isClause());
        System.out.println(clause2 + " is a clause? " + clause2.isClause());

        Struct clauseList = list(clause0, clause1, clause2);
        System.out.println(clauseList + " is a list? " + clauseList.isList());

        Prolog engine = new Prolog();
        Theory t = new Theory(clauseList);
        engine.addTheory(t);

        SolveInfo info = engine.solve(struct("p", var("X")));
        while (info.isSuccess()) { // taken from the previous example
            System.out.println("solution: " + info.getSolution() + " - bindings: " + info);
            if (engine.hasOpenAlternatives()) {
                info = engine.solveNext();
            } else {
                break;
            }
        }

        info = engine.solve(struct("node", var("X"), var("Y")));
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
        Struct clause0 = struct("node", struct("a"));
        Struct clause1 = struct("node", struct("b"));
        Struct clause2 = struct("node", struct("c"));
        Struct clause3 = struct("edge", struct("a"), struct("b"));
        Struct clause4 = clause(struct("test", var("N1"), var("N2")), and(
                struct("node", var("N1")),
                struct("node", var("N2")),
                struct("not", struct("edge", var("N1"), var("N2")))
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

        SolveInfo info = engine.solve(struct("test", var("N1"), var("N2")));
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
        Struct clause0 = struct("mom", struct("a"), struct("c"));
        Struct clause1 = struct("dad", struct("b"), struct("c"));
        Struct clause2 = clause(struct("parent", var("X")), or(
                struct("mom", var("X"), var()),
                struct("dad", var("X"), var())
        ));
        Struct clause3 = clause(struct("child", var("X")), or(
                struct("mom", var(), var("X")),
                struct("dad", var(), var("X"))
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

        SolveInfo info = engine.solve(struct("parent", var("X")));
        while (info.isSuccess()) { // taken from the previous example
            System.out.println("solution: " + info.getSolution() + " - bindings: " + info);
            if (engine.hasOpenAlternatives()) {
                info = engine.solveNext();
            } else {
                break;
            }
        }

        info = engine.solve(struct("child", var("X")));
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
