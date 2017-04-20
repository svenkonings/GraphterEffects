// Generated from C:/Users/Sven/Documents/GitHub/GraphterEffects/src/main/java/graafvis/grammar\Graafvis.g4 by ANTLR 4.6
package graafvis.grammar;
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
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ARROW=1, COLON=2, EOL=3, PAR_OPEN=4, PAR_CLOSE=5, BRACE_OPEN=6, BRACE_CLOSE=7, 
		BRACKET_OPEN=8, BRACKET_CLOSE=9, VBAR=10, EQ=11, NQ=12, GT=13, LT=14, 
		GE=15, LE=16, COMMA=17, SEMICOLON=18, OR=19, AND=20, NOT=21, PLUS=22, 
		MINUS=23, MULT=24, DIV=25, POW=26, MOD=27, UNDERSCORE=28, TRUE=29, FALSE=30, 
		IMPORT_TOKEN=31, NODE_LABEL_TOKEN=32, EDGE_LABEL_TOKEN=33, RENAME_TOKEN=34, 
		STRING=35, NUMBER=36, ID=37, HID=38, WS=39, BLOCKCOMMENT=40, LINECOMMENT=41;
	public static final int
		RULE_program = 0, RULE_importVis = 1, RULE_nodeLabelGen = 2, RULE_edgeLabelGen = 3, 
		RULE_label = 4, RULE_clause = 5, RULE_antecedent = 6, RULE_propositionalFormula = 7, 
		RULE_consequence = 8, RULE_literal = 9, RULE_atom = 10, RULE_multiAtom = 11, 
		RULE_multiTerm = 12, RULE_termTuple = 13, RULE_predicate = 14, RULE_term = 15, 
		RULE_variable = 16, RULE_andOp = 17, RULE_orOp = 18;
	public static final String[] ruleNames = {
		"program", "importVis", "nodeLabelGen", "edgeLabelGen", "label", "clause", 
		"antecedent", "propositionalFormula", "consequence", "literal", "atom", 
		"multiAtom", "multiTerm", "termTuple", "predicate", "term", "variable", 
		"andOp", "orOp"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'->'", "':'", "'.'", "'('", "')'", "'{'", "'}'", "'['", "']'", 
		"'|'", "'=='", "'!='", "'>'", "'<'", "'>='", "'<='", "','", "';'", "'or'", 
		"'and'", "'not'", "'+'", "'-'", "'*'", "'/'", "'^'", "'%'", "'_'", "'true'", 
		"'false'", "'consult'", "'node labels'", "'edge labels'", "'as'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "ARROW", "COLON", "EOL", "PAR_OPEN", "PAR_CLOSE", "BRACE_OPEN", 
		"BRACE_CLOSE", "BRACKET_OPEN", "BRACKET_CLOSE", "VBAR", "EQ", "NQ", "GT", 
		"LT", "GE", "LE", "COMMA", "SEMICOLON", "OR", "AND", "NOT", "PLUS", "MINUS", 
		"MULT", "DIV", "POW", "MOD", "UNDERSCORE", "TRUE", "FALSE", "IMPORT_TOKEN", 
		"NODE_LABEL_TOKEN", "EDGE_LABEL_TOKEN", "RENAME_TOKEN", "STRING", "NUMBER", 
		"ID", "HID", "WS", "BLOCKCOMMENT", "LINECOMMENT"
	};
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
			setState(41);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT_TOKEN) {
				{
				{
				setState(38);
				importVis();
				}
				}
				setState(43);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(45);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NODE_LABEL_TOKEN) {
				{
				setState(44);
				nodeLabelGen();
				}
			}

			setState(48);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EDGE_LABEL_TOKEN) {
				{
				setState(47);
				edgeLabelGen();
				}
			}

			setState(53);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PAR_OPEN) | (1L << NOT) | (1L << ID))) != 0)) {
				{
				{
				setState(50);
				clause();
				}
				}
				setState(55);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(56);
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
			setState(58);
			match(IMPORT_TOKEN);
			setState(59);
			match(STRING);
			setState(60);
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
		public TerminalNode NODE_LABEL_TOKEN() { return getToken(GraafvisParser.NODE_LABEL_TOKEN, 0); }
		public TerminalNode COLON() { return getToken(GraafvisParser.COLON, 0); }
		public List<LabelContext> label() {
			return getRuleContexts(LabelContext.class);
		}
		public LabelContext label(int i) {
			return getRuleContext(LabelContext.class,i);
		}
		public TerminalNode EOL() { return getToken(GraafvisParser.EOL, 0); }
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
			setState(62);
			match(NODE_LABEL_TOKEN);
			setState(63);
			match(COLON);
			setState(64);
			label();
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(65);
				match(COMMA);
				setState(66);
				label();
				}
				}
				setState(71);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(72);
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
		public TerminalNode EDGE_LABEL_TOKEN() { return getToken(GraafvisParser.EDGE_LABEL_TOKEN, 0); }
		public TerminalNode COLON() { return getToken(GraafvisParser.COLON, 0); }
		public List<LabelContext> label() {
			return getRuleContexts(LabelContext.class);
		}
		public LabelContext label(int i) {
			return getRuleContext(LabelContext.class,i);
		}
		public TerminalNode EOL() { return getToken(GraafvisParser.EOL, 0); }
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
			setState(74);
			match(EDGE_LABEL_TOKEN);
			setState(75);
			match(COLON);
			setState(76);
			label();
			setState(81);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(77);
				match(COMMA);
				setState(78);
				label();
				}
				}
				setState(83);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(84);
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
			setState(86);
			match(STRING);
			setState(89);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==RENAME_TOKEN) {
				{
				setState(87);
				match(RENAME_TOKEN);
				setState(88);
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
		public ConsequenceContext consequence() {
			return getRuleContext(ConsequenceContext.class,0);
		}
		public TerminalNode EOL() { return getToken(GraafvisParser.EOL, 0); }
		public AntecedentContext antecedent() {
			return getRuleContext(AntecedentContext.class,0);
		}
		public TerminalNode ARROW() { return getToken(GraafvisParser.ARROW, 0); }
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
			setState(94);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(91);
				antecedent();
				setState(92);
				match(ARROW);
				}
				break;
			}
			setState(96);
			consequence();
			setState(97);
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

	public static class AntecedentContext extends ParserRuleContext {
		public PropositionalFormulaContext propositionalFormula() {
			return getRuleContext(PropositionalFormulaContext.class,0);
		}
		public AntecedentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_antecedent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterAntecedent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitAntecedent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitAntecedent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AntecedentContext antecedent() throws RecognitionException {
		AntecedentContext _localctx = new AntecedentContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_antecedent);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			propositionalFormula(0);
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

	public static class PropositionalFormulaContext extends ParserRuleContext {
		public PropositionalFormulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propositionalFormula; }
	 
		public PropositionalFormulaContext() { }
		public void copyFrom(PropositionalFormulaContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PfNotContext extends PropositionalFormulaContext {
		public TerminalNode NOT() { return getToken(GraafvisParser.NOT, 0); }
		public PropositionalFormulaContext propositionalFormula() {
			return getRuleContext(PropositionalFormulaContext.class,0);
		}
		public PfNotContext(PropositionalFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterPfNot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitPfNot(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitPfNot(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PfLitContext extends PropositionalFormulaContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public PfLitContext(PropositionalFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterPfLit(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitPfLit(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitPfLit(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PfAndContext extends PropositionalFormulaContext {
		public List<PropositionalFormulaContext> propositionalFormula() {
			return getRuleContexts(PropositionalFormulaContext.class);
		}
		public PropositionalFormulaContext propositionalFormula(int i) {
			return getRuleContext(PropositionalFormulaContext.class,i);
		}
		public AndOpContext andOp() {
			return getRuleContext(AndOpContext.class,0);
		}
		public PfAndContext(PropositionalFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterPfAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitPfAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitPfAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PfOrContext extends PropositionalFormulaContext {
		public List<PropositionalFormulaContext> propositionalFormula() {
			return getRuleContexts(PropositionalFormulaContext.class);
		}
		public PropositionalFormulaContext propositionalFormula(int i) {
			return getRuleContext(PropositionalFormulaContext.class,i);
		}
		public OrOpContext orOp() {
			return getRuleContext(OrOpContext.class,0);
		}
		public PfOrContext(PropositionalFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterPfOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitPfOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitPfOr(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PfNestContext extends PropositionalFormulaContext {
		public TerminalNode PAR_OPEN() { return getToken(GraafvisParser.PAR_OPEN, 0); }
		public PropositionalFormulaContext propositionalFormula() {
			return getRuleContext(PropositionalFormulaContext.class,0);
		}
		public TerminalNode PAR_CLOSE() { return getToken(GraafvisParser.PAR_CLOSE, 0); }
		public PfNestContext(PropositionalFormulaContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterPfNest(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitPfNest(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitPfNest(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropositionalFormulaContext propositionalFormula() throws RecognitionException {
		return propositionalFormula(0);
	}

	private PropositionalFormulaContext propositionalFormula(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PropositionalFormulaContext _localctx = new PropositionalFormulaContext(_ctx, _parentState);
		PropositionalFormulaContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_propositionalFormula, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(109);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case NOT:
				{
				_localctx = new PfNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(102);
				match(NOT);
				setState(103);
				propositionalFormula(5);
				}
				break;
			case PAR_OPEN:
				{
				_localctx = new PfNestContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(104);
				match(PAR_OPEN);
				setState(105);
				propositionalFormula(0);
				setState(106);
				match(PAR_CLOSE);
				}
				break;
			case ID:
				{
				_localctx = new PfLitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(108);
				literal();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(121);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(119);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new PfAndContext(new PropositionalFormulaContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_propositionalFormula);
						setState(111);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(112);
						andOp();
						setState(113);
						propositionalFormula(5);
						}
						break;
					case 2:
						{
						_localctx = new PfOrContext(new PropositionalFormulaContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_propositionalFormula);
						setState(115);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(116);
						orOp();
						setState(117);
						propositionalFormula(4);
						}
						break;
					}
					} 
				}
				setState(123);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
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

	public static class ConsequenceContext extends ParserRuleContext {
		public List<LiteralContext> literal() {
			return getRuleContexts(LiteralContext.class);
		}
		public LiteralContext literal(int i) {
			return getRuleContext(LiteralContext.class,i);
		}
		public List<AndOpContext> andOp() {
			return getRuleContexts(AndOpContext.class);
		}
		public AndOpContext andOp(int i) {
			return getRuleContext(AndOpContext.class,i);
		}
		public ConsequenceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_consequence; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterConsequence(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitConsequence(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitConsequence(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConsequenceContext consequence() throws RecognitionException {
		ConsequenceContext _localctx = new ConsequenceContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_consequence);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(124);
			literal();
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA || _la==AND) {
				{
				{
				setState(125);
				andOp();
				setState(126);
				literal();
				}
				}
				setState(132);
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

	public static class LiteralContext extends ParserRuleContext {
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
	 
		public LiteralContext() { }
		public void copyFrom(LiteralContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MultiAtomLiteralContext extends LiteralContext {
		public MultiAtomContext multiAtom() {
			return getRuleContext(MultiAtomContext.class,0);
		}
		public MultiAtomLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterMultiAtomLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitMultiAtomLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitMultiAtomLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class AtomLiteralContext extends LiteralContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public AtomLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterAtomLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitAtomLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitAtomLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_literal);
		try {
			setState(135);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				_localctx = new AtomLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(133);
				atom();
				}
				break;
			case 2:
				_localctx = new MultiAtomLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(134);
				multiAtom();
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

	public static class AtomContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public TermTupleContext termTuple() {
			return getRuleContext(TermTupleContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_atom);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			predicate();
			setState(139);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				{
				setState(138);
				termTuple();
				}
				break;
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

	public static class MultiAtomContext extends ParserRuleContext {
		public MultiAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiAtom; }
	 
		public MultiAtomContext() { }
		public void copyFrom(MultiAtomContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class MultiAndContext extends MultiAtomContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public TerminalNode BRACE_OPEN() { return getToken(GraafvisParser.BRACE_OPEN, 0); }
		public List<MultiTermContext> multiTerm() {
			return getRuleContexts(MultiTermContext.class);
		}
		public MultiTermContext multiTerm(int i) {
			return getRuleContext(MultiTermContext.class,i);
		}
		public TerminalNode BRACE_CLOSE() { return getToken(GraafvisParser.BRACE_CLOSE, 0); }
		public List<AndOpContext> andOp() {
			return getRuleContexts(AndOpContext.class);
		}
		public AndOpContext andOp(int i) {
			return getRuleContext(AndOpContext.class,i);
		}
		public MultiAndContext(MultiAtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterMultiAnd(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitMultiAnd(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitMultiAnd(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultiOrContext extends MultiAtomContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public TerminalNode BRACE_OPEN() { return getToken(GraafvisParser.BRACE_OPEN, 0); }
		public List<MultiTermContext> multiTerm() {
			return getRuleContexts(MultiTermContext.class);
		}
		public MultiTermContext multiTerm(int i) {
			return getRuleContext(MultiTermContext.class,i);
		}
		public TerminalNode BRACE_CLOSE() { return getToken(GraafvisParser.BRACE_CLOSE, 0); }
		public List<OrOpContext> orOp() {
			return getRuleContexts(OrOpContext.class);
		}
		public OrOpContext orOp(int i) {
			return getRuleContext(OrOpContext.class,i);
		}
		public MultiOrContext(MultiAtomContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterMultiOr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitMultiOr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitMultiOr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiAtomContext multiAtom() throws RecognitionException {
		MultiAtomContext _localctx = new MultiAtomContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_multiAtom);
		int _la;
		try {
			setState(167);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				_localctx = new MultiAndContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(141);
				predicate();
				setState(142);
				match(BRACE_OPEN);
				setState(143);
				multiTerm();
				setState(149);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA || _la==AND) {
					{
					{
					setState(144);
					andOp();
					setState(145);
					multiTerm();
					}
					}
					setState(151);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(152);
				match(BRACE_CLOSE);
				}
				break;
			case 2:
				_localctx = new MultiOrContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				predicate();
				setState(155);
				match(BRACE_OPEN);
				setState(156);
				multiTerm();
				setState(162);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEMICOLON || _la==OR) {
					{
					{
					setState(157);
					orOp();
					setState(158);
					multiTerm();
					}
					}
					setState(164);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(165);
				match(BRACE_CLOSE);
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

	public static class MultiTermContext extends ParserRuleContext {
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public TermTupleContext termTuple() {
			return getRuleContext(TermTupleContext.class,0);
		}
		public MultiTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multiTerm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterMultiTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitMultiTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitMultiTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MultiTermContext multiTerm() throws RecognitionException {
		MultiTermContext _localctx = new MultiTermContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_multiTerm);
		try {
			setState(171);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BRACKET_OPEN:
			case UNDERSCORE:
			case STRING:
			case NUMBER:
			case ID:
			case HID:
				enterOuterAlt(_localctx, 1);
				{
				setState(169);
				term();
				}
				break;
			case PAR_OPEN:
				enterOuterAlt(_localctx, 2);
				{
				setState(170);
				termTuple();
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

	public static class TermTupleContext extends ParserRuleContext {
		public TerminalNode PAR_OPEN() { return getToken(GraafvisParser.PAR_OPEN, 0); }
		public TerminalNode PAR_CLOSE() { return getToken(GraafvisParser.PAR_CLOSE, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GraafvisParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraafvisParser.COMMA, i);
		}
		public TermTupleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_termTuple; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterTermTuple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitTermTuple(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitTermTuple(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermTupleContext termTuple() throws RecognitionException {
		TermTupleContext _localctx = new TermTupleContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_termTuple);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(173);
			match(PAR_OPEN);
			setState(182);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BRACKET_OPEN) | (1L << UNDERSCORE) | (1L << STRING) | (1L << NUMBER) | (1L << ID) | (1L << HID))) != 0)) {
				{
				setState(174);
				term();
				setState(179);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(175);
					match(COMMA);
					setState(176);
					term();
					}
					}
					setState(181);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(184);
			match(PAR_CLOSE);
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

	public static class PredicateContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GraafvisParser.ID, 0); }
		public PredicateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_predicate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterPredicate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitPredicate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitPredicate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PredicateContext predicate() throws RecognitionException {
		PredicateContext _localctx = new PredicateContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_predicate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			match(ID);
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

	public static class TermContext extends ParserRuleContext {
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	 
		public TermContext() { }
		public void copyFrom(TermContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class TermListContext extends TermContext {
		public TerminalNode BRACKET_OPEN() { return getToken(GraafvisParser.BRACKET_OPEN, 0); }
		public TerminalNode BRACKET_CLOSE() { return getToken(GraafvisParser.BRACKET_CLOSE, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GraafvisParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraafvisParser.COMMA, i);
		}
		public TerminalNode VBAR() { return getToken(GraafvisParser.VBAR, 0); }
		public TermListContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterTermList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitTermList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitTermList(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TermIDContext extends TermContext {
		public TerminalNode ID() { return getToken(GraafvisParser.ID, 0); }
		public TermIDContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterTermID(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitTermID(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitTermID(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TermStringContext extends TermContext {
		public TerminalNode STRING() { return getToken(GraafvisParser.STRING, 0); }
		public TermStringContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterTermString(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitTermString(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitTermString(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TermWildcardContext extends TermContext {
		public TerminalNode UNDERSCORE() { return getToken(GraafvisParser.UNDERSCORE, 0); }
		public TermWildcardContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterTermWildcard(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitTermWildcard(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitTermWildcard(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TermNumberContext extends TermContext {
		public TerminalNode NUMBER() { return getToken(GraafvisParser.NUMBER, 0); }
		public TermNumberContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterTermNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitTermNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitTermNumber(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TermAtomContext extends TermContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public TermAtomContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterTermAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitTermAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitTermAtom(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TermVarContext extends TermContext {
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TermVarContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterTermVar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitTermVar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitTermVar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_term);
		int _la;
		try {
			setState(217);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,24,_ctx) ) {
			case 1:
				_localctx = new TermVarContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(188);
				variable();
				}
				break;
			case 2:
				_localctx = new TermAtomContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(189);
				atom();
				}
				break;
			case 3:
				_localctx = new TermWildcardContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(190);
				match(UNDERSCORE);
				}
				break;
			case 4:
				_localctx = new TermStringContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(191);
				match(STRING);
				}
				break;
			case 5:
				_localctx = new TermNumberContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(192);
				match(NUMBER);
				}
				break;
			case 6:
				_localctx = new TermIDContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(193);
				match(ID);
				}
				break;
			case 7:
				_localctx = new TermListContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(194);
				match(BRACKET_OPEN);
				setState(214);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BRACKET_OPEN) | (1L << UNDERSCORE) | (1L << STRING) | (1L << NUMBER) | (1L << ID) | (1L << HID))) != 0)) {
					{
					setState(195);
					term();
					setState(200);
					_errHandler.sync(this);
					_la = _input.LA(1);
					while (_la==COMMA) {
						{
						{
						setState(196);
						match(COMMA);
						setState(197);
						term();
						}
						}
						setState(202);
						_errHandler.sync(this);
						_la = _input.LA(1);
					}
					setState(212);
					_errHandler.sync(this);
					_la = _input.LA(1);
					if (_la==VBAR) {
						{
						setState(203);
						match(VBAR);
						setState(204);
						term();
						setState(209);
						_errHandler.sync(this);
						_la = _input.LA(1);
						while (_la==COMMA) {
							{
							{
							setState(205);
							match(COMMA);
							setState(206);
							term();
							}
							}
							setState(211);
							_errHandler.sync(this);
							_la = _input.LA(1);
						}
						}
					}

					}
				}

				setState(216);
				match(BRACKET_CLOSE);
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

	public static class VariableContext extends ParserRuleContext {
		public TerminalNode HID() { return getToken(GraafvisParser.HID, 0); }
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
			match(HID);
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

	public static class AndOpContext extends ParserRuleContext {
		public TerminalNode COMMA() { return getToken(GraafvisParser.COMMA, 0); }
		public TerminalNode AND() { return getToken(GraafvisParser.AND, 0); }
		public AndOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_andOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterAndOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitAndOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitAndOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AndOpContext andOp() throws RecognitionException {
		AndOpContext _localctx = new AndOpContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_andOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			_la = _input.LA(1);
			if ( !(_la==COMMA || _la==AND) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class OrOpContext extends ParserRuleContext {
		public TerminalNode SEMICOLON() { return getToken(GraafvisParser.SEMICOLON, 0); }
		public TerminalNode OR() { return getToken(GraafvisParser.OR, 0); }
		public OrOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterOrOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitOrOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitOrOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OrOpContext orOp() throws RecognitionException {
		OrOpContext _localctx = new OrOpContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_orOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			_la = _input.LA(1);
			if ( !(_la==SEMICOLON || _la==OR) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 7:
			return propositionalFormula_sempred((PropositionalFormulaContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean propositionalFormula_sempred(PropositionalFormulaContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3+\u00e4\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\7\2*\n\2\f\2\16\2-\13\2\3\2\5\2\60\n\2\3\2\5"+
		"\2\63\n\2\3\2\7\2\66\n\2\f\2\16\29\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3"+
		"\4\3\4\3\4\3\4\7\4F\n\4\f\4\16\4I\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\7\5"+
		"R\n\5\f\5\16\5U\13\5\3\5\3\5\3\6\3\6\3\6\5\6\\\n\6\3\7\3\7\3\7\5\7a\n"+
		"\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\5\tp\n\t\3\t\3"+
		"\t\3\t\3\t\3\t\3\t\3\t\3\t\7\tz\n\t\f\t\16\t}\13\t\3\n\3\n\3\n\3\n\7\n"+
		"\u0083\n\n\f\n\16\n\u0086\13\n\3\13\3\13\5\13\u008a\n\13\3\f\3\f\5\f\u008e"+
		"\n\f\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u0096\n\r\f\r\16\r\u0099\13\r\3\r\3\r"+
		"\3\r\3\r\3\r\3\r\3\r\3\r\7\r\u00a3\n\r\f\r\16\r\u00a6\13\r\3\r\3\r\5\r"+
		"\u00aa\n\r\3\16\3\16\5\16\u00ae\n\16\3\17\3\17\3\17\3\17\7\17\u00b4\n"+
		"\17\f\17\16\17\u00b7\13\17\5\17\u00b9\n\17\3\17\3\17\3\20\3\20\3\21\3"+
		"\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\3\21\7\21\u00c9\n\21\f\21\16\21"+
		"\u00cc\13\21\3\21\3\21\3\21\3\21\7\21\u00d2\n\21\f\21\16\21\u00d5\13\21"+
		"\5\21\u00d7\n\21\5\21\u00d9\n\21\3\21\5\21\u00dc\n\21\3\22\3\22\3\23\3"+
		"\23\3\24\3\24\3\24\2\3\20\25\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \""+
		"$&\2\4\4\2\23\23\26\26\3\2\24\25\u00ef\2+\3\2\2\2\4<\3\2\2\2\6@\3\2\2"+
		"\2\bL\3\2\2\2\nX\3\2\2\2\f`\3\2\2\2\16e\3\2\2\2\20o\3\2\2\2\22~\3\2\2"+
		"\2\24\u0089\3\2\2\2\26\u008b\3\2\2\2\30\u00a9\3\2\2\2\32\u00ad\3\2\2\2"+
		"\34\u00af\3\2\2\2\36\u00bc\3\2\2\2 \u00db\3\2\2\2\"\u00dd\3\2\2\2$\u00df"+
		"\3\2\2\2&\u00e1\3\2\2\2(*\5\4\3\2)(\3\2\2\2*-\3\2\2\2+)\3\2\2\2+,\3\2"+
		"\2\2,/\3\2\2\2-+\3\2\2\2.\60\5\6\4\2/.\3\2\2\2/\60\3\2\2\2\60\62\3\2\2"+
		"\2\61\63\5\b\5\2\62\61\3\2\2\2\62\63\3\2\2\2\63\67\3\2\2\2\64\66\5\f\7"+
		"\2\65\64\3\2\2\2\669\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28:\3\2\2\29\67\3"+
		"\2\2\2:;\7\2\2\3;\3\3\2\2\2<=\7!\2\2=>\7%\2\2>?\7\5\2\2?\5\3\2\2\2@A\7"+
		"\"\2\2AB\7\4\2\2BG\5\n\6\2CD\7\23\2\2DF\5\n\6\2EC\3\2\2\2FI\3\2\2\2GE"+
		"\3\2\2\2GH\3\2\2\2HJ\3\2\2\2IG\3\2\2\2JK\7\5\2\2K\7\3\2\2\2LM\7#\2\2M"+
		"N\7\4\2\2NS\5\n\6\2OP\7\23\2\2PR\5\n\6\2QO\3\2\2\2RU\3\2\2\2SQ\3\2\2\2"+
		"ST\3\2\2\2TV\3\2\2\2US\3\2\2\2VW\7\5\2\2W\t\3\2\2\2X[\7%\2\2YZ\7$\2\2"+
		"Z\\\7\'\2\2[Y\3\2\2\2[\\\3\2\2\2\\\13\3\2\2\2]^\5\16\b\2^_\7\3\2\2_a\3"+
		"\2\2\2`]\3\2\2\2`a\3\2\2\2ab\3\2\2\2bc\5\22\n\2cd\7\5\2\2d\r\3\2\2\2e"+
		"f\5\20\t\2f\17\3\2\2\2gh\b\t\1\2hi\7\27\2\2ip\5\20\t\7jk\7\6\2\2kl\5\20"+
		"\t\2lm\7\7\2\2mp\3\2\2\2np\5\24\13\2og\3\2\2\2oj\3\2\2\2on\3\2\2\2p{\3"+
		"\2\2\2qr\f\6\2\2rs\5$\23\2st\5\20\t\7tz\3\2\2\2uv\f\5\2\2vw\5&\24\2wx"+
		"\5\20\t\6xz\3\2\2\2yq\3\2\2\2yu\3\2\2\2z}\3\2\2\2{y\3\2\2\2{|\3\2\2\2"+
		"|\21\3\2\2\2}{\3\2\2\2~\u0084\5\24\13\2\177\u0080\5$\23\2\u0080\u0081"+
		"\5\24\13\2\u0081\u0083\3\2\2\2\u0082\177\3\2\2\2\u0083\u0086\3\2\2\2\u0084"+
		"\u0082\3\2\2\2\u0084\u0085\3\2\2\2\u0085\23\3\2\2\2\u0086\u0084\3\2\2"+
		"\2\u0087\u008a\5\26\f\2\u0088\u008a\5\30\r\2\u0089\u0087\3\2\2\2\u0089"+
		"\u0088\3\2\2\2\u008a\25\3\2\2\2\u008b\u008d\5\36\20\2\u008c\u008e\5\34"+
		"\17\2\u008d\u008c\3\2\2\2\u008d\u008e\3\2\2\2\u008e\27\3\2\2\2\u008f\u0090"+
		"\5\36\20\2\u0090\u0091\7\b\2\2\u0091\u0097\5\32\16\2\u0092\u0093\5$\23"+
		"\2\u0093\u0094\5\32\16\2\u0094\u0096\3\2\2\2\u0095\u0092\3\2\2\2\u0096"+
		"\u0099\3\2\2\2\u0097\u0095\3\2\2\2\u0097\u0098\3\2\2\2\u0098\u009a\3\2"+
		"\2\2\u0099\u0097\3\2\2\2\u009a\u009b\7\t\2\2\u009b\u00aa\3\2\2\2\u009c"+
		"\u009d\5\36\20\2\u009d\u009e\7\b\2\2\u009e\u00a4\5\32\16\2\u009f\u00a0"+
		"\5&\24\2\u00a0\u00a1\5\32\16\2\u00a1\u00a3\3\2\2\2\u00a2\u009f\3\2\2\2"+
		"\u00a3\u00a6\3\2\2\2\u00a4\u00a2\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a7"+
		"\3\2\2\2\u00a6\u00a4\3\2\2\2\u00a7\u00a8\7\t\2\2\u00a8\u00aa\3\2\2\2\u00a9"+
		"\u008f\3\2\2\2\u00a9\u009c\3\2\2\2\u00aa\31\3\2\2\2\u00ab\u00ae\5 \21"+
		"\2\u00ac\u00ae\5\34\17\2\u00ad\u00ab\3\2\2\2\u00ad\u00ac\3\2\2\2\u00ae"+
		"\33\3\2\2\2\u00af\u00b8\7\6\2\2\u00b0\u00b5\5 \21\2\u00b1\u00b2\7\23\2"+
		"\2\u00b2\u00b4\5 \21\2\u00b3\u00b1\3\2\2\2\u00b4\u00b7\3\2\2\2\u00b5\u00b3"+
		"\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b6\u00b9\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b8"+
		"\u00b0\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb\7\7"+
		"\2\2\u00bb\35\3\2\2\2\u00bc\u00bd\7\'\2\2\u00bd\37\3\2\2\2\u00be\u00dc"+
		"\5\"\22\2\u00bf\u00dc\5\26\f\2\u00c0\u00dc\7\36\2\2\u00c1\u00dc\7%\2\2"+
		"\u00c2\u00dc\7&\2\2\u00c3\u00dc\7\'\2\2\u00c4\u00d8\7\n\2\2\u00c5\u00ca"+
		"\5 \21\2\u00c6\u00c7\7\23\2\2\u00c7\u00c9\5 \21\2\u00c8\u00c6\3\2\2\2"+
		"\u00c9\u00cc\3\2\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00d6"+
		"\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cd\u00ce\7\f\2\2\u00ce\u00d3\5 \21\2\u00cf"+
		"\u00d0\7\23\2\2\u00d0\u00d2\5 \21\2\u00d1\u00cf\3\2\2\2\u00d2\u00d5\3"+
		"\2\2\2\u00d3\u00d1\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d7\3\2\2\2\u00d5"+
		"\u00d3\3\2\2\2\u00d6\u00cd\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d9\3\2"+
		"\2\2\u00d8\u00c5\3\2\2\2\u00d8\u00d9\3\2\2\2\u00d9\u00da\3\2\2\2\u00da"+
		"\u00dc\7\13\2\2\u00db\u00be\3\2\2\2\u00db\u00bf\3\2\2\2\u00db\u00c0\3"+
		"\2\2\2\u00db\u00c1\3\2\2\2\u00db\u00c2\3\2\2\2\u00db\u00c3\3\2\2\2\u00db"+
		"\u00c4\3\2\2\2\u00dc!\3\2\2\2\u00dd\u00de\7(\2\2\u00de#\3\2\2\2\u00df"+
		"\u00e0\t\2\2\2\u00e0%\3\2\2\2\u00e1\u00e2\t\3\2\2\u00e2\'\3\2\2\2\33+"+
		"/\62\67GS[`oy{\u0084\u0089\u008d\u0097\u00a4\u00a9\u00ad\u00b5\u00b8\u00ca"+
		"\u00d3\u00d6\u00d8\u00db";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}