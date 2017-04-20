// Generated from C:/Users/Sven/Documents/GitHub/GraphterEffects/src/main/java/graafvis\Graafvis.g4 by ANTLR 4.6
package graafvis;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GraafvisParser}.
 */
public interface GraafvisListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(GraafvisParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(GraafvisParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#import_vis}.
	 * @param ctx the parse tree
	 */
	void enterImport_vis(GraafvisParser.Import_visContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#import_vis}.
	 * @param ctx the parse tree
	 */
	void exitImport_vis(GraafvisParser.Import_visContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#node_label_gen}.
	 * @param ctx the parse tree
	 */
	void enterNode_label_gen(GraafvisParser.Node_label_genContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#node_label_gen}.
	 * @param ctx the parse tree
	 */
	void exitNode_label_gen(GraafvisParser.Node_label_genContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#edge_label_gen}.
	 * @param ctx the parse tree
	 */
	void enterEdge_label_gen(GraafvisParser.Edge_label_genContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#edge_label_gen}.
	 * @param ctx the parse tree
	 */
	void exitEdge_label_gen(GraafvisParser.Edge_label_genContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel(GraafvisParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel(GraafvisParser.LabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#clause}.
	 * @param ctx the parse tree
	 */
	void enterClause(GraafvisParser.ClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#clause}.
	 * @param ctx the parse tree
	 */
	void exitClause(GraafvisParser.ClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#antecedent}.
	 * @param ctx the parse tree
	 */
	void enterAntecedent(GraafvisParser.AntecedentContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#antecedent}.
	 * @param ctx the parse tree
	 */
	void exitAntecedent(GraafvisParser.AntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pfNot}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 */
	void enterPfNot(GraafvisParser.PfNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pfNot}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 */
	void exitPfNot(GraafvisParser.PfNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pfLit}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 */
	void enterPfLit(GraafvisParser.PfLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pfLit}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 */
	void exitPfLit(GraafvisParser.PfLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pfAnd}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 */
	void enterPfAnd(GraafvisParser.PfAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pfAnd}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 */
	void exitPfAnd(GraafvisParser.PfAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pfOr}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 */
	void enterPfOr(GraafvisParser.PfOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pfOr}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 */
	void exitPfOr(GraafvisParser.PfOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pfNest}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 */
	void enterPfNest(GraafvisParser.PfNestContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pfNest}
	 * labeled alternative in {@link GraafvisParser#propositional_formula}.
	 * @param ctx the parse tree
	 */
	void exitPfNest(GraafvisParser.PfNestContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#consequence}.
	 * @param ctx the parse tree
	 */
	void enterConsequence(GraafvisParser.ConsequenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#consequence}.
	 * @param ctx the parse tree
	 */
	void exitConsequence(GraafvisParser.ConsequenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomLiteral}
	 * labeled alternative in {@link GraafvisParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterAtomLiteral(GraafvisParser.AtomLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomLiteral}
	 * labeled alternative in {@link GraafvisParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitAtomLiteral(GraafvisParser.AtomLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiAtomLiteral}
	 * labeled alternative in {@link GraafvisParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterMultiAtomLiteral(GraafvisParser.MultiAtomLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiAtomLiteral}
	 * labeled alternative in {@link GraafvisParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitMultiAtomLiteral(GraafvisParser.MultiAtomLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numExprLiteral}
	 * labeled alternative in {@link GraafvisParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterNumExprLiteral(GraafvisParser.NumExprLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numExprLiteral}
	 * labeled alternative in {@link GraafvisParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitNumExprLiteral(GraafvisParser.NumExprLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(GraafvisParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(GraafvisParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#multi_atom}.
	 * @param ctx the parse tree
	 */
	void enterMulti_atom(GraafvisParser.Multi_atomContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#multi_atom}.
	 * @param ctx the parse tree
	 */
	void exitMulti_atom(GraafvisParser.Multi_atomContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#predicate}.
	 * @param ctx the parse tree
	 */
	void enterPredicate(GraafvisParser.PredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#predicate}.
	 * @param ctx the parse tree
	 */
	void exitPredicate(GraafvisParser.PredicateContext ctx);
	/**
	 * Enter a parse tree produced by the {@code termGround}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTermGround(GraafvisParser.TermGroundContext ctx);
	/**
	 * Exit a parse tree produced by the {@code termGround}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTermGround(GraafvisParser.TermGroundContext ctx);
	/**
	 * Enter a parse tree produced by the {@code termVar}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTermVar(GraafvisParser.TermVarContext ctx);
	/**
	 * Exit a parse tree produced by the {@code termVar}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTermVar(GraafvisParser.TermVarContext ctx);
	/**
	 * Enter a parse tree produced by the {@code wildcard}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void enterWildcard(GraafvisParser.WildcardContext ctx);
	/**
	 * Exit a parse tree produced by the {@code wildcard}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void exitWildcard(GraafvisParser.WildcardContext ctx);
	/**
	 * Enter a parse tree produced by the {@code tuple}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTuple(GraafvisParser.TupleContext ctx);
	/**
	 * Exit a parse tree produced by the {@code tuple}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTuple(GraafvisParser.TupleContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#ground_term}.
	 * @param ctx the parse tree
	 */
	void enterGround_term(GraafvisParser.Ground_termContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#ground_term}.
	 * @param ctx the parse tree
	 */
	void exitGround_term(GraafvisParser.Ground_termContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#variable}.
	 * @param ctx the parse tree
	 */
	void enterVariable(GraafvisParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#variable}.
	 * @param ctx the parse tree
	 */
	void exitVariable(GraafvisParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#num_expr}.
	 * @param ctx the parse tree
	 */
	void enterNum_expr(GraafvisParser.Num_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#num_expr}.
	 * @param ctx the parse tree
	 */
	void exitNum_expr(GraafvisParser.Num_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#and_op}.
	 * @param ctx the parse tree
	 */
	void enterAnd_op(GraafvisParser.And_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#and_op}.
	 * @param ctx the parse tree
	 */
	void exitAnd_op(GraafvisParser.And_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#eq_op}.
	 * @param ctx the parse tree
	 */
	void enterEq_op(GraafvisParser.Eq_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#eq_op}.
	 * @param ctx the parse tree
	 */
	void exitEq_op(GraafvisParser.Eq_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#pow_op}.
	 * @param ctx the parse tree
	 */
	void enterPow_op(GraafvisParser.Pow_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#pow_op}.
	 * @param ctx the parse tree
	 */
	void exitPow_op(GraafvisParser.Pow_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#mult_op}.
	 * @param ctx the parse tree
	 */
	void enterMult_op(GraafvisParser.Mult_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#mult_op}.
	 * @param ctx the parse tree
	 */
	void exitMult_op(GraafvisParser.Mult_opContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#plus_op}.
	 * @param ctx the parse tree
	 */
	void enterPlus_op(GraafvisParser.Plus_opContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#plus_op}.
	 * @param ctx the parse tree
	 */
	void exitPlus_op(GraafvisParser.Plus_opContext ctx);
}