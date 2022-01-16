// Generated from C:/Users/poesd/IdeaProjects/GraphterEffectsOld/GraphterEffectsLibrary/src/main/java/com/github/meteoorkip/graafvis/grammar\Graafvis.g4 by ANTLR 4.9.2
package com.github.meteoorkip.graafvis.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GraafvisParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ARROW=1, COLON=2, EOL=3, PAR_OPEN=4, PAR_CLOSE=5, BRACE_OPEN=6, BRACE_CLOSE=7, 
		BRACKET_OPEN=8, BRACKET_CLOSE=9, VBAR=10, COMMA=11, SEMICOLON=12, NOT=13, 
		MINUS=14, UNDERSCORE=15, IMPORT_TOKEN=16, NODE_LABEL_TOKEN=17, EDGE_LABEL_TOKEN=18, 
		RENAME_TOKEN=19, STRING=20, NUMBER=21, ID=22, HID=23, INFIX_ID=24, WS=25, 
		BLOCKCOMMENT=26, LINECOMMENT=27;
	public static final int
		RULE_program = 0, RULE_importVis = 1, RULE_nodeLabelGen = 2, RULE_edgeLabelGen = 3, 
		RULE_label = 4, RULE_clause = 5, RULE_aTerm = 6, RULE_aArgSeries = 7, 
		RULE_orSeries = 8, RULE_aTermExpr = 9, RULE_aMultiArg = 10, RULE_cTerm = 11, 
		RULE_cArgSeries = 12, RULE_cMultiArg = 13, RULE_functor = 14;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "importVis", "nodeLabelGen", "edgeLabelGen", "label", "clause", 
			"aTerm", "aArgSeries", "orSeries", "aTermExpr", "aMultiArg", "cTerm", 
			"cArgSeries", "cMultiArg", "functor"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'->'", "':'", "'.'", "'('", "')'", "'{'", "'}'", "'['", "']'", 
			"'|'", "','", "';'", "'not'", "'-'", "'_'", "'consult'", "'node labels'", 
			"'edge labels'", "'as'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ARROW", "COLON", "EOL", "PAR_OPEN", "PAR_CLOSE", "BRACE_OPEN", 
			"BRACE_CLOSE", "BRACKET_OPEN", "BRACKET_CLOSE", "VBAR", "COMMA", "SEMICOLON", 
			"NOT", "MINUS", "UNDERSCORE", "IMPORT_TOKEN", "NODE_LABEL_TOKEN", "EDGE_LABEL_TOKEN", 
			"RENAME_TOKEN", "STRING", "NUMBER", "ID", "HID", "INFIX_ID", "WS", "BLOCKCOMMENT", 
			"LINECOMMENT"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Graafvis.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GraafvisParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(GraafvisParser.EOF, 0); }
		public List<ImportVisContext> importVis() {
			return getRuleContexts(ImportVisContext.class);
		}
		public ImportVisContext importVis(int i) {
			return getRuleContext(ImportVisContext.class,i);
		}
		public NodeLabelGenContext nodeLabelGen() {
			return getRuleContext(NodeLabelGenContext.class,0);
		}
		public EdgeLabelGenContext edgeLabelGen() {
			return getRuleContext(EdgeLabelGenContext.class,0);
		}
		public List<ClauseContext> clause() {
			return getRuleContexts(ClauseContext.class);
		}
		public ClauseContext clause(int i) {
			return getRuleContext(ClauseContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterProgram(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitProgram(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitProgram(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT_TOKEN) {
				{
				{
				setState(30);
				importVis();
				}
				}
				setState(35);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NODE_LABEL_TOKEN) {
				{
				setState(36);
				nodeLabelGen();
				}
			}

			setState(40);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EDGE_LABEL_TOKEN) {
				{
				setState(39);
				edgeLabelGen();
				}
			}

			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PAR_OPEN) | (1L << BRACKET_OPEN) | (1L << NOT) | (1L << UNDERSCORE) | (1L << STRING) | (1L << NUMBER) | (1L << ID) | (1L << HID) | (1L << INFIX_ID))) != 0)) {
				{
				{
				setState(42);
				clause();
				}
				}
				setState(47);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(48);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ImportVisContext extends ParserRuleContext {
		public TerminalNode IMPORT_TOKEN() { return getToken(GraafvisParser.IMPORT_TOKEN, 0); }
		public TerminalNode STRING() { return getToken(GraafvisParser.STRING, 0); }
		public TerminalNode EOL() { return getToken(GraafvisParser.EOL, 0); }
		public ImportVisContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_importVis; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterImportVis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitImportVis(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitImportVis(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ImportVisContext importVis() throws RecognitionException {
		ImportVisContext _localctx = new ImportVisContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_importVis);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(50);
			match(IMPORT_TOKEN);
			setState(51);
			match(STRING);
			setState(52);
			match(EOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NodeLabelGenContext extends ParserRuleContext {
		public LabelContext label;
		public List<LabelContext> labels = new ArrayList<LabelContext>();
		public TerminalNode NODE_LABEL_TOKEN() { return getToken(GraafvisParser.NODE_LABEL_TOKEN, 0); }
		public TerminalNode COLON() { return getToken(GraafvisParser.COLON, 0); }
		public TerminalNode EOL() { return getToken(GraafvisParser.EOL, 0); }
		public List<LabelContext> label() {
			return getRuleContexts(LabelContext.class);
		}
		public LabelContext label(int i) {
			return getRuleContext(LabelContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GraafvisParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraafvisParser.COMMA, i);
		}
		public NodeLabelGenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeLabelGen; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterNodeLabelGen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitNodeLabelGen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitNodeLabelGen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NodeLabelGenContext nodeLabelGen() throws RecognitionException {
		NodeLabelGenContext _localctx = new NodeLabelGenContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_nodeLabelGen);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			match(NODE_LABEL_TOKEN);
			setState(55);
			match(COLON);
			setState(56);
			((NodeLabelGenContext)_localctx).label = label();
			((NodeLabelGenContext)_localctx).labels.add(((NodeLabelGenContext)_localctx).label);
			setState(61);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(57);
				match(COMMA);
				setState(58);
				((NodeLabelGenContext)_localctx).label = label();
				((NodeLabelGenContext)_localctx).labels.add(((NodeLabelGenContext)_localctx).label);
				}
				}
				setState(63);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(64);
			match(EOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EdgeLabelGenContext extends ParserRuleContext {
		public LabelContext label;
		public List<LabelContext> labels = new ArrayList<LabelContext>();
		public TerminalNode EDGE_LABEL_TOKEN() { return getToken(GraafvisParser.EDGE_LABEL_TOKEN, 0); }
		public TerminalNode COLON() { return getToken(GraafvisParser.COLON, 0); }
		public TerminalNode EOL() { return getToken(GraafvisParser.EOL, 0); }
		public List<LabelContext> label() {
			return getRuleContexts(LabelContext.class);
		}
		public LabelContext label(int i) {
			return getRuleContext(LabelContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GraafvisParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraafvisParser.COMMA, i);
		}
		public EdgeLabelGenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edgeLabelGen; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterEdgeLabelGen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitEdgeLabelGen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitEdgeLabelGen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EdgeLabelGenContext edgeLabelGen() throws RecognitionException {
		EdgeLabelGenContext _localctx = new EdgeLabelGenContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_edgeLabelGen);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(66);
			match(EDGE_LABEL_TOKEN);
			setState(67);
			match(COLON);
			setState(68);
			((EdgeLabelGenContext)_localctx).label = label();
			((EdgeLabelGenContext)_localctx).labels.add(((EdgeLabelGenContext)_localctx).label);
			setState(73);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(69);
				match(COMMA);
				setState(70);
				((EdgeLabelGenContext)_localctx).label = label();
				((EdgeLabelGenContext)_localctx).labels.add(((EdgeLabelGenContext)_localctx).label);
				}
				}
				setState(75);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(76);
			match(EOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(GraafvisParser.STRING, 0); }
		public TerminalNode RENAME_TOKEN() { return getToken(GraafvisParser.RENAME_TOKEN, 0); }
		public TerminalNode ID() { return getToken(GraafvisParser.ID, 0); }
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterLabel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitLabel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_label);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(STRING);
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==RENAME_TOKEN) {
				{
				setState(79);
				match(RENAME_TOKEN);
				setState(80);
				match(ID);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ClauseContext extends ParserRuleContext {
		public ATermExprContext antecedent;
		public CArgSeriesContext consequence;
		public TerminalNode EOL() { return getToken(GraafvisParser.EOL, 0); }
		public CArgSeriesContext cArgSeries() {
			return getRuleContext(CArgSeriesContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(GraafvisParser.ARROW, 0); }
		public ATermExprContext aTermExpr() {
			return getRuleContext(ATermExprContext.class,0);
		}
		public ClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_clause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ClauseContext clause() throws RecognitionException {
		ClauseContext _localctx = new ClauseContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_clause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(86);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(83);
				((ClauseContext)_localctx).antecedent = aTermExpr(0);
				setState(84);
				match(ARROW);
				}
				break;
			}
			setState(88);
			((ClauseContext)_localctx).consequence = cArgSeries();
			setState(89);
			match(EOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ATermContext extends ParserRuleContext {
		public ATermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aTerm; }
	 
		public ATermContext() { }
		public void copyFrom(ATermContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class WildcardAntecedentContext extends ATermContext {
		public Token wildcard;
		public TerminalNode UNDERSCORE() { return getToken(GraafvisParser.UNDERSCORE, 0); }
		public WildcardAntecedentContext(ATermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterWildcardAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitWildcardAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitWildcardAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ListAntecedentContext extends ATermContext {
		public List<TerminalNode> BRACKET_OPEN() { return getTokens(GraafvisParser.BRACKET_OPEN); }
		public TerminalNode BRACKET_OPEN(int i) {
			return getToken(GraafvisParser.BRACKET_OPEN, i);
		}
		public List<TerminalNode> BRACKET_CLOSE() { return getTokens(GraafvisParser.BRACKET_CLOSE); }
		public TerminalNode BRACKET_CLOSE(int i) {
			return getToken(GraafvisParser.BRACKET_CLOSE, i);
		}
		public AArgSeriesContext aArgSeries() {
			return getRuleContext(AArgSeriesContext.class,0);
		}
		public TerminalNode VBAR() { return getToken(GraafvisParser.VBAR, 0); }
		public ATermContext aTerm() {
			return getRuleContext(ATermContext.class,0);
		}
		public ListAntecedentContext(ATermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterListAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitListAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitListAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NumberAntecedentContext extends ATermContext {
		public TerminalNode NUMBER() { return getToken(GraafvisParser.NUMBER, 0); }
		public NumberAntecedentContext(ATermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterNumberAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitNumberAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitNumberAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultiAndCompoundAntecedentContext extends ATermContext {
		public AMultiArgContext aMultiArg;
		public List<AMultiArgContext> args = new ArrayList<AMultiArgContext>();
		public FunctorContext functor() {
			return getRuleContext(FunctorContext.class,0);
		}
		public TerminalNode BRACE_OPEN() { return getToken(GraafvisParser.BRACE_OPEN, 0); }
		public TerminalNode BRACE_CLOSE() { return getToken(GraafvisParser.BRACE_CLOSE, 0); }
		public List<AMultiArgContext> aMultiArg() {
			return getRuleContexts(AMultiArgContext.class);
		}
		public AMultiArgContext aMultiArg(int i) {
			return getRuleContext(AMultiArgContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GraafvisParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraafvisParser.COMMA, i);
		}
		public MultiAndCompoundAntecedentContext(ATermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterMultiAndCompoundAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitMultiAndCompoundAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitMultiAndCompoundAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableAntecedentContext extends ATermContext {
		public Token variable;
		public TerminalNode HID() { return getToken(GraafvisParser.HID, 0); }
		public VariableAntecedentContext(ATermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterVariableAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitVariableAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitVariableAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NotAntecedentContext extends ATermContext {
		public TerminalNode NOT() { return getToken(GraafvisParser.NOT, 0); }
		public ATermContext aTerm() {
			return getRuleContext(ATermContext.class,0);
		}
		public NotAntecedentContext(ATermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterNotAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitNotAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitNotAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultiOrCompoundAntecedentContext extends ATermContext {
		public AMultiArgContext aMultiArg;
		public List<AMultiArgContext> args = new ArrayList<AMultiArgContext>();
		public FunctorContext functor() {
			return getRuleContext(FunctorContext.class,0);
		}
		public TerminalNode BRACE_OPEN() { return getToken(GraafvisParser.BRACE_OPEN, 0); }
		public TerminalNode BRACE_CLOSE() { return getToken(GraafvisParser.BRACE_CLOSE, 0); }
		public List<AMultiArgContext> aMultiArg() {
			return getRuleContexts(AMultiArgContext.class);
		}
		public AMultiArgContext aMultiArg(int i) {
			return getRuleContext(AMultiArgContext.class,i);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(GraafvisParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(GraafvisParser.SEMICOLON, i);
		}
		public MultiOrCompoundAntecedentContext(ATermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterMultiOrCompoundAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitMultiOrCompoundAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitMultiOrCompoundAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParAntecedentContext extends ATermContext {
		public TerminalNode PAR_OPEN() { return getToken(GraafvisParser.PAR_OPEN, 0); }
		public ATermExprContext aTermExpr() {
			return getRuleContext(ATermExprContext.class,0);
		}
		public TerminalNode PAR_CLOSE() { return getToken(GraafvisParser.PAR_CLOSE, 0); }
		public ParAntecedentContext(ATermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterParAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitParAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitParAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CompoundAntecedentContext extends ATermContext {
		public AArgSeriesContext args;
		public FunctorContext functor() {
			return getRuleContext(FunctorContext.class,0);
		}
		public TerminalNode PAR_OPEN() { return getToken(GraafvisParser.PAR_OPEN, 0); }
		public TerminalNode PAR_CLOSE() { return getToken(GraafvisParser.PAR_CLOSE, 0); }
		public AArgSeriesContext aArgSeries() {
			return getRuleContext(AArgSeriesContext.class,0);
		}
		public CompoundAntecedentContext(ATermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterCompoundAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitCompoundAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitCompoundAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringAntecedentContext extends ATermContext {
		public TerminalNode STRING() { return getToken(GraafvisParser.STRING, 0); }
		public StringAntecedentContext(ATermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterStringAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitStringAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitStringAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ATermContext aTerm() throws RecognitionException {
		ATermContext _localctx = new ATermContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_aTerm);
		int _la;
		try {
			int _alt;
			setState(148);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
			case 1:
				_localctx = new NotAntecedentContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(91);
				match(NOT);
				setState(92);
				aTerm();
				}
				break;
			case 2:
				_localctx = new CompoundAntecedentContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(93);
				functor();
				setState(99);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
				case 1:
					{
					setState(94);
					match(PAR_OPEN);
					setState(96);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PAR_OPEN) | (1L << BRACKET_OPEN) | (1L << NOT) | (1L << UNDERSCORE) | (1L << STRING) | (1L << NUMBER) | (1L << ID) | (1L << HID) | (1L << INFIX_ID))) != 0)) {
						{
						setState(95);
						((CompoundAntecedentContext)_localctx).args = aArgSeries();
						}
					}

					setState(98);
					match(PAR_CLOSE);
					}
					break;
				}
				}
				break;
			case 3:
				_localctx = new MultiAndCompoundAntecedentContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(101);
				functor();
				setState(102);
				match(BRACE_OPEN);
				setState(108);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(103);
						((MultiAndCompoundAntecedentContext)_localctx).aMultiArg = aMultiArg();
						((MultiAndCompoundAntecedentContext)_localctx).args.add(((MultiAndCompoundAntecedentContext)_localctx).aMultiArg);
						setState(104);
						match(COMMA);
						}
						} 
					}
					setState(110);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
				}
				setState(111);
				((MultiAndCompoundAntecedentContext)_localctx).aMultiArg = aMultiArg();
				((MultiAndCompoundAntecedentContext)_localctx).args.add(((MultiAndCompoundAntecedentContext)_localctx).aMultiArg);
				setState(112);
				match(BRACE_CLOSE);
				}
				break;
			case 4:
				_localctx = new MultiOrCompoundAntecedentContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(114);
				functor();
				setState(115);
				match(BRACE_OPEN);
				setState(121);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(116);
						((MultiOrCompoundAntecedentContext)_localctx).aMultiArg = aMultiArg();
						((MultiOrCompoundAntecedentContext)_localctx).args.add(((MultiOrCompoundAntecedentContext)_localctx).aMultiArg);
						setState(117);
						match(SEMICOLON);
						}
						} 
					}
					setState(123);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
				}
				setState(124);
				((MultiOrCompoundAntecedentContext)_localctx).aMultiArg = aMultiArg();
				((MultiOrCompoundAntecedentContext)_localctx).args.add(((MultiOrCompoundAntecedentContext)_localctx).aMultiArg);
				setState(125);
				match(BRACE_CLOSE);
				}
				break;
			case 5:
				_localctx = new ListAntecedentContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(127);
				match(BRACKET_OPEN);
				setState(137);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PAR_OPEN) | (1L << BRACKET_OPEN) | (1L << NOT) | (1L << UNDERSCORE) | (1L << STRING) | (1L << NUMBER) | (1L << ID) | (1L << HID) | (1L << INFIX_ID))) != 0)) {
					{
					setState(128);
					aArgSeries();
					setState(135);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==VBAR) {
						{
						setState(129);
						match(VBAR);
						setState(130);
						match(BRACKET_OPEN);
						setState(132);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PAR_OPEN) | (1L << BRACKET_OPEN) | (1L << NOT) | (1L << UNDERSCORE) | (1L << STRING) | (1L << NUMBER) | (1L << ID) | (1L << HID) | (1L << INFIX_ID))) != 0)) {
							{
							setState(131);
							aTerm();
							}
						}

						setState(134);
						match(BRACKET_CLOSE);
						}
					}

					}
				}

				setState(139);
				match(BRACKET_CLOSE);
				}
				break;
			case 6:
				_localctx = new ParAntecedentContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(140);
				match(PAR_OPEN);
				setState(141);
				aTermExpr(0);
				setState(142);
				match(PAR_CLOSE);
				}
				break;
			case 7:
				_localctx = new VariableAntecedentContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(144);
				((VariableAntecedentContext)_localctx).variable = match(HID);
				}
				break;
			case 8:
				_localctx = new WildcardAntecedentContext(_localctx);
				enterOuterAlt(_localctx, 8);
				{
				setState(145);
				((WildcardAntecedentContext)_localctx).wildcard = match(UNDERSCORE);
				}
				break;
			case 9:
				_localctx = new StringAntecedentContext(_localctx);
				enterOuterAlt(_localctx, 9);
				{
				setState(146);
				match(STRING);
				}
				break;
			case 10:
				_localctx = new NumberAntecedentContext(_localctx);
				enterOuterAlt(_localctx, 10);
				{
				setState(147);
				match(NUMBER);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AArgSeriesContext extends ParserRuleContext {
		public OrSeriesContext orSeries;
		public List<OrSeriesContext> args = new ArrayList<OrSeriesContext>();
		public List<OrSeriesContext> orSeries() {
			return getRuleContexts(OrSeriesContext.class);
		}
		public OrSeriesContext orSeries(int i) {
			return getRuleContext(OrSeriesContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GraafvisParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraafvisParser.COMMA, i);
		}
		public AArgSeriesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aArgSeries; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterAArgSeries(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitAArgSeries(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitAArgSeries(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AArgSeriesContext aArgSeries() throws RecognitionException {
		AArgSeriesContext _localctx = new AArgSeriesContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_aArgSeries);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(150);
			((AArgSeriesContext)_localctx).orSeries = orSeries();
			((AArgSeriesContext)_localctx).args.add(((AArgSeriesContext)_localctx).orSeries);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(151);
				match(COMMA);
				setState(152);
				((AArgSeriesContext)_localctx).orSeries = orSeries();
				((AArgSeriesContext)_localctx).args.add(((AArgSeriesContext)_localctx).orSeries);
				}
				}
				setState(157);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrSeriesContext extends ParserRuleContext {
		public ATermContext aTerm;
		public List<ATermContext> args = new ArrayList<ATermContext>();
		public List<ATermContext> aTerm() {
			return getRuleContexts(ATermContext.class);
		}
		public ATermContext aTerm(int i) {
			return getRuleContext(ATermContext.class,i);
		}
		public List<TerminalNode> SEMICOLON() { return getTokens(GraafvisParser.SEMICOLON); }
		public TerminalNode SEMICOLON(int i) {
			return getToken(GraafvisParser.SEMICOLON, i);
		}
		public OrSeriesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orSeries; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterOrSeries(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitOrSeries(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitOrSeries(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrSeriesContext orSeries() throws RecognitionException {
		OrSeriesContext _localctx = new OrSeriesContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_orSeries);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			((OrSeriesContext)_localctx).aTerm = aTerm();
			((OrSeriesContext)_localctx).args.add(((OrSeriesContext)_localctx).aTerm);
			setState(163);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEMICOLON) {
				{
				{
				setState(159);
				match(SEMICOLON);
				setState(160);
				((OrSeriesContext)_localctx).aTerm = aTerm();
				((OrSeriesContext)_localctx).args.add(((OrSeriesContext)_localctx).aTerm);
				}
				}
				setState(165);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ATermExprContext extends ParserRuleContext {
		public ATermExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aTermExpr; }
	 
		public ATermExprContext() { }
		public void copyFrom(ATermExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class AndExpressionAntecedentContext extends ATermExprContext {
		public List<ATermExprContext> aTermExpr() {
			return getRuleContexts(ATermExprContext.class);
		}
		public ATermExprContext aTermExpr(int i) {
			return getRuleContext(ATermExprContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(GraafvisParser.COMMA, 0); }
		public AndExpressionAntecedentContext(ATermExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterAndExpressionAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitAndExpressionAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitAndExpressionAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ParExpressionAntecedentContext extends ATermExprContext {
		public TerminalNode PAR_OPEN() { return getToken(GraafvisParser.PAR_OPEN, 0); }
		public ATermExprContext aTermExpr() {
			return getRuleContext(ATermExprContext.class,0);
		}
		public TerminalNode PAR_CLOSE() { return getToken(GraafvisParser.PAR_CLOSE, 0); }
		public ParExpressionAntecedentContext(ATermExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterParExpressionAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitParExpressionAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitParExpressionAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TermExpressionAntecedentContext extends ATermExprContext {
		public ATermContext aTerm() {
			return getRuleContext(ATermContext.class,0);
		}
		public TermExpressionAntecedentContext(ATermExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterTermExpressionAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitTermExpressionAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitTermExpressionAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class OrExpressionAntecedentContext extends ATermExprContext {
		public List<ATermExprContext> aTermExpr() {
			return getRuleContexts(ATermExprContext.class);
		}
		public ATermExprContext aTermExpr(int i) {
			return getRuleContext(ATermExprContext.class,i);
		}
		public TerminalNode SEMICOLON() { return getToken(GraafvisParser.SEMICOLON, 0); }
		public OrExpressionAntecedentContext(ATermExprContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterOrExpressionAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitOrExpressionAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitOrExpressionAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ATermExprContext aTermExpr() throws RecognitionException {
		return aTermExpr(0);
	}

	private ATermExprContext aTermExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ATermExprContext _localctx = new ATermExprContext(_ctx, _parentState);
		ATermExprContext _prevctx = _localctx;
		int _startState = 18;
		enterRecursionRule(_localctx, 18, RULE_aTermExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				_localctx = new ParExpressionAntecedentContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(167);
				match(PAR_OPEN);
				setState(168);
				aTermExpr(0);
				setState(169);
				match(PAR_CLOSE);
				}
				break;
			case 2:
				{
				_localctx = new TermExpressionAntecedentContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(171);
				aTerm();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(182);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(180);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new AndExpressionAntecedentContext(new ATermExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_aTermExpr);
						setState(174);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(175);
						match(COMMA);
						setState(176);
						aTermExpr(5);
						}
						break;
					case 2:
						{
						_localctx = new OrExpressionAntecedentContext(new ATermExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_aTermExpr);
						setState(177);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(178);
						match(SEMICOLON);
						setState(179);
						aTermExpr(4);
						}
						break;
					}
					} 
				}
				setState(184);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class AMultiArgContext extends ParserRuleContext {
		public TerminalNode PAR_OPEN() { return getToken(GraafvisParser.PAR_OPEN, 0); }
		public TerminalNode PAR_CLOSE() { return getToken(GraafvisParser.PAR_CLOSE, 0); }
		public AArgSeriesContext aArgSeries() {
			return getRuleContext(AArgSeriesContext.class,0);
		}
		public ATermContext aTerm() {
			return getRuleContext(ATermContext.class,0);
		}
		public AMultiArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_aMultiArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterAMultiArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitAMultiArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitAMultiArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AMultiArgContext aMultiArg() throws RecognitionException {
		AMultiArgContext _localctx = new AMultiArgContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_aMultiArg);
		int _la;
		try {
			setState(191);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,22,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(185);
				match(PAR_OPEN);
				setState(187);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PAR_OPEN) | (1L << BRACKET_OPEN) | (1L << NOT) | (1L << UNDERSCORE) | (1L << STRING) | (1L << NUMBER) | (1L << ID) | (1L << HID) | (1L << INFIX_ID))) != 0)) {
					{
					setState(186);
					aArgSeries();
					}
				}

				setState(189);
				match(PAR_CLOSE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(190);
				aTerm();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CTermContext extends ParserRuleContext {
		public CTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cTerm; }
	 
		public CTermContext() { }
		public void copyFrom(CTermContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MultiCompoundConsequenceContext extends CTermContext {
		public CMultiArgContext cMultiArg;
		public List<CMultiArgContext> args = new ArrayList<CMultiArgContext>();
		public FunctorContext functor() {
			return getRuleContext(FunctorContext.class,0);
		}
		public TerminalNode BRACE_OPEN() { return getToken(GraafvisParser.BRACE_OPEN, 0); }
		public TerminalNode BRACE_CLOSE() { return getToken(GraafvisParser.BRACE_CLOSE, 0); }
		public List<CMultiArgContext> cMultiArg() {
			return getRuleContexts(CMultiArgContext.class);
		}
		public CMultiArgContext cMultiArg(int i) {
			return getRuleContext(CMultiArgContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GraafvisParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraafvisParser.COMMA, i);
		}
		public MultiCompoundConsequenceContext(CTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterMultiCompoundConsequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitMultiCompoundConsequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitMultiCompoundConsequence(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class StringConsequenceContext extends CTermContext {
		public TerminalNode STRING() { return getToken(GraafvisParser.STRING, 0); }
		public StringConsequenceContext(CTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterStringConsequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitStringConsequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitStringConsequence(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NumberConsequenceContext extends CTermContext {
		public TerminalNode NUMBER() { return getToken(GraafvisParser.NUMBER, 0); }
		public NumberConsequenceContext(CTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterNumberConsequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitNumberConsequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitNumberConsequence(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ListConsequenceContext extends CTermContext {
		public List<TerminalNode> BRACKET_OPEN() { return getTokens(GraafvisParser.BRACKET_OPEN); }
		public TerminalNode BRACKET_OPEN(int i) {
			return getToken(GraafvisParser.BRACKET_OPEN, i);
		}
		public List<TerminalNode> BRACKET_CLOSE() { return getTokens(GraafvisParser.BRACKET_CLOSE); }
		public TerminalNode BRACKET_CLOSE(int i) {
			return getToken(GraafvisParser.BRACKET_CLOSE, i);
		}
		public CArgSeriesContext cArgSeries() {
			return getRuleContext(CArgSeriesContext.class,0);
		}
		public TerminalNode VBAR() { return getToken(GraafvisParser.VBAR, 0); }
		public CTermContext cTerm() {
			return getRuleContext(CTermContext.class,0);
		}
		public ListConsequenceContext(CTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterListConsequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitListConsequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitListConsequence(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class VariableConsequenceContext extends CTermContext {
		public Token variable;
		public TerminalNode HID() { return getToken(GraafvisParser.HID, 0); }
		public VariableConsequenceContext(CTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterVariableConsequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitVariableConsequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitVariableConsequence(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class CompoundConsequenceContext extends CTermContext {
		public CArgSeriesContext args;
		public FunctorContext functor() {
			return getRuleContext(FunctorContext.class,0);
		}
		public TerminalNode PAR_OPEN() { return getToken(GraafvisParser.PAR_OPEN, 0); }
		public TerminalNode PAR_CLOSE() { return getToken(GraafvisParser.PAR_CLOSE, 0); }
		public CArgSeriesContext cArgSeries() {
			return getRuleContext(CArgSeriesContext.class,0);
		}
		public CompoundConsequenceContext(CTermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterCompoundConsequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitCompoundConsequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitCompoundConsequence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CTermContext cTerm() throws RecognitionException {
		CTermContext _localctx = new CTermContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_cTerm);
		int _la;
		try {
			int _alt;
			setState(230);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,29,_ctx) ) {
			case 1:
				_localctx = new CompoundConsequenceContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(193);
				functor();
				setState(199);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==PAR_OPEN) {
					{
					setState(194);
					match(PAR_OPEN);
					setState(196);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BRACKET_OPEN) | (1L << STRING) | (1L << NUMBER) | (1L << ID) | (1L << HID) | (1L << INFIX_ID))) != 0)) {
						{
						setState(195);
						((CompoundConsequenceContext)_localctx).args = cArgSeries();
						}
					}

					setState(198);
					match(PAR_CLOSE);
					}
				}

				}
				break;
			case 2:
				_localctx = new MultiCompoundConsequenceContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(201);
				functor();
				setState(202);
				match(BRACE_OPEN);
				setState(208);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(203);
						((MultiCompoundConsequenceContext)_localctx).cMultiArg = cMultiArg();
						((MultiCompoundConsequenceContext)_localctx).args.add(((MultiCompoundConsequenceContext)_localctx).cMultiArg);
						setState(204);
						match(COMMA);
						}
						} 
					}
					setState(210);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,25,_ctx);
				}
				setState(211);
				((MultiCompoundConsequenceContext)_localctx).cMultiArg = cMultiArg();
				((MultiCompoundConsequenceContext)_localctx).args.add(((MultiCompoundConsequenceContext)_localctx).cMultiArg);
				setState(212);
				match(BRACE_CLOSE);
				}
				break;
			case 3:
				_localctx = new ListConsequenceContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(214);
				match(BRACKET_OPEN);
				setState(224);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BRACKET_OPEN) | (1L << STRING) | (1L << NUMBER) | (1L << ID) | (1L << HID) | (1L << INFIX_ID))) != 0)) {
					{
					setState(215);
					cArgSeries();
					setState(222);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==VBAR) {
						{
						setState(216);
						match(VBAR);
						setState(217);
						match(BRACKET_OPEN);
						setState(219);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BRACKET_OPEN) | (1L << STRING) | (1L << NUMBER) | (1L << ID) | (1L << HID) | (1L << INFIX_ID))) != 0)) {
							{
							setState(218);
							cTerm();
							}
						}

						setState(221);
						match(BRACKET_CLOSE);
						}
					}

					}
				}

				setState(226);
				match(BRACKET_CLOSE);
				}
				break;
			case 4:
				_localctx = new VariableConsequenceContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(227);
				((VariableConsequenceContext)_localctx).variable = match(HID);
				}
				break;
			case 5:
				_localctx = new StringConsequenceContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(228);
				match(STRING);
				}
				break;
			case 6:
				_localctx = new NumberConsequenceContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(229);
				match(NUMBER);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CArgSeriesContext extends ParserRuleContext {
		public CTermContext cTerm;
		public List<CTermContext> args = new ArrayList<CTermContext>();
		public List<CTermContext> cTerm() {
			return getRuleContexts(CTermContext.class);
		}
		public CTermContext cTerm(int i) {
			return getRuleContext(CTermContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GraafvisParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraafvisParser.COMMA, i);
		}
		public CArgSeriesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cArgSeries; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterCArgSeries(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitCArgSeries(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitCArgSeries(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CArgSeriesContext cArgSeries() throws RecognitionException {
		CArgSeriesContext _localctx = new CArgSeriesContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_cArgSeries);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(237);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(232);
					((CArgSeriesContext)_localctx).cTerm = cTerm();
					((CArgSeriesContext)_localctx).args.add(((CArgSeriesContext)_localctx).cTerm);
					setState(233);
					match(COMMA);
					}
					} 
				}
				setState(239);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			}
			setState(240);
			((CArgSeriesContext)_localctx).cTerm = cTerm();
			((CArgSeriesContext)_localctx).args.add(((CArgSeriesContext)_localctx).cTerm);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CMultiArgContext extends ParserRuleContext {
		public TerminalNode PAR_OPEN() { return getToken(GraafvisParser.PAR_OPEN, 0); }
		public TerminalNode PAR_CLOSE() { return getToken(GraafvisParser.PAR_CLOSE, 0); }
		public CArgSeriesContext cArgSeries() {
			return getRuleContext(CArgSeriesContext.class,0);
		}
		public CTermContext cTerm() {
			return getRuleContext(CTermContext.class,0);
		}
		public CMultiArgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cMultiArg; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterCMultiArg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitCMultiArg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitCMultiArg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CMultiArgContext cMultiArg() throws RecognitionException {
		CMultiArgContext _localctx = new CMultiArgContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_cMultiArg);
		int _la;
		try {
			setState(248);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PAR_OPEN:
				enterOuterAlt(_localctx, 1);
				{
				setState(242);
				match(PAR_OPEN);
				setState(244);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BRACKET_OPEN) | (1L << STRING) | (1L << NUMBER) | (1L << ID) | (1L << HID) | (1L << INFIX_ID))) != 0)) {
					{
					setState(243);
					cArgSeries();
					}
				}

				setState(246);
				match(PAR_CLOSE);
				}
				break;
			case BRACKET_OPEN:
			case STRING:
			case NUMBER:
			case ID:
			case HID:
			case INFIX_ID:
				enterOuterAlt(_localctx, 2);
				{
				setState(247);
				cTerm();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FunctorContext extends ParserRuleContext {
		public FunctorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_functor; }
	 
		public FunctorContext() { }
		public void copyFrom(FunctorContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class IdFunctorContext extends FunctorContext {
		public TerminalNode ID() { return getToken(GraafvisParser.ID, 0); }
		public IdFunctorContext(FunctorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterIdFunctor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitIdFunctor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitIdFunctor(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class InfixFunctorContext extends FunctorContext {
		public TerminalNode INFIX_ID() { return getToken(GraafvisParser.INFIX_ID, 0); }
		public InfixFunctorContext(FunctorContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterInfixFunctor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitInfixFunctor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitInfixFunctor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FunctorContext functor() throws RecognitionException {
		FunctorContext _localctx = new FunctorContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_functor);
		try {
			setState(252);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				_localctx = new IdFunctorContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(250);
				match(ID);
				}
				break;
			case INFIX_ID:
				_localctx = new InfixFunctorContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(251);
				match(INFIX_ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 9:
			return aTermExpr_sempred((ATermExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean aTermExpr_sempred(ATermExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\35\u0101\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\3\2\7\2\"\n\2\f\2"+
		"\16\2%\13\2\3\2\5\2(\n\2\3\2\5\2+\n\2\3\2\7\2.\n\2\f\2\16\2\61\13\2\3"+
		"\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\7\4>\n\4\f\4\16\4A\13\4\3\4"+
		"\3\4\3\5\3\5\3\5\3\5\3\5\7\5J\n\5\f\5\16\5M\13\5\3\5\3\5\3\6\3\6\3\6\5"+
		"\6T\n\6\3\7\3\7\3\7\5\7Y\n\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\5\bc\n\b"+
		"\3\b\5\bf\n\b\3\b\3\b\3\b\3\b\3\b\7\bm\n\b\f\b\16\bp\13\b\3\b\3\b\3\b"+
		"\3\b\3\b\3\b\3\b\3\b\7\bz\n\b\f\b\16\b}\13\b\3\b\3\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\5\b\u0087\n\b\3\b\5\b\u008a\n\b\5\b\u008c\n\b\3\b\3\b\3\b\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\5\b\u0097\n\b\3\t\3\t\3\t\7\t\u009c\n\t\f\t\16\t\u009f"+
		"\13\t\3\n\3\n\3\n\7\n\u00a4\n\n\f\n\16\n\u00a7\13\n\3\13\3\13\3\13\3\13"+
		"\3\13\3\13\5\13\u00af\n\13\3\13\3\13\3\13\3\13\3\13\3\13\7\13\u00b7\n"+
		"\13\f\13\16\13\u00ba\13\13\3\f\3\f\5\f\u00be\n\f\3\f\3\f\5\f\u00c2\n\f"+
		"\3\r\3\r\3\r\5\r\u00c7\n\r\3\r\5\r\u00ca\n\r\3\r\3\r\3\r\3\r\3\r\7\r\u00d1"+
		"\n\r\f\r\16\r\u00d4\13\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\3\r\5\r\u00de\n\r"+
		"\3\r\5\r\u00e1\n\r\5\r\u00e3\n\r\3\r\3\r\3\r\3\r\5\r\u00e9\n\r\3\16\3"+
		"\16\3\16\7\16\u00ee\n\16\f\16\16\16\u00f1\13\16\3\16\3\16\3\17\3\17\5"+
		"\17\u00f7\n\17\3\17\3\17\5\17\u00fb\n\17\3\20\3\20\5\20\u00ff\n\20\3\20"+
		"\2\3\24\21\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36\2\2\2\u011f\2#\3\2\2"+
		"\2\4\64\3\2\2\2\68\3\2\2\2\bD\3\2\2\2\nP\3\2\2\2\fX\3\2\2\2\16\u0096\3"+
		"\2\2\2\20\u0098\3\2\2\2\22\u00a0\3\2\2\2\24\u00ae\3\2\2\2\26\u00c1\3\2"+
		"\2\2\30\u00e8\3\2\2\2\32\u00ef\3\2\2\2\34\u00fa\3\2\2\2\36\u00fe\3\2\2"+
		"\2 \"\5\4\3\2! \3\2\2\2\"%\3\2\2\2#!\3\2\2\2#$\3\2\2\2$\'\3\2\2\2%#\3"+
		"\2\2\2&(\5\6\4\2\'&\3\2\2\2\'(\3\2\2\2(*\3\2\2\2)+\5\b\5\2*)\3\2\2\2*"+
		"+\3\2\2\2+/\3\2\2\2,.\5\f\7\2-,\3\2\2\2.\61\3\2\2\2/-\3\2\2\2/\60\3\2"+
		"\2\2\60\62\3\2\2\2\61/\3\2\2\2\62\63\7\2\2\3\63\3\3\2\2\2\64\65\7\22\2"+
		"\2\65\66\7\26\2\2\66\67\7\5\2\2\67\5\3\2\2\289\7\23\2\29:\7\4\2\2:?\5"+
		"\n\6\2;<\7\r\2\2<>\5\n\6\2=;\3\2\2\2>A\3\2\2\2?=\3\2\2\2?@\3\2\2\2@B\3"+
		"\2\2\2A?\3\2\2\2BC\7\5\2\2C\7\3\2\2\2DE\7\24\2\2EF\7\4\2\2FK\5\n\6\2G"+
		"H\7\r\2\2HJ\5\n\6\2IG\3\2\2\2JM\3\2\2\2KI\3\2\2\2KL\3\2\2\2LN\3\2\2\2"+
		"MK\3\2\2\2NO\7\5\2\2O\t\3\2\2\2PS\7\26\2\2QR\7\25\2\2RT\7\30\2\2SQ\3\2"+
		"\2\2ST\3\2\2\2T\13\3\2\2\2UV\5\24\13\2VW\7\3\2\2WY\3\2\2\2XU\3\2\2\2X"+
		"Y\3\2\2\2YZ\3\2\2\2Z[\5\32\16\2[\\\7\5\2\2\\\r\3\2\2\2]^\7\17\2\2^\u0097"+
		"\5\16\b\2_e\5\36\20\2`b\7\6\2\2ac\5\20\t\2ba\3\2\2\2bc\3\2\2\2cd\3\2\2"+
		"\2df\7\7\2\2e`\3\2\2\2ef\3\2\2\2f\u0097\3\2\2\2gh\5\36\20\2hn\7\b\2\2"+
		"ij\5\26\f\2jk\7\r\2\2km\3\2\2\2li\3\2\2\2mp\3\2\2\2nl\3\2\2\2no\3\2\2"+
		"\2oq\3\2\2\2pn\3\2\2\2qr\5\26\f\2rs\7\t\2\2s\u0097\3\2\2\2tu\5\36\20\2"+
		"u{\7\b\2\2vw\5\26\f\2wx\7\16\2\2xz\3\2\2\2yv\3\2\2\2z}\3\2\2\2{y\3\2\2"+
		"\2{|\3\2\2\2|~\3\2\2\2}{\3\2\2\2~\177\5\26\f\2\177\u0080\7\t\2\2\u0080"+
		"\u0097\3\2\2\2\u0081\u008b\7\n\2\2\u0082\u0089\5\20\t\2\u0083\u0084\7"+
		"\f\2\2\u0084\u0086\7\n\2\2\u0085\u0087\5\16\b\2\u0086\u0085\3\2\2\2\u0086"+
		"\u0087\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u008a\7\13\2\2\u0089\u0083\3"+
		"\2\2\2\u0089\u008a\3\2\2\2\u008a\u008c\3\2\2\2\u008b\u0082\3\2\2\2\u008b"+
		"\u008c\3\2\2\2\u008c\u008d\3\2\2\2\u008d\u0097\7\13\2\2\u008e\u008f\7"+
		"\6\2\2\u008f\u0090\5\24\13\2\u0090\u0091\7\7\2\2\u0091\u0097\3\2\2\2\u0092"+
		"\u0097\7\31\2\2\u0093\u0097\7\21\2\2\u0094\u0097\7\26\2\2\u0095\u0097"+
		"\7\27\2\2\u0096]\3\2\2\2\u0096_\3\2\2\2\u0096g\3\2\2\2\u0096t\3\2\2\2"+
		"\u0096\u0081\3\2\2\2\u0096\u008e\3\2\2\2\u0096\u0092\3\2\2\2\u0096\u0093"+
		"\3\2\2\2\u0096\u0094\3\2\2\2\u0096\u0095\3\2\2\2\u0097\17\3\2\2\2\u0098"+
		"\u009d\5\22\n\2\u0099\u009a\7\r\2\2\u009a\u009c\5\22\n\2\u009b\u0099\3"+
		"\2\2\2\u009c\u009f\3\2\2\2\u009d\u009b\3\2\2\2\u009d\u009e\3\2\2\2\u009e"+
		"\21\3\2\2\2\u009f\u009d\3\2\2\2\u00a0\u00a5\5\16\b\2\u00a1\u00a2\7\16"+
		"\2\2\u00a2\u00a4\5\16\b\2\u00a3\u00a1\3\2\2\2\u00a4\u00a7\3\2\2\2\u00a5"+
		"\u00a3\3\2\2\2\u00a5\u00a6\3\2\2\2\u00a6\23\3\2\2\2\u00a7\u00a5\3\2\2"+
		"\2\u00a8\u00a9\b\13\1\2\u00a9\u00aa\7\6\2\2\u00aa\u00ab\5\24\13\2\u00ab"+
		"\u00ac\7\7\2\2\u00ac\u00af\3\2\2\2\u00ad\u00af\5\16\b\2\u00ae\u00a8\3"+
		"\2\2\2\u00ae\u00ad\3\2\2\2\u00af\u00b8\3\2\2\2\u00b0\u00b1\f\6\2\2\u00b1"+
		"\u00b2\7\r\2\2\u00b2\u00b7\5\24\13\7\u00b3\u00b4\f\5\2\2\u00b4\u00b5\7"+
		"\16\2\2\u00b5\u00b7\5\24\13\6\u00b6\u00b0\3\2\2\2\u00b6\u00b3\3\2\2\2"+
		"\u00b7\u00ba\3\2\2\2\u00b8\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\25"+
		"\3\2\2\2\u00ba\u00b8\3\2\2\2\u00bb\u00bd\7\6\2\2\u00bc\u00be\5\20\t\2"+
		"\u00bd\u00bc\3\2\2\2\u00bd\u00be\3\2\2\2\u00be\u00bf\3\2\2\2\u00bf\u00c2"+
		"\7\7\2\2\u00c0\u00c2\5\16\b\2\u00c1\u00bb\3\2\2\2\u00c1\u00c0\3\2\2\2"+
		"\u00c2\27\3\2\2\2\u00c3\u00c9\5\36\20\2\u00c4\u00c6\7\6\2\2\u00c5\u00c7"+
		"\5\32\16\2\u00c6\u00c5\3\2\2\2\u00c6\u00c7\3\2\2\2\u00c7\u00c8\3\2\2\2"+
		"\u00c8\u00ca\7\7\2\2\u00c9\u00c4\3\2\2\2\u00c9\u00ca\3\2\2\2\u00ca\u00e9"+
		"\3\2\2\2\u00cb\u00cc\5\36\20\2\u00cc\u00d2\7\b\2\2\u00cd\u00ce\5\34\17"+
		"\2\u00ce\u00cf\7\r\2\2\u00cf\u00d1\3\2\2\2\u00d0\u00cd\3\2\2\2\u00d1\u00d4"+
		"\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3\u00d5\3\2\2\2\u00d4"+
		"\u00d2\3\2\2\2\u00d5\u00d6\5\34\17\2\u00d6\u00d7\7\t\2\2\u00d7\u00e9\3"+
		"\2\2\2\u00d8\u00e2\7\n\2\2\u00d9\u00e0\5\32\16\2\u00da\u00db\7\f\2\2\u00db"+
		"\u00dd\7\n\2\2\u00dc\u00de\5\30\r\2\u00dd\u00dc\3\2\2\2\u00dd\u00de\3"+
		"\2\2\2\u00de\u00df\3\2\2\2\u00df\u00e1\7\13\2\2\u00e0\u00da\3\2\2\2\u00e0"+
		"\u00e1\3\2\2\2\u00e1\u00e3\3\2\2\2\u00e2\u00d9\3\2\2\2\u00e2\u00e3\3\2"+
		"\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e9\7\13\2\2\u00e5\u00e9\7\31\2\2\u00e6"+
		"\u00e9\7\26\2\2\u00e7\u00e9\7\27\2\2\u00e8\u00c3\3\2\2\2\u00e8\u00cb\3"+
		"\2\2\2\u00e8\u00d8\3\2\2\2\u00e8\u00e5\3\2\2\2\u00e8\u00e6\3\2\2\2\u00e8"+
		"\u00e7\3\2\2\2\u00e9\31\3\2\2\2\u00ea\u00eb\5\30\r\2\u00eb\u00ec\7\r\2"+
		"\2\u00ec\u00ee\3\2\2\2\u00ed\u00ea\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef\u00ed"+
		"\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0\u00f2\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2"+
		"\u00f3\5\30\r\2\u00f3\33\3\2\2\2\u00f4\u00f6\7\6\2\2\u00f5\u00f7\5\32"+
		"\16\2\u00f6\u00f5\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f8\3\2\2\2\u00f8"+
		"\u00fb\7\7\2\2\u00f9\u00fb\5\30\r\2\u00fa\u00f4\3\2\2\2\u00fa\u00f9\3"+
		"\2\2\2\u00fb\35\3\2\2\2\u00fc\u00ff\7\30\2\2\u00fd\u00ff\7\32\2\2\u00fe"+
		"\u00fc\3\2\2\2\u00fe\u00fd\3\2\2\2\u00ff\37\3\2\2\2$#\'*/?KSXben{\u0086"+
		"\u0089\u008b\u0096\u009d\u00a5\u00ae\u00b6\u00b8\u00bd\u00c1\u00c6\u00c9"+
		"\u00d2\u00dd\u00e0\u00e2\u00e8\u00ef\u00f6\u00fa\u00fe";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}