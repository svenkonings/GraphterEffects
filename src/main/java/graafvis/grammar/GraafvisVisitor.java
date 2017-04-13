// Generated from C:/Users/poesd_000/IdeaProjects/GraphterEffects/src/main/java/graafvis/grammar\Graafvis.g4 by ANTLR 4.6
package graafvis.grammar;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GraafvisParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GraafvisVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(GraafvisParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#importVis}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportVis(GraafvisParser.ImportVisContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#nodeLabelGen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNodeLabelGen(GraafvisParser.NodeLabelGenContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#edgeLabelGen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEdgeLabelGen(GraafvisParser.EdgeLabelGenContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(GraafvisParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#clause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClause(GraafvisParser.ClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#antecedent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAntecedent(GraafvisParser.AntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pfNot}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPfNot(GraafvisParser.PfNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pfLit}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPfLit(GraafvisParser.PfLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pfAnd}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPfAnd(GraafvisParser.PfAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pfOr}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPfOr(GraafvisParser.PfOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pfNest}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPfNest(GraafvisParser.PfNestContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#consequence}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConsequence(GraafvisParser.ConsequenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code atomLiteral}
	 * labeled alternative in {@link GraafvisParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtomLiteral(GraafvisParser.AtomLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiAtomLiteral}
	 * labeled alternative in {@link GraafvisParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiAtomLiteral(GraafvisParser.MultiAtomLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(GraafvisParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiAnd}
	 * labeled alternative in {@link GraafvisParser#multiAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiAnd(GraafvisParser.MultiAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiOr}
	 * labeled alternative in {@link GraafvisParser#multiAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiOr(GraafvisParser.MultiOrContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#multiTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiTerm(GraafvisParser.MultiTermContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#termTuple}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermTuple(GraafvisParser.TermTupleContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicate(GraafvisParser.PredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termVar}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermVar(GraafvisParser.TermVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termAtom}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermAtom(GraafvisParser.TermAtomContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termWildcard}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermWildcard(GraafvisParser.TermWildcardContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termString}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermString(GraafvisParser.TermStringContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termNumber}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermNumber(GraafvisParser.TermNumberContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termID}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermID(GraafvisParser.TermIDContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termList}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermList(GraafvisParser.TermListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(GraafvisParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#andOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndOp(GraafvisParser.AndOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#orOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrOp(GraafvisParser.OrOpContext ctx);
}