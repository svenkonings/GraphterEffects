// Generated from C:/Users/Sven/Documents/GitHub/GraphterEffects/src/main/java/graafvis\Graafvis.g4 by ANTLR 4.6
package graafvis;
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
	 * Visit a parse tree produced by {@link GraafvisParser#import_vis}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImport_vis(GraafvisParser.Import_visContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#node_label_gen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNode_label_gen(GraafvisParser.Node_label_genContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#edge_label_gen}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEdge_label_gen(GraafvisParser.Edge_label_genContext ctx);
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
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPfNot(GraafvisParser.PfNotContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pfLit}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPfLit(GraafvisParser.PfLitContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pfAnd}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPfAnd(GraafvisParser.PfAndContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pfOr}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPfOr(GraafvisParser.PfOrContext ctx);
	/**
	 * Visit a parse tree produced by the {@code pfNest}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
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
	 * Visit a parse tree produced by the {@code numExprLiteral}
	 * labeled alternative in {@link GraafvisParser#literal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumExprLiteral(GraafvisParser.NumExprLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(GraafvisParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#multi_atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulti_atom(GraafvisParser.Multi_atomContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#predicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPredicate(GraafvisParser.PredicateContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termGround}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermGround(GraafvisParser.TermGroundContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termVar}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermVar(GraafvisParser.TermVarContext ctx);
	/**
	 * Visit a parse tree produced by the {@code wildcard}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildcard(GraafvisParser.WildcardContext ctx);
	/**
	 * Visit a parse tree produced by the {@code tuple}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTuple(GraafvisParser.TupleContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#ground_term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGround_term(GraafvisParser.Ground_termContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#variable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariable(GraafvisParser.VariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#num_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNum_expr(GraafvisParser.Num_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#and_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAnd_op(GraafvisParser.And_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#eq_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEq_op(GraafvisParser.Eq_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#pow_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPow_op(GraafvisParser.Pow_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#mult_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMult_op(GraafvisParser.Mult_opContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#plus_op}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPlus_op(GraafvisParser.Plus_opContext ctx);
}