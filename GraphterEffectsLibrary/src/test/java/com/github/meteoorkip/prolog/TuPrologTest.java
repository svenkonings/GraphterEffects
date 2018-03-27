package com.github.meteoorkip.prolog;

import alice.tuprolog.*;
import org.junit.Test;

import static com.github.meteoorkip.prolog.TuProlog.*;
import static org.junit.Assert.*;

public class TuPrologTest {
    @Test
    public void test4() throws InvalidTheoryException, MalformedGoalException, NoSolutionException, NoMoreSolutionException {
        Struct clause0 = struct("node", list(term("a"), term("b")), term("e"));
        Struct clause1 = clause(struct("p", var("X")), struct("q", var("X")));
        Struct clause2 = struct("q", intVal(0));

        assertFalse(clause0.isClause());
        assertTrue(clause1.isClause());
        assertFalse(clause2.isClause());

        Struct clauseList = list(clause0, clause1, clause2);
        assertTrue(clauseList.isList());

        Prolog engine = new Prolog();
        Theory t = new Theory(clauseList);
        engine.addTheory(t);

        SolveInfo info = engine.solve(struct("p", var("X")));
        assertFalse(engine.hasOpenAlternatives());
        assertEquals("p(0)", info.getSolution().toString());
        assertEquals("[X / 0]", info.getBindingVars().toString());
        try {
            engine.solveNext();
            fail();
        } catch (NoMoreSolutionException e) {}

        info = engine.solve(struct("node", var("X"), var("Y")));
        assertFalse(engine.hasOpenAlternatives());
        assertEquals("node([a,b],e)", info.getSolution().toString());
        assertEquals("[X / [a,b], Y / e]", info.getBindingVars().toString());
        try {
            engine.solveNext();
            fail();
        } catch (NoMoreSolutionException e) {}

    }

    @Test
    public void test5() throws InvalidTheoryException, MalformedGoalException, NoSolutionException, NoMoreSolutionException {
        Struct clause0 = struct("node", term("a"));
        Struct clause1 = struct("node", term("b"));
        Struct clause2 = struct("node", term("c"));
        Struct clause3 = struct("edge", term("a"), term("b"));
        Struct clause4 = clause(struct("test", var("N1"), var("N2")), and(
                struct("node", var("N1")),
                struct("node", var("N2")),
                struct("not", struct("edge", var("N1"), var("N2")))
        ));

        assertFalse(clause0.isClause());
        assertFalse(clause1.isClause());
        assertFalse(clause2.isClause());
        assertFalse(clause3.isClause());
        assertTrue(clause4.isClause());

        Struct clauseList = list(clause0, clause1, clause2, clause3, clause4);
        assertTrue(clauseList.isList());

        Prolog engine = new Prolog();
        Theory t = new Theory(clauseList);
        engine.addTheory(t);

        SolveInfo info = engine.solve(struct("test", var("N1"), var("N2")));
        assertTrue(info.hasOpenAlternatives());
        assertEquals("test(a,a)", info.getSolution().toString());
        assertEquals("[N1 / a, N2 / a]", info.getBindingVars().toString());
        info = engine.solveNext();
        assertTrue(info.hasOpenAlternatives());
        assertEquals("test(a,c)", info.getSolution().toString());
        assertEquals("[N1 / a, N2 / c]", info.getBindingVars().toString());
        info = engine.solveNext();
        assertTrue(info.hasOpenAlternatives());
        assertEquals("test(b,a)", info.getSolution().toString());
        assertEquals("[N1 / b, N2 / a]", info.getBindingVars().toString());
        info = engine.solveNext();
        assertTrue(info.hasOpenAlternatives());
        assertEquals("test(b,b)", info.getSolution().toString());
        assertEquals("[N1 / b, N2 / b]", info.getBindingVars().toString());
        info = engine.solveNext();
        assertTrue(info.hasOpenAlternatives());
        assertEquals("test(b,c)", info.getSolution().toString());
        assertEquals("[N1 / b, N2 / c]", info.getBindingVars().toString());
        info = engine.solveNext();
        assertTrue(info.hasOpenAlternatives());
        assertEquals("test(c,a)", info.getSolution().toString());
        assertEquals("[N1 / c, N2 / a]", info.getBindingVars().toString());
        info = engine.solveNext();
        assertTrue(info.hasOpenAlternatives());
        assertEquals("test(c,b)", info.getSolution().toString());
        assertEquals("[N1 / c, N2 / b]", info.getBindingVars().toString());
        info = engine.solveNext();
        assertFalse(info.hasOpenAlternatives());
        assertEquals("test(c,c)", info.getSolution().toString());
        assertEquals("[N1 / c, N2 / c]", info.getBindingVars().toString());
        try {
            engine.solveNext();
            fail();
        } catch (NoMoreSolutionException e) {}
    }

    @Test
    public void test6() throws InvalidTheoryException, MalformedGoalException, NoSolutionException, NoMoreSolutionException {
        Struct clause0 = struct("mom", term("a"), term("c"));
        Struct clause1 = struct("dad", term("b"), term("c"));
        Struct clause2 = clause(struct("parent", var("X")), or(
                struct("mom", var("X"), var()),
                struct("dad", var("X"), var())
        ));
        Struct clause3 = clause(struct("child", var("X")), or(
                struct("mom", var(), var("X")),
                struct("dad", var(), var("X"))
        ));

        assertFalse(clause0.isClause());
        assertFalse(clause1.isClause());
        assertTrue(clause2.isClause());
        assertTrue(clause3.isClause());

        Struct clauseList = list(clause0, clause1, clause2, clause3);
        assertTrue(clauseList.isList());

        Prolog engine = new Prolog();
        Theory t = new Theory(clauseList);
        engine.addTheory(t);

        SolveInfo info = engine.solve(struct("parent", var("X")));
        assertTrue(engine.hasOpenAlternatives());
        assertEquals("parent(a)", info.getSolution().toString());
        assertEquals("[X / a]", info.getBindingVars().toString());
        info = engine.solveNext();
        assertFalse(engine.hasOpenAlternatives());
        assertEquals("parent(b)", info.getSolution().toString());
        assertEquals("[X / b]", info.getBindingVars().toString());
        try {
            engine.solveNext();
            fail();
        } catch (NoMoreSolutionException e) {}

        info = engine.solve(struct("child", var("X")));
        assertTrue(info.hasOpenAlternatives());
        assertEquals("child(c)", info.getSolution().toString());
        assertEquals("[X / c]", info.getBindingVars().toString());
        info = engine.solveNext();
        assertFalse(engine.hasOpenAlternatives());
        assertEquals("child(c)", info.getSolution().toString());
        assertEquals("[X / c]", info.getBindingVars().toString());
        try {
            engine.solveNext();
            fail();
        } catch (NoMoreSolutionException e) {}
    }
}
