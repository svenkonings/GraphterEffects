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
	 * Enter a parse tree produced by the {@code notAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void enterNotAntecedent(GraafvisParser.NotAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void exitNotAntecedent(GraafvisParser.NotAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compoundAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void enterCompoundAntecedent(GraafvisParser.CompoundAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compoundAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void exitCompoundAntecedent(GraafvisParser.CompoundAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiAndCompoundAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void enterMultiAndCompoundAntecedent(GraafvisParser.MultiAndCompoundAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiAndCompoundAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void exitMultiAndCompoundAntecedent(GraafvisParser.MultiAndCompoundAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiOrCompoundAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void enterMultiOrCompoundAntecedent(GraafvisParser.MultiOrCompoundAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiOrCompoundAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void exitMultiOrCompoundAntecedent(GraafvisParser.MultiOrCompoundAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code listAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void enterListAntecedent(GraafvisParser.ListAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code listAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void exitListAntecedent(GraafvisParser.ListAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void enterParAntecedent(GraafvisParser.ParAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void exitParAntecedent(GraafvisParser.ParAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code variableAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void enterVariableAntecedent(GraafvisParser.VariableAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variableAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void exitVariableAntecedent(GraafvisParser.VariableAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code wildcardAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void enterWildcardAntecedent(GraafvisParser.WildcardAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code wildcardAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void exitWildcardAntecedent(GraafvisParser.WildcardAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void enterStringAntecedent(GraafvisParser.StringAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void exitStringAntecedent(GraafvisParser.StringAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void enterNumberAntecedent(GraafvisParser.NumberAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 */
	void exitNumberAntecedent(GraafvisParser.NumberAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#aArgSeries}.
	 * @param ctx the parse tree
	 */
	void enterAArgSeries(GraafvisParser.AArgSeriesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#aArgSeries}.
	 * @param ctx the parse tree
	 */
	void exitAArgSeries(GraafvisParser.AArgSeriesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#orSeries}.
	 * @param ctx the parse tree
	 */
	void enterOrSeries(GraafvisParser.OrSeriesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#orSeries}.
	 * @param ctx the parse tree
	 */
	void exitOrSeries(GraafvisParser.OrSeriesContext ctx);
	/**
	 * Enter a parse tree produced by the {@code andExpressionAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTermExpr}.
	 * @param ctx the parse tree
	 */
	void enterAndExpressionAntecedent(GraafvisParser.AndExpressionAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code andExpressionAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTermExpr}.
	 * @param ctx the parse tree
	 */
	void exitAndExpressionAntecedent(GraafvisParser.AndExpressionAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parExpressionAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTermExpr}.
	 * @param ctx the parse tree
	 */
	void enterParExpressionAntecedent(GraafvisParser.ParExpressionAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExpressionAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTermExpr}.
	 * @param ctx the parse tree
	 */
	void exitParExpressionAntecedent(GraafvisParser.ParExpressionAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code termExpressionAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTermExpr}.
	 * @param ctx the parse tree
	 */
	void enterTermExpressionAntecedent(GraafvisParser.TermExpressionAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code termExpressionAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTermExpr}.
	 * @param ctx the parse tree
	 */
	void exitTermExpressionAntecedent(GraafvisParser.TermExpressionAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code orExpressionAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTermExpr}.
	 * @param ctx the parse tree
	 */
	void enterOrExpressionAntecedent(GraafvisParser.OrExpressionAntecedentContext ctx);
	/**
	 * Exit a parse tree produced by the {@code orExpressionAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTermExpr}.
	 * @param ctx the parse tree
	 */
	void exitOrExpressionAntecedent(GraafvisParser.OrExpressionAntecedentContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#aMultiArg}.
	 * @param ctx the parse tree
	 */
	void enterAMultiArg(GraafvisParser.AMultiArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#aMultiArg}.
	 * @param ctx the parse tree
	 */
	void exitAMultiArg(GraafvisParser.AMultiArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code compoundConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 */
	void enterCompoundConsequence(GraafvisParser.CompoundConsequenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code compoundConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 */
	void exitCompoundConsequence(GraafvisParser.CompoundConsequenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code multiCompoundConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 */
	void enterMultiCompoundConsequence(GraafvisParser.MultiCompoundConsequenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code multiCompoundConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 */
	void exitMultiCompoundConsequence(GraafvisParser.MultiCompoundConsequenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code listConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 */
	void enterListConsequence(GraafvisParser.ListConsequenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code listConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 */
	void exitListConsequence(GraafvisParser.ListConsequenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code variableConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 */
	void enterVariableConsequence(GraafvisParser.VariableConsequenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variableConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 */
	void exitVariableConsequence(GraafvisParser.VariableConsequenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 */
	void enterStringConsequence(GraafvisParser.StringConsequenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 */
	void exitStringConsequence(GraafvisParser.StringConsequenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 */
	void enterNumberConsequence(GraafvisParser.NumberConsequenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 */
	void exitNumberConsequence(GraafvisParser.NumberConsequenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#cArgSeries}.
	 * @param ctx the parse tree
	 */
	void enterCArgSeries(GraafvisParser.CArgSeriesContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#cArgSeries}.
	 * @param ctx the parse tree
	 */
	void exitCArgSeries(GraafvisParser.CArgSeriesContext ctx);
	/**
	 * Enter a parse tree produced by {@link GraafvisParser#cMultiArg}.
	 * @param ctx the parse tree
	 */
	void enterCMultiArg(GraafvisParser.CMultiArgContext ctx);
	/**
	 * Exit a parse tree produced by {@link GraafvisParser#cMultiArg}.
	 * @param ctx the parse tree
	 */
	void exitCMultiArg(GraafvisParser.CMultiArgContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idFunctor}
	 * labeled alternative in {@link GraafvisParser#functor}.
	 * @param ctx the parse tree
	 */
	void enterIdFunctor(GraafvisParser.IdFunctorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idFunctor}
	 * labeled alternative in {@link GraafvisParser#functor}.
	 * @param ctx the parse tree
	 */
	void exitIdFunctor(GraafvisParser.IdFunctorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code infixFunctor}
	 * labeled alternative in {@link GraafvisParser#functor}.
	 * @param ctx the parse tree
	 */
	void enterInfixFunctor(GraafvisParser.InfixFunctorContext ctx);
	/**
	 * Exit a parse tree produced by the {@code infixFunctor}
	 * labeled alternative in {@link GraafvisParser#functor}.
	 * @param ctx the parse tree
	 */
	void exitInfixFunctor(GraafvisParser.InfixFunctorContext ctx);
}