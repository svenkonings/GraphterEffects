// Generated from C:/Users/Sven/Documents/GitHub/GraphterEffects/src/main/java/graafvis/grammar\Graafvis.g4 by ANTLR 4.6
package com.github.meteoorkip.graafvis.grammar;
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
	 * Visit a parse tree produced by the {@code notAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotAntecedent(GraafvisParser.NotAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compoundAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundAntecedent(GraafvisParser.CompoundAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiAndCompoundAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiAndCompoundAntecedent(GraafvisParser.MultiAndCompoundAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiOrCompoundAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiOrCompoundAntecedent(GraafvisParser.MultiOrCompoundAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code listAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListAntecedent(GraafvisParser.ListAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParAntecedent(GraafvisParser.ParAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code variableAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableAntecedent(GraafvisParser.VariableAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code wildcardAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWildcardAntecedent(GraafvisParser.WildcardAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringAntecedent(GraafvisParser.StringAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberAntecedent(GraafvisParser.NumberAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#aArgSeries}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAArgSeries(GraafvisParser.AArgSeriesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#orSeries}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrSeries(GraafvisParser.OrSeriesContext ctx);
	/**
	 * Visit a parse tree produced by the {@code andExpressionAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTermExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAndExpressionAntecedent(GraafvisParser.AndExpressionAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parExpressionAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTermExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpressionAntecedent(GraafvisParser.ParExpressionAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code termExpressionAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTermExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTermExpressionAntecedent(GraafvisParser.TermExpressionAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by the {@code orExpressionAntecedent}
	 * labeled alternative in {@link GraafvisParser#aTermExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOrExpressionAntecedent(GraafvisParser.OrExpressionAntecedentContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#aMultiArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAMultiArg(GraafvisParser.AMultiArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code compoundConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompoundConsequence(GraafvisParser.CompoundConsequenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code multiCompoundConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiCompoundConsequence(GraafvisParser.MultiCompoundConsequenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code listConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListConsequence(GraafvisParser.ListConsequenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code variableConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableConsequence(GraafvisParser.VariableConsequenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringConsequence(GraafvisParser.StringConsequenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberConsequence}
	 * labeled alternative in {@link GraafvisParser#cTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberConsequence(GraafvisParser.NumberConsequenceContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#cArgSeries}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCArgSeries(GraafvisParser.CArgSeriesContext ctx);
	/**
	 * Visit a parse tree produced by {@link GraafvisParser#cMultiArg}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCMultiArg(GraafvisParser.CMultiArgContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idFunctor}
	 * labeled alternative in {@link GraafvisParser#functor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdFunctor(GraafvisParser.IdFunctorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code infixFunctor}
	 * labeled alternative in {@link GraafvisParser#functor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitInfixFunctor(GraafvisParser.InfixFunctorContext ctx);
}