// Generated from C:/Users/Sven/Documents/GitHub/GraphterEffects/src/main/java/graafvis/grammar\Graafvis.g4 by ANTLR 4.6
package graafvis.grammar;
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
	 * Enter a parse tree produced by {@link GraafvisParser#importVis}.
	 * @param ctx the parse tree
	 */
	void enterImportVis(GraafvisParser.ImportVisContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#importVis}.
	 * @param ctx the parse tree
	 */
	void exitImportVis(GraafvisParser.ImportVisContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#nodeLabelGen}.
	 * @param ctx the parse tree
	 */
	void enterNodeLabelGen(GraafvisParser.NodeLabelGenContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#nodeLabelGen}.
	 * @param ctx the parse tree
	 */
	void exitNodeLabelGen(GraafvisParser.NodeLabelGenContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#edgeLabelGen}.
	 * @param ctx the parse tree
	 */
	void enterEdgeLabelGen(GraafvisParser.EdgeLabelGenContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#edgeLabelGen}.
	 * @param ctx the parse tree
	 */
	void exitEdgeLabelGen(GraafvisParser.EdgeLabelGenContext ctx);
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
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 */
	void enterPfNot(GraafvisParser.PfNotContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pfNot}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 */
	void exitPfNot(GraafvisParser.PfNotContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pfLit}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 */
	void enterPfLit(GraafvisParser.PfLitContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pfLit}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 */
	void exitPfLit(GraafvisParser.PfLitContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pfAnd}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 */
	void enterPfAnd(GraafvisParser.PfAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pfAnd}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 */
	void exitPfAnd(GraafvisParser.PfAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pfOr}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 */
	void enterPfOr(GraafvisParser.PfOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pfOr}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 */
	void exitPfOr(GraafvisParser.PfOrContext ctx);
	/**
	 * Enter a parse tree produced by the {@code pfNest}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
	 * @param ctx the parse tree
	 */
	void enterPfNest(GraafvisParser.PfNestContext ctx);
	/**
	 * Exit a parse tree produced by the {@code pfNest}
	 * labeled alternative in {@link GraafvisParser#propositionalFormula}.
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
	 * Enter a parse tree produced by the {@code multiAnd}
	 * labeled alternative in {@link GraafvisParser#multiAtom}.
	 * @param ctx the parse tree
	 */
	void enterMultiAnd(GraafvisParser.MultiAndContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiAnd}
	 * labeled alternative in {@link GraafvisParser#multiAtom}.
	 * @param ctx the parse tree
	 */
	void exitMultiAnd(GraafvisParser.MultiAndContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiOr}
	 * labeled alternative in {@link GraafvisParser#multiAtom}.
	 * @param ctx the parse tree
	 */
	void enterMultiOr(GraafvisParser.MultiOrContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiOr}
	 * labeled alternative in {@link GraafvisParser#multiAtom}.
	 * @param ctx the parse tree
	 */
	void exitMultiOr(GraafvisParser.MultiOrContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#multiTerm}.
	 * @param ctx the parse tree
	 */
	void enterMultiTerm(GraafvisParser.MultiTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#multiTerm}.
	 * @param ctx the parse tree
	 */
	void exitMultiTerm(GraafvisParser.MultiTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#termTuple}.
	 * @param ctx the parse tree
	 */
	void enterTermTuple(GraafvisParser.TermTupleContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#termTuple}.
	 * @param ctx the parse tree
	 */
	void exitTermTuple(GraafvisParser.TermTupleContext ctx);
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
	 * Enter a parse tree produced by the {@code termAtom}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTermAtom(GraafvisParser.TermAtomContext ctx);
	/**
	 * Exit a parse tree produced by the {@code termAtom}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTermAtom(GraafvisParser.TermAtomContext ctx);
	/**
	 * Enter a parse tree produced by the {@code termWildcard}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTermWildcard(GraafvisParser.TermWildcardContext ctx);
	/**
	 * Exit a parse tree produced by the {@code termWildcard}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTermWildcard(GraafvisParser.TermWildcardContext ctx);
	/**
	 * Enter a parse tree produced by the {@code termString}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTermString(GraafvisParser.TermStringContext ctx);
	/**
	 * Exit a parse tree produced by the {@code termString}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTermString(GraafvisParser.TermStringContext ctx);
	/**
	 * Enter a parse tree produced by the {@code termNumber}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTermNumber(GraafvisParser.TermNumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code termNumber}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTermNumber(GraafvisParser.TermNumberContext ctx);
	/**
	 * Enter a parse tree produced by the {@code termID}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTermID(GraafvisParser.TermIDContext ctx);
	/**
	 * Exit a parse tree produced by the {@code termID}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTermID(GraafvisParser.TermIDContext ctx);
	/**
	 * Enter a parse tree produced by the {@code termList}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTermList(GraafvisParser.TermListContext ctx);
	/**
	 * Exit a parse tree produced by the {@code termList}
	 * labeled alternative in {@link GraafvisParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTermList(GraafvisParser.TermListContext ctx);
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
	 * Enter a parse tree produced by {@link GraafvisParser#andOp}.
	 * @param ctx the parse tree
	 */
	void enterAndOp(GraafvisParser.AndOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#andOp}.
	 * @param ctx the parse tree
	 */
	void exitAndOp(GraafvisParser.AndOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#orOp}.
	 * @param ctx the parse tree
	 */
	void enterOrOp(GraafvisParser.OrOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#orOp}.
	 * @param ctx the parse tree
	 */
	void exitOrOp(GraafvisParser.OrOpContext ctx);
}