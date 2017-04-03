package graafvis;

import alice.tuprolog.Term;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static compiler.prolog.TuProlog.*;
import static compiler.prolog.TuProlog.struct;
import static graafvis.RuleGenerator.TUP_NOT;
import static graafvis.RuleGenerator.generate;
import static org.junit.Assert.assertEquals;

/**
 * Created by Lindsay on 31-Mar-17.
 */
public class RuleGeneratorTest {

    @Test
    public void testFacts() {
        singleAssert("p(X).", struct("p", var("X")));
        singleAssert("p(X,Y).", struct("p", var("X"), var("Y")));
        multAssert("p(X), p(Y).", struct("p", var("X")), struct("p", var("Y")));
        // List
        singleAssert("p([X,Y]).", struct("p", list(var("X"), var("Y"))));
        // arg1: list, arg2: constant
        singleAssert("shape([X,Y], square).",
                struct("shape", list(var("X"), var("Y")), struct("square"))
        );
        // Nested list
        singleAssert("shape([X,[3, \"wolf\"]], square).",
                struct("shape", list(var("X"), list(number("3"), struct("\"wolf\""))), struct("square"))
        );
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

        // Not
        singleAssert("not p(X) -> q(X).", clause(struct(TUP_NOT, struct("p", var("X"))), struct("q", var("X"))));
        singleAssert("not not p(X) -> q(X).", clause(struct(TUP_NOT, struct(TUP_NOT, struct("p", var("X")))), struct("q", var("X"))));
        singleAssert("not p(X), q(X) -> r(X).",
                clause(and(struct(TUP_NOT, struct("p", var("X"))), struct("q", var("X"))), struct("r", var("X")))
        );
        singleAssert("p(X), not q(X) -> r(X).",
                clause(and(struct("p", var("X"), struct(TUP_NOT, struct("q", var("X"))))), struct("r", var("X")))
        );

    }

    @Test // TODO
    public void testMultiAtoms() {
        // Comma
        singleAssert("node{X,Y,Z}.", and(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z"))));
        singleAssert("node{X,(Y),(Z1, Z2)}.",
                and(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z1"), var("Z2")))
        );
        // Semicolon
        singleAssert("node{X;Y;Z}.", or(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z"))));
        singleAssert("node{X;(Y);(Z1, Z2)}.",
                or(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z1"), var("Z2")))
        );
        // And (+ mixed)
        singleAssert("node{X and Y and Z}.", and(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z"))));
        singleAssert("node{X and (Y), (Z1, Z2)}.",
                and(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z1"), var("Z2")))
        );
        // Or (+ mixed)
        singleAssert("node{X ; Y or Z}.", or(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z"))));
        singleAssert("node{X or (Y) or (Z1, Z2)}.",
                or(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z1"), var("Z2")))
        );
        // Grouping
        multAssert("wolf(X), node{X;Y;Z}.",
                struct("wolf", var("X")),
                or(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z")))
        );
        multAssert("node{X;Y;Z}, wolf(X).",
                or(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z"))),
                struct("wolf", var("X"))
        );
        multAssert("wolf(X), node{X;Y;Z} -> check(X).",
                clause(
                        and(struct("wolf", var("X")), or(struct("node", var("X")), struct("node", var("Y")), struct("node", var("Z")))),
                        struct("check", var("X"))
                )
        );
    }

    // --- Little help for tests ---
    public static List<Term> inList(Term... ts) {
        return Arrays.asList(ts);
    }

    public static void singleAssert(String s, Term t) {
        assertEquals(inList(t), generate(s));
    }

    public static void multAssert(String s, Term... ts) {
        assertEquals(Arrays.asList(ts), generate(s));
    }
}
