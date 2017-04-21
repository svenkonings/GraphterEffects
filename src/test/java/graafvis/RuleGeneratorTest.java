package graafvis;

import alice.tuprolog.Term;
import org.junit.Test;

import java.util.Arrays;

import static prolog.TuProlog.*;
import static graafvis.generator.RuleGenerator.TUP_NOT;
import static graafvis.generator.RuleGenerator.generate;
import static graafvis.generator.RuleGenerator.inList;
import static org.junit.Assert.assertEquals;

public class RuleGeneratorTest {

    @Test
    public void testFacts() {
        singleAssert("p(X).", struct("p", var("X")));
        singleAssert("p(X,Y).", struct("p", var("X"), var("Y")));
        multAssert("p(X), p(Y).", struct("p", var("X")), struct("p", var("Y")));
        // TODO LISTS
        // List
        singleAssert("p([X,Y]).", struct("p", list(var("X"), var("Y"))));
        // arg1: list, arg2: constant
        singleAssert("shape([X,Y], square).",
                struct("shape", list(var("X"), var("Y")), struct("square"))
        );
        singleAssert("shape([], square).",
                struct("shape", list(), struct("square"))
        );
        // Nested list
        singleAssert("shape([X,[3, \"wolf\"]], square).",
                struct("shape", list(var("X"), list(number("3"), struct("\"wolf\""))), struct("square"))
        );
        singleAssert("shape([X,[3, \"wolf\", []]], square).",
                struct("shape", list(var("X"), list(number("3"), struct("\"wolf\""), list())), struct("square"))
        );
        // TODO head & tail lists
        // TODO Find way to test wildcards
    }

    @Test
    public void testRules() {
        singleAssert("p(X) -> q(X).", clause(struct("q", var("X")), struct("p", var("X"))));

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
                        or(or(struct("p", var("X")), and(struct("q", var("X")), struct("r", var("X")))), struct("s", var("X")))
                )
        );

        // Nest
        singleAssert("p(X), (q(X); r(X)) -> s(X).",
                clause(
                        struct("s", var("X")),
                        and(struct("p", var("X")), or(struct("q", var("X")), struct("r", var("X"))))
                )
        );
        singleAssert("p(X), (q(X); (r(X), s(X))) -> t(X).",
                clause(
                        struct("t", var("X")),
                        and(struct("p", var("X")), or(struct("q", var("X")), and(struct("r", var("X")), struct("s", var("X")))))
                )
        );

        // Not
        singleAssert("not p(X) -> q(X).", clause(struct("q", var("X")), struct(TUP_NOT, struct("p", var("X")))));
        singleAssert("not not p(X) -> q(X).", clause(struct("q", var("X")), struct(TUP_NOT, struct(TUP_NOT, struct("p", var("X"))))));
        singleAssert("not p(X), q(X) -> r(X).",
                clause(struct("r", var("X")), and(struct(TUP_NOT, struct("p", var("X"))), struct("q", var("X"))))
        );
        singleAssert("p(X), not q(X) -> r(X).",
                clause(struct("r", var("X")), and(struct("p", var("X")), struct(TUP_NOT, struct("q", var("X")))))
        );

        // List
        singleAssert("p([X,Y]) -> r.", clause(struct("r"), struct("p", list(var("X"), var("Y")))));
        // arg1: list, arg2: constant
        singleAssert("shape([X,Y], square) -> r.",
                clause(struct("r"), struct("shape", list(var("X"), var("Y")), struct("square")))
        );
        singleAssert("shape([], square) -> r.",
                clause(struct("r"), struct("shape", list(), struct("square")))
        );
        // Nested list
        singleAssert("shape([X,[3, \"wolf\"]], square) -> r.",
                clause(struct("r"), struct("shape", list(var("X"), list(number("3"), struct("\"wolf\""))), struct("square")))
        );
        singleAssert("shape([X,[3, \"wolf\", []]], square) -> r.",
                clause(struct("r"), struct("shape", list(var("X"), list(number("3"), struct("\"wolf\""), list())), struct("square")))
        );
    }

    @Test
    public void testMultiAtoms() {
        // Comma
        singleAssert("node{(X),(Y),(Z)}.", and(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z"))));
        singleAssert("node{X,Y,Z}.", and(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z"))));
        singleAssert("node{X,(Y),(Z1, Z2)}.",
                and(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z1"), var("Z2")))
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
        // mom(X) :- node(X), label(X, "mom").
        singleAssert("node labels: \"mom\".",
                clause(struct("mom", var("X")), and(struct("node", var("X")), struct("label", var("X"), struct("\"mom\""))))
        );
        singleAssert("node labels: \"mom\" as mother.",
                clause(struct("mother", var("X")), and(struct("node", var("X")), struct("label", var("X"), struct("\"mom\""))))
        );
        multAssert("node labels: \"mom\" as mother, \"dog\", \"*723^^& Illeg@l\" as legal.",
                clause(struct("mother", var("X")), and(struct("node", var("X")), struct("label", var("X"), struct("\"mom\"")))),
                clause(struct("dog", var("X")), and(struct("node", var("X")), struct("label", var("X"), struct("\"dog\"")))),
                clause(struct("legal", var("X")), and(struct("node", var("X")), struct("label", var("X"), struct("\"*723^^& Illeg@l\""))))
        );
        // Edge labels
        /* on(X)        :- edge(X), label(X, "on").
           on(X,Y)      :- edge(X,Y,Z), label(Z, "on").
           on(X,Y,Z)    :- edge(X,Y,Z), label(Z, "on"). */
        multAssert("edge labels: \"on\".",
                clause(struct("on", var("X")), and(struct("edge", var("X")), struct("label", var("X"), struct("\"on\"")))),
                clause(struct("on", var("X"), var("Y")), and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct("\"on\"")))),
                clause(struct("on", var("X"), var("Y"), var("Z")), and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct("\"on\""))))
        );
        multAssert("edge labels: \"On*&\" as on.",
                clause(struct("on", var("X")), and(struct("edge", var("X")), struct("label", var("X"), struct("\"On*&\"")))),
                clause(struct("on", var("X"), var("Y")), and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct("\"On*&\"")))),
                clause(struct("on", var("X"), var("Y"), var("Z")), and(struct("edge", var("X"), var("Y"), var("Z")), struct("label", var("Z"), struct("\"On*&\""))))
        );
    }

    // --- Little help for tests ---

    public static void singleAssert(String s, Term t) {
        System.out.println(s);
        assertEquals(inList(t), generate(s));
        System.out.println("\tSuccess!");
    }

    public static void multAssert(String s, Term... ts) {
        assertEquals(Arrays.asList(ts), generate(s));
    }
}
