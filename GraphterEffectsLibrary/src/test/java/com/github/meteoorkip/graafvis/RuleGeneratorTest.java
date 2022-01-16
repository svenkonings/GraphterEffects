package com.github.meteoorkip.graafvis;

import com.github.meteoorkip.graafvis.generator.RuleGenerator;
import com.github.meteoorkip.graafvis.grammar.GraafvisLexer;
import com.github.meteoorkip.graafvis.grammar.GraafvisParser;
import it.unibo.tuprolog.core.Clause;
import it.unibo.tuprolog.core.Rule;
import it.unibo.tuprolog.core.Struct;
import it.unibo.tuprolog.core.Term;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.meteoorkip.graafvis.generator.RuleGenerator.inList;
import static com.github.meteoorkip.prolog.TuProlog.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RuleGeneratorTest {

    /***************
     --- Testing ---
     ***************/

    @Test
    public void testFacts() {
        singleAssert("p(X).", fact(struct("p", var("X"))));
        singleAssert("`,`(a, b).", fact(struct(",", struct("a"), struct("b"))));
        singleAssert("`%/{`(a, b, c).", fact(struct("%/{", struct("a"), struct("b"), struct("c"))));
        singleAssert("p(X,Y).", fact(struct("p", var("X"), var("Y"))));
        multAssert("p(X), p(Y).", fact(struct("p", var("X"))), fact(struct("p", var("Y"))));
        // List
        singleAssert("p([X,Y]).", fact(struct("p", list(var("X"), var("Y")))));
        singleAssert("shape([X,Y], square).",
                fact(struct("shape", list(var("X"), var("Y")), struct("square")))
        );
        singleAssert("shape([], square).",
                fact(struct("shape", list(), struct("square")))
        );
        singleAssert("[].", fact(list()));
        singleAssert("[a].", fact(list(struct("a"))));
        singleAssert("[a,X].", fact(list(struct("a"), var("X"))));
        singleAssert("[a,b|[c]].",fact( struct(".", struct("a"), struct(".", struct("b"), list(struct("c"))))));
        // Nested list
        singleAssert("shape([X,[3, \"wolf\"]], square).",
                fact(struct("shape", list(var("X"), list(number("3"), struct("\"wolf\""))), struct("square")))
        );
        singleAssert("shape([X,[3, \"wolf\", []]], square).",
                fact(struct("shape", list(var("X"), list(number("3"), struct("\"wolf\""), list())), struct("square")))
        );
    }

    @Test
    public void testRules() {
        singleAssert("p(X) -> q(X).", clause(struct("q", var("X")), struct("p", var("X"))));
        singleAssert("`,`(a, b) -> r.", clause(struct("r"), struct(",", struct("a"), struct("b"))));
        singleAssert("`%/{`(a, b, c) -> r.", clause(struct("r"), struct("%/{", struct("a"), struct("b"), struct("c"))));

        // And
        singleAssert("p(X), q(X) -> r(X).",
                clause(struct("r", var("X")), and(struct("p", var("X")), struct("q", var("X"))))
        );
        multAssert("p(X) -> q(X), r(X).",
                clause(struct("q", var("X")), struct("p", var("X"))),
                clause(struct("r", var("X")), struct("p", var("X")))
        );
        multAssert("p(X), q(X) -> r(X), s(X).",
                clause(struct("r", var("X")), and(struct("p", var("X")), struct("q", var("X")))),
                clause(struct("s", var("X")), and(struct("p", var("X")), struct("q", var("X"))))
        );
        singleAssert("node(X), node(Y), edge(X, Y) -> shape([X,Y], line).",
                clause(
                        struct("shape", list(var("X"), var("Y")), struct("line")),
                        and(struct("node", var("X")), struct("node", var("Y")), struct("edge", var("X"), var("Y")))
                )
        );

        // Or
        singleAssert("p(X); q(X) -> r(X).",
                clause(struct("r", var("X")), or(struct("p", var("X")), struct("q", var("X"))))
        );
        singleAssert("p(X), q(X); r(X) -> s(X).",
                clause(
                        struct("s", var("X")),
                        or(and(struct("p", var("X")), struct("q", var("X"))), struct("r", var("X")))
                )
        );
        singleAssert("p(X); q(X), r(X) -> s(X).",
                clause(
                        struct("s", var("X")),
                        or(struct("p", var("X")), and(struct("q", var("X")), struct("r", var("X"))))
                )
        );
        singleAssert("p(X); q(X), r(X); s(X) -> t(X).",
                clause(
                        struct("t", var("X")),
                        or(
                                or(struct("p", var("X")), and(struct("q", var("X")), struct("r", var("X")))),
                                struct("s", var("X"))
                        )
                )
        );

        // Nesting
        singleAssert("p(X), (q(X); r(X)) -> s(X).",
                clause(
                        struct("s", var("X")),
                        and(struct("p", var("X")), or(struct("q", var("X")), struct("r", var("X"))))
                )
        );
        singleAssert("p(X), (q(X); (r(X), s(X))) -> t(X).",
                clause(
                        struct("t", var("X")),
                        and(
                                struct("p", var("X")),
                                or(struct("q", var("X")), and(struct("r", var("X")), struct("s", var("X"))))
                        )
                )
        );

        // Not
        singleAssert("not p(X) -> q(X).", clause(struct("q", var("X")), not(struct("p", var("X")))));
        singleAssert("not not p(X) -> q(X).", clause(struct("q", var("X")), not(not(struct("p", var("X"))))));
        singleAssert("not p(X), q(X) -> r(X).",
                clause(struct("r", var("X")), and(not(struct("p", var("X"))), struct("q", var("X"))))
        );
        singleAssert("p(X), not q(X) -> r(X).",
                clause(struct("r", var("X")), and(struct("p", var("X")), not(struct("q", var("X")))))
        );

        // List
        singleAssert("p([X,Y]) -> r.", clause(struct("r"), struct("p", list(var("X"), var("Y")))));
        singleAssert("shape([X,Y], square) -> r.",
                clause(struct("r"), struct("shape", list(var("X"), var("Y")), struct("square")))
        );
        singleAssert("shape([], square) -> r.",
                clause(struct("r"), struct("shape", list(), struct("square")))
        );
        singleAssert("[] -> r.", clause(struct("r"), list()));
        singleAssert("[a] -> r.", clause(struct("r"), list(struct("a"))));
        singleAssert("[a,X] -> r.", clause(struct("r"), list(struct("a"), var("X"))));
        singleAssert("[a,b|[c]] -> r.",
                clause(struct("r"), struct(".", struct("a"), struct(".", struct("b"), list(struct("c")))))
        );
        // Nested list
        singleAssert("shape([X,[3, \"wolf\"]], square) -> r.",
                clause(
                        struct("r"),
                        struct("shape", list(var("X"), list(number("3"), struct("\"wolf\""))), struct("square"))
                )
        );
        singleAssert("shape([X,[3, \"wolf\", []]], square) -> r.",
                clause(
                        struct("r"),
                        struct("shape", list(var("X"), list(number("3"), struct("\"wolf\""), list())), struct("square"))
                )
        );
    }

    @Test
    public void testMultiAtoms() {
        // Comma
        singleAssert("node{(X),(Y),(Z)}.",
                fact(and(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z"))))
        );
        singleAssert("node{X,Y,Z}.", fact(and(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z")))));
        singleAssert("node{X,(Y),(Z1, Z2)}.",
                fact(and(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z1"), var("Z2"))))
        );
        singleAssert("node{X, Y, Z} -> a.",
                clause(struct("a"), and(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z")))));
        // Semicolon
        singleAssert("node{X;Y;Z} -> a.",
                clause(struct("a"), or(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z"))))
        );
        singleAssert("node{X;(Y);(Z1, Z2)} -> a.",
                clause(
                        struct("a"),
                        or(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z1"), var("Z2")))
                )
        );
        // Grouping
        multAssert("wolf(X), node{X;Y;Z} -> a.",
                clause(
                        struct("a"),
                        and(
                                struct("wolf", var("X")),
                                or(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z")))
                        )
                )
        );
        multAssert("node{X;Y;Z}, wolf(X) -> a.",
                clause(
                        struct("a"),
                        and(
                                or(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z"))),
                                struct("wolf", var("X"))
                        )
                )
        );
        multAssert("wolf(X), node{X;Y;Z} -> check(X).",
                clause(
                        struct("check", var("X")),
                        and(
                                struct("wolf", var("X")),
                                or(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z"))))
                )
        );
        multAssert("node{(X),(A,(B1,B2;B3);C),Z} -> r.",
                clause(
                        struct("r"),
                        and(
                                struct("node", var("X")),
                                struct("node", var("A"), or(or(and(var("B1"), var("B2")), var("B3")), var("C"))),
                                struct("node", var("Z"))
                        )
                )
        );
    }

    @Test
    public void testLabelGen() {
        // Node labels
        /* The following example yields the hidden clause:
           mom(X) :- node(X), label(X, "mom").
         */
        singleAssert("node labels: \"mom\".",
                clause(
                        struct("mom", var("X")),
                        and(struct("node", var("X")), struct("label", var("X"), struct("\"mom\"")))
                )
        );
        singleAssert("node labels: \"mom\" as mother.",
                clause(
                        struct("mother", var("X")),
                        and(struct("node", var("X")), struct("label", var("X"), struct("\"mom\""))))
        );
        multAssert("node labels: \"mom\" as mother, \"dog\", \"*723^^& Illeg@l\" as legal.",
                clause(
                        struct("mother", var("X")),
                        and(struct("node", var("X")), struct("label", var("X"), struct("\"mom\"")))
                ),
                clause(
                        struct("dog", var("X")),
                        and(struct("node", var("X")), struct("label", var("X"), struct("\"dog\"")))
                ),
                clause(
                        struct("legal", var("X")),
                        and(struct("node", var("X")), struct("label", var("X"), struct("\"*723^^& Illeg@l\"")))
                )
        );
        // Edge labels
        /* The following example yields the hidden clauses:
           on(X)        :- edge(X), label(X, "on").
           on(X,Y)      :- edge(X,Y,Z), label(Z, "on").
           on(X,Y,Z)    :- edge(X,Y,Z), label(Z, "on"). */
        multAssert("edge labels: \"on\".",
                clause(
                        struct("on", var("X")),
                        and(struct("edge", var("X")), struct("label", var("X"), struct("\"on\"")))
                ),
                clause(
                        struct("on", var("X"), var("Y")),
                        and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct("\"on\"")))
                ),
                clause(
                        struct("on", var("X"), var("Y"), var("Z")),
                        and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct("\"on\"")))
                )
        );
        multAssert("edge labels: \"On*&\" as on.",
                clause(
                        struct("on", var("X")),
                        and(struct("edge", var("X")), struct("label", var("X"), struct("\"On*&\"")))
                ),
                clause(
                        struct("on", var("X"), var("Y")),
                        and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct("\"On*&\"")))
                ),
                clause(
                        struct("on", var("X"), var("Y"), var("Z")),
                        and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct("\"On*&\"")))
                )
        );
    }


    /**********************
     --- Helper methods ---
     **********************/

    // --- Custom assertions ---
    public static void singleAssert(String s, Term t) {
        assertEquals(inList(replaceVarsWithAtoms(t)), generate(s).stream().map(RuleGeneratorTest::replaceVarsWithAtoms).collect(Collectors.toList()));
    }

    private static Term replaceVarsWithAtoms(Term term) {
        if (term.isVar()) {
            return atom(term.castToVar().getName());
        } else if (term.isRule()) {
            List<Term> newBody = new ArrayList<>();
            term.castToRule().getBodyItems().forEach(x -> newBody.add(replaceVarsWithAtoms(x)));
            return Rule.of(replaceVarsWithAtoms(term.castToRule().getHead()).castToStruct(), newBody);
        } else if (term.isStruct()) {
            return Struct.of(term.castToStruct().getFunctor(), term.castToStruct().getArgs().stream().map(RuleGeneratorTest::replaceVarsWithAtoms).collect(Collectors.toList()));
        } else if (term.isInteger()) {
            return term;
        } else {
            throw new UnsupportedOperationException(term.getClass().getName());
        }
    }

    public static void multAssert(String s, Term... ts) {
        assertEquals(Arrays.stream(ts).map(RuleGeneratorTest::replaceVarsWithAtoms).collect(Collectors.toList()), generate(s).stream().map(RuleGeneratorTest::replaceVarsWithAtoms).collect(Collectors.toList()));
    }

    // --- Calling the rule generator ---

    public static List<Clause> generate(String script) {
        Lexer lexer = new GraafvisLexer(new ANTLRInputStream(script));
        TokenStream tokens = new CommonTokenStream(lexer);
        GraafvisParser parser = new GraafvisParser(tokens);
        GraafvisParser.ProgramContext tree = parser.program();
        RuleGenerator rg = new RuleGenerator(tree);
        return rg.getResult();
    }
}
