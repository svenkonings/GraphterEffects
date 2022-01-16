package com.github.meteoorkip.prolog;


import com.github.meteoorkip.utils.CollectionUtils;
import it.unibo.tuprolog.core.Clause;
import it.unibo.tuprolog.core.Term;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.github.meteoorkip.prolog.TuProlog.*;
import static org.junit.jupiter.api.Assertions.*;


public class TuPrologTest {
    @Test
    public void test4() {
        Clause clause0 = fact(struct("node", list(atom("a"), atom("b")), atom("e")));
        Clause clause1 = clause(struct("p", var("X")), struct("q", var("X")));
        Clause clause2 = fact(struct("q", intVal(0)));

        TuProlog tuProlog = new TuProlog(clause0, clause1, clause2);

        assertEquals(CollectionUtils.listOf(CollectionUtils.mapOf("X", intVal(0))), tuProlog.solve(struct("p", var("X"))));
        assertEquals(CollectionUtils.listOf(CollectionUtils.mapOf("X", list(atom("a"), atom("b")), "Y", atom("e"))), tuProlog.solve(struct("node", var("X"), var("Y"))));
    }

    @Test
    public void test5() {
        Clause clause0 = fact("node", atom("a"));
        Clause clause1 = fact("node", atom("b"));
        Clause clause2 = fact("node", atom("c"));
        Clause clause3 = fact("edge", atom("a"), atom("b"));
        Clause clause4 = clause(struct("test", var("N1"), var("N2")),
                struct("node", var("N1")),
                struct("node", var("N2")),
                struct("not", struct("edge", var("N1"), var("N2")))
        );

        TuProlog tuProlog = new TuProlog(clause0, clause1, clause2, clause3, clause4);
        java.util.List<Map<String, Term>> solved = tuProlog.solve(struct("test", var("N1"), var("N2")));
        assertEquals(CollectionUtils.listOf(
                CollectionUtils.mapOf("N1", atom("a"), "N2", atom("a")),
                CollectionUtils.mapOf("N1", atom("a"), "N2", atom("c")),
                CollectionUtils.mapOf("N1", atom("b"), "N2", atom("a")),
                CollectionUtils.mapOf("N1", atom("b"), "N2", atom("b")),
                CollectionUtils.mapOf("N1", atom("b"), "N2", atom("c")),
                CollectionUtils.mapOf("N1", atom("c"), "N2", atom("a")),
                CollectionUtils.mapOf("N1", atom("c"), "N2", atom("b")),
                CollectionUtils.mapOf("N1", atom("c"), "N2", atom("c"))), solved);
    }

    @Test
    public void test6() {
        Clause clause0 = Clause.of(struct("mom", atom("a"), atom("c")));
        Clause clause1 = Clause.of(struct("dad", atom("b"), atom("c")));
        Clause clause2 = Clause.of(struct("parent", var("X")), or(
                struct("mom", var("X"), var()),
                struct("dad", var("X"), var())
        ));
        Clause clause3 = clause(struct("child", var("X")), or(
                struct("mom", var(), var("X")),
                struct("dad", var(), var("X"))
        ));

        assertTrue(clause0.isClause());
        assertTrue(clause1.isClause());
        assertTrue(clause2.isClause());
        assertTrue(clause3.isClause());

        TuProlog tuProlog = new TuProlog(clause0, clause1, clause2, clause3);
        java.util.List<Map<String, Term>> info = tuProlog.solve(struct("parent", var("X")));
        assertEquals(2, info.size());
        assertEquals(CollectionUtils.listOf(CollectionUtils.mapOf("X", atom("a")), CollectionUtils.mapOf("X", atom("b"))), info);

        info = tuProlog.solve(struct("child", var("X")));
//        assertEquals(2, info.size());
//        assertEquals(java.util.List.of(Map.of("X", atom("c")), Map.of("X", atom("c"))), info);
}}
