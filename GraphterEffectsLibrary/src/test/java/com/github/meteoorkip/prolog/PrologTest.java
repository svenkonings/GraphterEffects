//package com.github.meteoorkip.prolog;
//
//import alice.tuprolog.*;
//import org.junit.Test;
//
//import static junit.framework.TestCase.assertFalse;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertTrue;
//import static org.junit.Assert.fail;
//
//public class PrologTest {
//    @Test
//    public void test1() {
//        Var varX = new Var("X");
//        Var varY = new Var("Y");
//        Var varW = new Var("W");
//
//        Struct atomP = new Struct("p");
//        Struct list = new Struct(atomP, varY);
//        Struct fact = new Struct("p", new Struct("a"), new Int(5));
//        Struct goal = new Struct("p", varX, new Var("Z"));
//        Struct st = new Struct("q", new Var("Y"), new Var("Y")); // unresolved
//        Prolog engine = new Prolog();
//
//        assertEquals("[p|Y]", list.toString());
//        assertTrue(goal.unify(engine, fact));
//        assertEquals("p(a,5)", goal.toString());
//        assertEquals("X / a", varX.toString());
//        assertTrue(varW.unify(engine, varY));
//        assertEquals("Y", varY.toString());
//        assertEquals("W / Y", varW.toString());
//        assertFalse(st.getArg(0) == st.getArg(1));
//        st.resolveTerm(); // now the term is resolved
//        assertFalse(st.match(new Struct()));
//        assertFalse(st.unify(engine, new Struct()));
//        assertTrue(st.getArg(0)==st.getArg(1));
//    }
//
//    @Test
//    public void test2() throws MalformedGoalException, NoSolutionException {
//        Prolog engine = new Prolog();
//        SolveInfo info = engine.solve("append([1],[2,3],X).");
//        assertEquals("append([1],[2,3],[1,2,3])", info.getSolution().toString());
//    }
//
//    @Test
//    public void test3() throws MalformedGoalException, NoSolutionException, NoMoreSolutionException {
//        Prolog engine = new Prolog();
//        SolveInfo info = engine.solve("append(X,Y,[1,2]).");
//
//        assertTrue(info.isSuccess());
//        assertEquals("append([],[1,2],[1,2])", info.getSolution().toString());
//        assertEquals("[X / [], Y / [1,2]]", info.getBindingVars().toString());
//        info = engine.solveNext();
//
//        assertTrue(info.isSuccess());
//        assertEquals("append([1],[2],[1,2])", info.getSolution().toString());
//        assertEquals("[X / [1], Y / [2]]", info.getBindingVars().toString());
//        info = engine.solveNext();
//
//        assertTrue(info.isSuccess());
//        assertEquals("append([1,2],[],[1,2])", info.getSolution().toString());
//        assertEquals("[X / [1,2], Y / []]", info.getBindingVars().toString());
//
//        try {
//            engine.solveNext();
//            fail();
//        } catch (NoMoreSolutionException e) {}
//
//    }
//
//    @Test
//    public void test4() throws InvalidTheoryException, MalformedGoalException, NoSolutionException, NoMoreSolutionException {
//        // node(a
//        Struct clause0 =
//                new Struct(
//                        ":-",
//                        new Struct(
//                                "node",
//                                new Struct(new Term[]{new Struct("a"), new Struct("b")}),
//                                new Struct("e")
//                        ),
//                        new Struct("true")
//                );
//        Struct clause1 =
//                new Struct(
//                        ":-",
//                        new Struct("p", new Var("X")),
//                        new Struct("q", new Var("X"))
//                );
//        Struct clause2 =
//                new Struct(
//                        ":-",
//                        new Struct("q", new Int(0)),
//                        new Struct("true")
//                );
//
//        assertTrue(clause0.isClause());
//        assertTrue(clause1.isClause());
//        assertTrue(clause2.isClause());
//
//        Prolog engine = new Prolog();
//
//        Struct clauseList =
//                new Struct(clause0,
//                        new Struct(clause1,
//                                new Struct(clause2,
//                                        new Struct()
//                                )
//                        )
//                );
//
//        assertTrue(clauseList.isList());
//
//        Theory t = new Theory(clauseList);
//        engine.addTheory(t);
//
//        SolveInfo info = engine.solve(new Struct("p", new Var("X")));
//        assertFalse(engine.hasOpenAlternatives());
//        assertEquals("p(0)", info.getSolution().toString());
//        assertEquals("[X / 0]", info.getBindingVars().toString());
//        try {
//            engine.solveNext();
//            fail();
//        } catch (NoMoreSolutionException e) {}
//
//
//        info = engine.solve(new Struct("node", new Var("X"), new Var("Y")));
//        assertFalse(engine.hasOpenAlternatives());
//        assertEquals("node([a,b],e)", info.getSolution().toString());
//        assertEquals("[X / [a,b], Y / e]", info.getBindingVars().toString());
//        try {
//            engine.solveNext();
//            fail();
//        } catch (NoMoreSolutionException e) {}
//    }
//
//    @Test
//    public void test5() throws InvalidTheoryException, MalformedGoalException, NoSolutionException, NoMoreSolutionException {
//        Struct clause0 =
//                new Struct(":-",
//                        new Struct("node", new Struct("a")),
//                        new Struct("true")
//                );
//        Struct clause1 =
//                new Struct(":-",
//                        new Struct("node", new Struct("b")),
//                        new Struct("true")
//                );
//        Struct clause2 =
//                new Struct(":-",
//                        new Struct("node", new Struct("c")),
//                        new Struct("true")
//                );
//        Struct clause3 =
//                new Struct(":-",
//                        new Struct("edge", new Struct("a"), new Struct("b")),
//                        new Struct("true")
//                );
//        Struct clause4 =
//                new Struct(":-",
//                        new Struct("test", new Var("N1"), new Var("N2")),
//                        new Struct(",",
//                                new Struct("node", new Var("N1")),
//                                new Struct(",",
//                                        new Struct("node", new Var("N2")),
//                                        new Struct("not",
//                                                new Struct("edge", new Var("N1"), new Var("N2"))
//                                        )
//                                )
//                        )
//                );
//
//        assertTrue(clause0.isClause());
//        assertTrue(clause1.isClause());
//        assertTrue(clause2.isClause());
//        assertTrue(clause3.isClause());
//        assertTrue(clause4.isClause());
//
//        Prolog engine = new Prolog();
//
//        Struct clauseList =
//                new Struct(clause0,
//                        new Struct(clause1,
//                                new Struct(clause2,
//                                        new Struct(clause3,
//                                                new Struct(clause4,
//                                                        new Struct()
//                                                )
//                                        )
//                                )
//                        )
//                );
//
//        assertTrue(clauseList.isList());
//
//        Theory t = new Theory(clauseList);
//        engine.addTheory(t);
//
//        SolveInfo info = engine.solve(new Struct("test", new Var("N1"), new Var("N2")));
//        assertTrue(engine.hasOpenAlternatives());
//        assertEquals("test(a,a)", info.getSolution().toString());
//        assertEquals("[N1 / a, N2 / a]", info.getBindingVars().toString());
//        info = engine.solveNext();
//        assertTrue(engine.hasOpenAlternatives());
//        assertEquals("test(a,c)", info.getSolution().toString());
//        assertEquals("[N1 / a, N2 / c]", info.getBindingVars().toString());
//        info = engine.solveNext();
//        assertTrue(engine.hasOpenAlternatives());
//        assertEquals("test(b,a)", info.getSolution().toString());
//        assertEquals("[N1 / b, N2 / a]", info.getBindingVars().toString());
//        info = engine.solveNext();
//        assertTrue(engine.hasOpenAlternatives());
//        assertEquals("test(b,b)", info.getSolution().toString());
//        assertEquals("[N1 / b, N2 / b]", info.getBindingVars().toString());
//        info = engine.solveNext();
//        assertTrue(engine.hasOpenAlternatives());
//        assertEquals("test(b,c)", info.getSolution().toString());
//        assertEquals("[N1 / b, N2 / c]", info.getBindingVars().toString());
//        info = engine.solveNext();
//        assertTrue(engine.hasOpenAlternatives());
//        assertEquals("test(c,a)", info.getSolution().toString());
//        assertEquals("[N1 / c, N2 / a]", info.getBindingVars().toString());
//        info = engine.solveNext();
//        assertTrue(engine.hasOpenAlternatives());
//        assertEquals("test(c,b)", info.getSolution().toString());
//        assertEquals("[N1 / c, N2 / b]", info.getBindingVars().toString());
//        info = engine.solveNext();
//        assertFalse(engine.hasOpenAlternatives());
//        assertEquals("test(c,c)", info.getSolution().toString());
//        assertEquals("[N1 / c, N2 / c]", info.getBindingVars().toString());
//        try {
//            engine.solveNext();
//            fail();
//        } catch (NoMoreSolutionException e) {}
//    }
//
//    @Test
//    public void test6() throws InvalidTheoryException, MalformedGoalException, NoSolutionException, NoMoreSolutionException {
//        Struct clause0 =
//                new Struct(":-",
//                        new Struct("mom", new Struct("a"), new Struct("c")),
//                        new Struct("true")
//                );
//        Struct clause1 =
//                new Struct(":-",
//                        new Struct("dad", new Struct("b"), new Struct("c")),
//                        new Struct("true")
//                );
//        Struct clause2 =
//                new Struct(":-",
//                        new Struct("parent", new Var("X")),
//                        new Struct(";",
//                                new Struct("mom", new Var("X"), new Var()),
//                                new Struct("dad", new Var("X"), new Var())
//                        )
//                );
//        Struct clause3 =
//                new Struct(":-",
//                        new Struct("child", new Var("X")),
//                        new Struct(";",
//                                new Struct("mom", new Var(), new Var("X")),
//                                new Struct("dad", new Var(), new Var("X"))
//                        )
//                );
//
//        assertTrue(clause0.isClause());
//        assertTrue(clause1.isClause());
//        assertTrue(clause2.isClause());
//        assertTrue(clause3.isClause());
//
//        Prolog engine = new Prolog();
//
//        Struct clauseList =
//                new Struct(clause0,
//                        new Struct(clause1,
//                                new Struct(clause2,
//                                        new Struct(clause3,
//                                                new Struct()
//                                        )
//                                )
//                        )
//                );
//
//        assertTrue(clauseList.isList());
//
//        Theory t = new Theory(clauseList);
//        engine.addTheory(t);
//
//        SolveInfo info = engine.solve(new Struct("parent", new Var("X")));
//        assertTrue(engine.hasOpenAlternatives());
//        assertEquals("parent(a)", info.getSolution().toString());
//        assertEquals("[X / a]", info.getBindingVars().toString());
//        info = engine.solveNext();
//        assertFalse(engine.hasOpenAlternatives());
//        assertEquals("parent(b)", info.getSolution().toString());
//        assertEquals("[X / b]", info.getBindingVars().toString());
//        try {
//            engine.solveNext();
//            fail();
//        } catch (NoMoreSolutionException e) {}
//
//
//        info = engine.solve(new Struct("child", new Var("X")));
//        assertTrue(engine.hasOpenAlternatives());
//        assertEquals("child(c)", info.getSolution().toString());
//        assertEquals("[X / c]", info.getBindingVars().toString());
//        info = engine.solveNext();
//        assertFalse(engine.hasOpenAlternatives());
//        assertEquals("child(c)", info.getSolution().toString());
//        assertEquals("[X / c]", info.getBindingVars().toString());
//        try {
//            engine.solveNext();
//            fail();
//        } catch (NoMoreSolutionException e) {}
//    }
//}
