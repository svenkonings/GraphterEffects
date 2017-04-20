// Generated from C:/Users/Sven/Documents/GitHub/GraphterEffects/src/main/java/graafvis\Graafvis.g4 by ANTLR 4.6
package graafvis;
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
		EQ=8, NQ=9, GT=10, LT=11, GE=12, LE=13, COMMA=14, OR=15, AND=16, NOT=17, 
		PLUS=18, MINUS=19, MULT=20, DIV=21, POW=22, MOD=23, UNDERSCORE=24, TRUE=25, 
		FALSE=26, IMPORT_TOKEN=27, NODE_LABEL_TOKEN=28, EDGE_LABEL_TOKEN=29, RENAME_TOKEN=30, 
		STRING=31, NUMBER=32, ID=33, HID=34, WS=35, BLOCKCOMMENT=36, LINECOMMENT=37;
	public static final int
		RULE_program = 0, RULE_import_vis = 1, RULE_node_label_gen = 2, RULE_edge_label_gen = 3, 
		RULE_label = 4, RULE_clause = 5, RULE_antecedent = 6, RULE_propositional_formula = 7, 
		RULE_consequence = 8, RULE_literal = 9, RULE_atom = 10, RULE_multi_atom = 11, 
		RULE_predicate = 12, RULE_term = 13, RULE_ground_term = 14, RULE_variable = 15, 
		RULE_num_expr = 16, RULE_and_op = 17, RULE_eq_op = 18, RULE_pow_op = 19, 
		RULE_mult_op = 20, RULE_plus_op = 21;
	public static final String[] ruleNames = {
		"program", "import_vis", "node_label_gen", "edge_label_gen", "label", 
		"clause", "antecedent", "propositional_formula", "consequence", "literal", 
		"atom", "multi_atom", "predicate", "term", "ground_term", "variable", 
		"num_expr", "and_op", "eq_op", "pow_op", "mult_op", "plus_op"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'->'", "':'", "'.'", "'('", "')'", "'{'", "'}'", "'=='", "'!='", 
		"'>'", "'<'", "'>='", "'<='", "','", "'or'", "'and'", "'not'", "'+'", 
		"'-'", "'*'", "'/'", "'^'", "'%'", "'_'", "'true'", "'false'", "'consult'", 
		"'node labels'", "'edge labels'", "'as'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "ARROW", "COLON", "EOL", "PAR_OPEN", "PAR_CLOSE", "BRACE_OPEN", 
		"BRACE_CLOSE", "EQ", "NQ", "GT", "LT", "GE", "LE", "COMMA", "OR", "AND", 
		"NOT", "PLUS", "MINUS", "MULT", "DIV", "POW", "MOD", "UNDERSCORE", "TRUE", 
		"FALSE", "IMPORT_TOKEN", "NODE_LABEL_TOKEN", "EDGE_LABEL_TOKEN", "RENAME_TOKEN", 
		"STRING", "NUMBER", "ID", "HID", "WS", "BLOCKCOMMENT", "LINECOMMENT"
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
		public List<Import_visContext> import_vis() {
			return getRuleContexts(Import_visContext.class);
		}
		public Import_visContext import_vis(int i) {
			return getRuleContext(Import_visContext.class,i);
		}
		public Node_label_genContext node_label_gen() {
			return getRuleContext(Node_label_genContext.class,0);
		}
		public Edge_label_genContext edge_label_gen() {
			return getRuleContext(Edge_label_genContext.class,0);
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
			setState(47);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==IMPORT_TOKEN) {
				{
				{
				setState(44);
				import_vis();
				}
				}
				setState(49);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(51);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NODE_LABEL_TOKEN) {
				{
				setState(50);
				node_label_gen();
				}
			}

			setState(54);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==EDGE_LABEL_TOKEN) {
				{
				setState(53);
				edge_label_gen();
				}
			}

			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PAR_OPEN) | (1L << NOT) | (1L << NUMBER) | (1L << ID) | (1L << HID))) != 0)) {
				{
				{
				setState(56);
				clause();
				}
				}
				setState(61);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(62);
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

	public static class Import_visContext extends ParserRuleContext {
		public TerminalNode IMPORT_TOKEN() { return getToken(GraafvisParser.IMPORT_TOKEN, 0); }
		public TerminalNode STRING() { return getToken(GraafvisParser.STRING, 0); }
		public TerminalNode EOL() { return getToken(GraafvisParser.EOL, 0); }
		public Import_visContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_import_vis; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterImport_vis(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitImport_vis(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitImport_vis(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Import_visContext import_vis() throws RecognitionException {
		Import_visContext _localctx = new Import_visContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_import_vis);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(IMPORT_TOKEN);
			setState(65);
			match(STRING);
			setState(66);
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

	public static class Node_label_genContext extends ParserRuleContext {
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
		public Node_label_genContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_node_label_gen; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterNode_label_gen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitNode_label_gen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitNode_label_gen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Node_label_genContext node_label_gen() throws RecognitionException {
		Node_label_genContext _localctx = new Node_label_genContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_node_label_gen);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68);
			match(NODE_LABEL_TOKEN);
			setState(69);
			match(COLON);
			setState(70);
			label();
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(71);
				match(COMMA);
				setState(72);
				label();
				}
				}
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(78);
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

	public static class Edge_label_genContext extends ParserRuleContext {
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
		public Edge_label_genContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edge_label_gen; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterEdge_label_gen(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitEdge_label_gen(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitEdge_label_gen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Edge_label_genContext edge_label_gen() throws RecognitionException {
		Edge_label_genContext _localctx = new Edge_label_genContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_edge_label_gen);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(EDGE_LABEL_TOKEN);
			setState(81);
			match(COLON);
			setState(82);
			label();
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(83);
				match(COMMA);
				setState(84);
				label();
				}
				}
				setState(89);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(90);
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
			setState(92);
			match(STRING);
			setState(95);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==RENAME_TOKEN) {
				{
				setState(93);
				match(RENAME_TOKEN);
				setState(94);
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
			setState(100);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(97);
				antecedent();
				setState(98);
				match(ARROW);
				}
				break;
			}
			setState(102);
			consequence();
			setState(103);
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
		public Propositional_formulaContext propositional_formula() {
			return getRuleContext(Propositional_formulaContext.class,0);
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
			setState(105);
			propositional_formula(0);
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

	public static class Propositional_formulaContext extends ParserRuleContext {
		public Propositional_formulaContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propositional_formula; }
	 
		public Propositional_formulaContext() { }
		public void copyFrom(Propositional_formulaContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class PfNotContext extends Propositional_formulaContext {
		public TerminalNode NOT() { return getToken(GraafvisParser.NOT, 0); }
		public Propositional_formulaContext propositional_formula() {
			return getRuleContext(Propositional_formulaContext.class,0);
		}
		public PfNotContext(Propositional_formulaContext ctx) { copyFrom(ctx); }
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
	public static class PfLitContext extends Propositional_formulaContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public PfLitContext(Propositional_formulaContext ctx) { copyFrom(ctx); }
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
	public static class PfAndContext extends Propositional_formulaContext {
		public List<Propositional_formulaContext> propositional_formula() {
			return getRuleContexts(Propositional_formulaContext.class);
		}
		public Propositional_formulaContext propositional_formula(int i) {
			return getRuleContext(Propositional_formulaContext.class,i);
		}
		public And_opContext and_op() {
			return getRuleContext(And_opContext.class,0);
		}
		public PfAndContext(Propositional_formulaContext ctx) { copyFrom(ctx); }
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
	public static class PfOrContext extends Propositional_formulaContext {
		public List<Propositional_formulaContext> propositional_formula() {
			return getRuleContexts(Propositional_formulaContext.class);
		}
		public Propositional_formulaContext propositional_formula(int i) {
			return getRuleContext(Propositional_formulaContext.class,i);
		}
		public TerminalNode OR() { return getToken(GraafvisParser.OR, 0); }
		public PfOrContext(Propositional_formulaContext ctx) { copyFrom(ctx); }
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
	public static class PfNestContext extends Propositional_formulaContext {
		public TerminalNode PAR_OPEN() { return getToken(GraafvisParser.PAR_OPEN, 0); }
		public Propositional_formulaContext propositional_formula() {
			return getRuleContext(Propositional_formulaContext.class,0);
		}
		public TerminalNode PAR_CLOSE() { return getToken(GraafvisParser.PAR_CLOSE, 0); }
		public PfNestContext(Propositional_formulaContext ctx) { copyFrom(ctx); }
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

	public final Propositional_formulaContext propositional_formula() throws RecognitionException {
		return propositional_formula(0);
	}

	private Propositional_formulaContext propositional_formula(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Propositional_formulaContext _localctx = new Propositional_formulaContext(_ctx, _parentState);
		Propositional_formulaContext _prevctx = _localctx;
		int _startState = 14;
		enterRecursionRule(_localctx, 14, RULE_propositional_formula, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				_localctx = new PfNotContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(108);
				match(NOT);
				setState(109);
				propositional_formula(5);
				}
				break;
			case 2:
				{
				_localctx = new PfNestContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(110);
				match(PAR_OPEN);
				setState(111);
				propositional_formula(0);
				setState(112);
				match(PAR_CLOSE);
				}
				break;
			case 3:
				{
				_localctx = new PfLitContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(114);
				literal();
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(126);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,10,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(124);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
					case 1:
						{
						_localctx = new PfAndContext(new Propositional_formulaContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_propositional_formula);
						setState(117);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(118);
						and_op();
						setState(119);
						propositional_formula(5);
						}
						break;
					case 2:
						{
						_localctx = new PfOrContext(new Propositional_formulaContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_propositional_formula);
						setState(121);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(122);
						match(OR);
						setState(123);
						propositional_formula(4);
						}
						break;
					}
					} 
				}
				setState(128);
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
		public List<TerminalNode> COMMA() { return getTokens(GraafvisParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraafvisParser.COMMA, i);
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
			setState(129);
			literal();
			setState(134);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(130);
				match(COMMA);
				setState(131);
				literal();
				}
				}
				setState(136);
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
	public static class NumExprLiteralContext extends LiteralContext {
		public List<Num_exprContext> num_expr() {
			return getRuleContexts(Num_exprContext.class);
		}
		public Num_exprContext num_expr(int i) {
			return getRuleContext(Num_exprContext.class,i);
		}
		public Eq_opContext eq_op() {
			return getRuleContext(Eq_opContext.class,0);
		}
		public NumExprLiteralContext(LiteralContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterNumExprLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitNumExprLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitNumExprLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class MultiAtomLiteralContext extends LiteralContext {
		public Multi_atomContext multi_atom() {
			return getRuleContext(Multi_atomContext.class,0);
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
			setState(143);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,12,_ctx) ) {
			case 1:
				_localctx = new AtomLiteralContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(137);
				atom();
				}
				break;
			case 2:
				_localctx = new MultiAtomLiteralContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(138);
				multi_atom();
				}
				break;
			case 3:
				_localctx = new NumExprLiteralContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(139);
				num_expr(0);
				setState(140);
				eq_op();
				setState(141);
				num_expr(0);
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
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			predicate();
			setState(146);
			match(PAR_OPEN);
			setState(155);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PAR_OPEN) | (1L << UNDERSCORE) | (1L << STRING) | (1L << NUMBER) | (1L << ID) | (1L << HID))) != 0)) {
				{
				setState(147);
				term();
				setState(152);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(148);
					match(COMMA);
					setState(149);
					term();
					}
					}
					setState(154);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(157);
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

	public static class Multi_atomContext extends ParserRuleContext {
		public PredicateContext predicate() {
			return getRuleContext(PredicateContext.class,0);
		}
		public TerminalNode BRACE_OPEN() { return getToken(GraafvisParser.BRACE_OPEN, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public TerminalNode BRACE_CLOSE() { return getToken(GraafvisParser.BRACE_CLOSE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GraafvisParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraafvisParser.COMMA, i);
		}
		public Multi_atomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_multi_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterMulti_atom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitMulti_atom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitMulti_atom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Multi_atomContext multi_atom() throws RecognitionException {
		Multi_atomContext _localctx = new Multi_atomContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_multi_atom);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(159);
			predicate();
			setState(160);
			match(BRACE_OPEN);
			setState(161);
			term();
			setState(166);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(162);
				match(COMMA);
				setState(163);
				term();
				}
				}
				setState(168);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(169);
			match(BRACE_CLOSE);
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
		enterRule(_localctx, 24, RULE_predicate);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(171);
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
	public static class TupleContext extends TermContext {
		public TerminalNode PAR_OPEN() { return getToken(GraafvisParser.PAR_OPEN, 0); }
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public TerminalNode PAR_CLOSE() { return getToken(GraafvisParser.PAR_CLOSE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(GraafvisParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GraafvisParser.COMMA, i);
		}
		public TupleContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterTuple(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitTuple(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitTuple(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class TermGroundContext extends TermContext {
		public Ground_termContext ground_term() {
			return getRuleContext(Ground_termContext.class,0);
		}
		public TermGroundContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterTermGround(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitTermGround(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitTermGround(this);
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
	public static class WildcardContext extends TermContext {
		public TerminalNode UNDERSCORE() { return getToken(GraafvisParser.UNDERSCORE, 0); }
		public WildcardContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterWildcard(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitWildcard(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitWildcard(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_term);
		int _la;
		try {
			setState(187);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING:
			case NUMBER:
			case ID:
				_localctx = new TermGroundContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(173);
				ground_term();
				}
				break;
			case HID:
				_localctx = new TermVarContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(174);
				variable();
				}
				break;
			case UNDERSCORE:
				_localctx = new WildcardContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(175);
				match(UNDERSCORE);
				}
				break;
			case PAR_OPEN:
				_localctx = new TupleContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(176);
				match(PAR_OPEN);
				setState(177);
				term();
				setState(182);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(178);
					match(COMMA);
					setState(179);
					term();
					}
					}
					setState(184);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(185);
				match(PAR_CLOSE);
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

	public static class Ground_termContext extends ParserRuleContext {
		public TerminalNode STRING() { return getToken(GraafvisParser.STRING, 0); }
		public TerminalNode NUMBER() { return getToken(GraafvisParser.NUMBER, 0); }
		public TerminalNode ID() { return getToken(GraafvisParser.ID, 0); }
		public Ground_termContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ground_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterGround_term(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitGround_term(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitGround_term(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Ground_termContext ground_term() throws RecognitionException {
		Ground_termContext _localctx = new Ground_termContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_ground_term);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(189);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << STRING) | (1L << NUMBER) | (1L << ID))) != 0)) ) {
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
		enterRule(_localctx, 30, RULE_variable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
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

	public static class Num_exprContext extends ParserRuleContext {
		public TerminalNode PAR_OPEN() { return getToken(GraafvisParser.PAR_OPEN, 0); }
		public List<Num_exprContext> num_expr() {
			return getRuleContexts(Num_exprContext.class);
		}
		public Num_exprContext num_expr(int i) {
			return getRuleContext(Num_exprContext.class,i);
		}
		public TerminalNode PAR_CLOSE() { return getToken(GraafvisParser.PAR_CLOSE, 0); }
		public VariableContext variable() {
			return getRuleContext(VariableContext.class,0);
		}
		public TerminalNode NUMBER() { return getToken(GraafvisParser.NUMBER, 0); }
		public Pow_opContext pow_op() {
			return getRuleContext(Pow_opContext.class,0);
		}
		public Mult_opContext mult_op() {
			return getRuleContext(Mult_opContext.class,0);
		}
		public Plus_opContext plus_op() {
			return getRuleContext(Plus_opContext.class,0);
		}
		public Num_exprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_num_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterNum_expr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitNum_expr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitNum_expr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Num_exprContext num_expr() throws RecognitionException {
		return num_expr(0);
	}

	private Num_exprContext num_expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		Num_exprContext _localctx = new Num_exprContext(_ctx, _parentState);
		Num_exprContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_num_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(200);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PAR_OPEN:
				{
				setState(194);
				match(PAR_OPEN);
				setState(195);
				num_expr(0);
				setState(196);
				match(PAR_CLOSE);
				}
				break;
			case HID:
				{
				setState(198);
				variable();
				}
				break;
			case NUMBER:
				{
				setState(199);
				match(NUMBER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(216);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(214);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,19,_ctx) ) {
					case 1:
						{
						_localctx = new Num_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_num_expr);
						setState(202);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(203);
						pow_op();
						setState(204);
						num_expr(7);
						}
						break;
					case 2:
						{
						_localctx = new Num_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_num_expr);
						setState(206);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(207);
						mult_op();
						setState(208);
						num_expr(6);
						}
						break;
					case 3:
						{
						_localctx = new Num_exprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_num_expr);
						setState(210);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(211);
						plus_op();
						setState(212);
						num_expr(5);
						}
						break;
					}
					} 
				}
				setState(218);
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

	public static class And_opContext extends ParserRuleContext {
		public TerminalNode COMMA() { return getToken(GraafvisParser.COMMA, 0); }
		public TerminalNode AND() { return getToken(GraafvisParser.AND, 0); }
		public And_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_and_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterAnd_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitAnd_op(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitAnd_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final And_opContext and_op() throws RecognitionException {
		And_opContext _localctx = new And_opContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_and_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(219);
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

	public static class Eq_opContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(GraafvisParser.EQ, 0); }
		public TerminalNode NQ() { return getToken(GraafvisParser.NQ, 0); }
		public TerminalNode GT() { return getToken(GraafvisParser.GT, 0); }
		public TerminalNode LT() { return getToken(GraafvisParser.LT, 0); }
		public TerminalNode GE() { return getToken(GraafvisParser.GE, 0); }
		public TerminalNode LE() { return getToken(GraafvisParser.LE, 0); }
		public Eq_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_eq_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterEq_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitEq_op(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitEq_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Eq_opContext eq_op() throws RecognitionException {
		Eq_opContext _localctx = new Eq_opContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_eq_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << EQ) | (1L << NQ) | (1L << GT) | (1L << LT) | (1L << GE) | (1L << LE))) != 0)) ) {
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

	public static class Pow_opContext extends ParserRuleContext {
		public TerminalNode POW() { return getToken(GraafvisParser.POW, 0); }
		public Pow_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pow_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterPow_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitPow_op(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitPow_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Pow_opContext pow_op() throws RecognitionException {
		Pow_opContext _localctx = new Pow_opContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_pow_op);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(223);
			match(POW);
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

	public static class Mult_opContext extends ParserRuleContext {
		public TerminalNode MULT() { return getToken(GraafvisParser.MULT, 0); }
		public TerminalNode DIV() { return getToken(GraafvisParser.DIV, 0); }
		public TerminalNode MOD() { return getToken(GraafvisParser.MOD, 0); }
		public Mult_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mult_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterMult_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitMult_op(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitMult_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Mult_opContext mult_op() throws RecognitionException {
		Mult_opContext _localctx = new Mult_opContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_mult_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(225);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << MULT) | (1L << DIV) | (1L << MOD))) != 0)) ) {
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

	public static class Plus_opContext extends ParserRuleContext {
		public TerminalNode PLUS() { return getToken(GraafvisParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(GraafvisParser.MINUS, 0); }
		public Plus_opContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_plus_op; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).enterPlus_op(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GraafvisListener ) ((GraafvisListener)listener).exitPlus_op(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GraafvisVisitor ) return ((GraafvisVisitor<? extends T>)visitor).visitPlus_op(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Plus_opContext plus_op() throws RecognitionException {
		Plus_opContext _localctx = new Plus_opContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_plus_op);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(227);
			_la = _input.LA(1);
			if ( !(_la==PLUS || _la==MINUS) ) {
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
			return propositional_formula_sempred((Propositional_formulaContext)_localctx, predIndex);
		case 16:
			return num_expr_sempred((Num_exprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean propositional_formula_sempred(Propositional_formulaContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 4);
		case 1:
			return precpred(_ctx, 3);
		}
		return true;
	}
	private boolean num_expr_sempred(Num_exprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 6);
		case 3:
			return precpred(_ctx, 5);
		case 4:
			return precpred(_ctx, 4);
		}
		return true;
	}

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\'\u00e8\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\3\2\7\2\60\n\2\f\2"+
		"\16\2\63\13\2\3\2\5\2\66\n\2\3\2\5\29\n\2\3\2\7\2<\n\2\f\2\16\2?\13\2"+
		"\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\7\4L\n\4\f\4\16\4O\13\4\3"+
		"\4\3\4\3\5\3\5\3\5\3\5\3\5\7\5X\n\5\f\5\16\5[\13\5\3\5\3\5\3\6\3\6\3\6"+
		"\5\6b\n\6\3\7\3\7\3\7\5\7g\n\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\3\t\3\t\3\t"+
		"\3\t\3\t\3\t\5\tv\n\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\7\t\177\n\t\f\t\16\t"+
		"\u0082\13\t\3\n\3\n\3\n\7\n\u0087\n\n\f\n\16\n\u008a\13\n\3\13\3\13\3"+
		"\13\3\13\3\13\3\13\5\13\u0092\n\13\3\f\3\f\3\f\3\f\3\f\7\f\u0099\n\f\f"+
		"\f\16\f\u009c\13\f\5\f\u009e\n\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\7\r\u00a7"+
		"\n\r\f\r\16\r\u00aa\13\r\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3"+
		"\17\3\17\7\17\u00b7\n\17\f\17\16\17\u00ba\13\17\3\17\3\17\5\17\u00be\n"+
		"\17\3\20\3\20\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\5\22\u00cb"+
		"\n\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\7\22"+
		"\u00d9\n\22\f\22\16\22\u00dc\13\22\3\23\3\23\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\27\3\27\3\27\2\4\20\"\30\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"$&(*,\2\7\3\2!#\4\2\20\20\22\22\3\2\n\17\4\2\26\27\31\31\3\2\24\25"+
		"\u00ec\2\61\3\2\2\2\4B\3\2\2\2\6F\3\2\2\2\bR\3\2\2\2\n^\3\2\2\2\ff\3\2"+
		"\2\2\16k\3\2\2\2\20u\3\2\2\2\22\u0083\3\2\2\2\24\u0091\3\2\2\2\26\u0093"+
		"\3\2\2\2\30\u00a1\3\2\2\2\32\u00ad\3\2\2\2\34\u00bd\3\2\2\2\36\u00bf\3"+
		"\2\2\2 \u00c1\3\2\2\2\"\u00ca\3\2\2\2$\u00dd\3\2\2\2&\u00df\3\2\2\2(\u00e1"+
		"\3\2\2\2*\u00e3\3\2\2\2,\u00e5\3\2\2\2.\60\5\4\3\2/.\3\2\2\2\60\63\3\2"+
		"\2\2\61/\3\2\2\2\61\62\3\2\2\2\62\65\3\2\2\2\63\61\3\2\2\2\64\66\5\6\4"+
		"\2\65\64\3\2\2\2\65\66\3\2\2\2\668\3\2\2\2\679\5\b\5\28\67\3\2\2\289\3"+
		"\2\2\29=\3\2\2\2:<\5\f\7\2;:\3\2\2\2<?\3\2\2\2=;\3\2\2\2=>\3\2\2\2>@\3"+
		"\2\2\2?=\3\2\2\2@A\7\2\2\3A\3\3\2\2\2BC\7\35\2\2CD\7!\2\2DE\7\5\2\2E\5"+
		"\3\2\2\2FG\7\36\2\2GH\7\4\2\2HM\5\n\6\2IJ\7\20\2\2JL\5\n\6\2KI\3\2\2\2"+
		"LO\3\2\2\2MK\3\2\2\2MN\3\2\2\2NP\3\2\2\2OM\3\2\2\2PQ\7\5\2\2Q\7\3\2\2"+
		"\2RS\7\37\2\2ST\7\4\2\2TY\5\n\6\2UV\7\20\2\2VX\5\n\6\2WU\3\2\2\2X[\3\2"+
		"\2\2YW\3\2\2\2YZ\3\2\2\2Z\\\3\2\2\2[Y\3\2\2\2\\]\7\5\2\2]\t\3\2\2\2^a"+
		"\7!\2\2_`\7 \2\2`b\7#\2\2a_\3\2\2\2ab\3\2\2\2b\13\3\2\2\2cd\5\16\b\2d"+
		"e\7\3\2\2eg\3\2\2\2fc\3\2\2\2fg\3\2\2\2gh\3\2\2\2hi\5\22\n\2ij\7\5\2\2"+
		"j\r\3\2\2\2kl\5\20\t\2l\17\3\2\2\2mn\b\t\1\2no\7\23\2\2ov\5\20\t\7pq\7"+
		"\6\2\2qr\5\20\t\2rs\7\7\2\2sv\3\2\2\2tv\5\24\13\2um\3\2\2\2up\3\2\2\2"+
		"ut\3\2\2\2v\u0080\3\2\2\2wx\f\6\2\2xy\5$\23\2yz\5\20\t\7z\177\3\2\2\2"+
		"{|\f\5\2\2|}\7\21\2\2}\177\5\20\t\6~w\3\2\2\2~{\3\2\2\2\177\u0082\3\2"+
		"\2\2\u0080~\3\2\2\2\u0080\u0081\3\2\2\2\u0081\21\3\2\2\2\u0082\u0080\3"+
		"\2\2\2\u0083\u0088\5\24\13\2\u0084\u0085\7\20\2\2\u0085\u0087\5\24\13"+
		"\2\u0086\u0084\3\2\2\2\u0087\u008a\3\2\2\2\u0088\u0086\3\2\2\2\u0088\u0089"+
		"\3\2\2\2\u0089\23\3\2\2\2\u008a\u0088\3\2\2\2\u008b\u0092\5\26\f\2\u008c"+
		"\u0092\5\30\r\2\u008d\u008e\5\"\22\2\u008e\u008f\5&\24\2\u008f\u0090\5"+
		"\"\22\2\u0090\u0092\3\2\2\2\u0091\u008b\3\2\2\2\u0091\u008c\3\2\2\2\u0091"+
		"\u008d\3\2\2\2\u0092\25\3\2\2\2\u0093\u0094\5\32\16\2\u0094\u009d\7\6"+
		"\2\2\u0095\u009a\5\34\17\2\u0096\u0097\7\20\2\2\u0097\u0099\5\34\17\2"+
		"\u0098\u0096\3\2\2\2\u0099\u009c\3\2\2\2\u009a\u0098\3\2\2\2\u009a\u009b"+
		"\3\2\2\2\u009b\u009e\3\2\2\2\u009c\u009a\3\2\2\2\u009d\u0095\3\2\2\2\u009d"+
		"\u009e\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a0\7\7\2\2\u00a0\27\3\2\2"+
		"\2\u00a1\u00a2\5\32\16\2\u00a2\u00a3\7\b\2\2\u00a3\u00a8\5\34\17\2\u00a4"+
		"\u00a5\7\20\2\2\u00a5\u00a7\5\34\17\2\u00a6\u00a4\3\2\2\2\u00a7\u00aa"+
		"\3\2\2\2\u00a8\u00a6\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9\u00ab\3\2\2\2\u00aa"+
		"\u00a8\3\2\2\2\u00ab\u00ac\7\t\2\2\u00ac\31\3\2\2\2\u00ad\u00ae\7#\2\2"+
		"\u00ae\33\3\2\2\2\u00af\u00be\5\36\20\2\u00b0\u00be\5 \21\2\u00b1\u00be"+
		"\7\32\2\2\u00b2\u00b3\7\6\2\2\u00b3\u00b8\5\34\17\2\u00b4\u00b5\7\20\2"+
		"\2\u00b5\u00b7\5\34\17\2\u00b6\u00b4\3\2\2\2\u00b7\u00ba\3\2\2\2\u00b8"+
		"\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00bb\3\2\2\2\u00ba\u00b8\3\2"+
		"\2\2\u00bb\u00bc\7\7\2\2\u00bc\u00be\3\2\2\2\u00bd\u00af\3\2\2\2\u00bd"+
		"\u00b0\3\2\2\2\u00bd\u00b1\3\2\2\2\u00bd\u00b2\3\2\2\2\u00be\35\3\2\2"+
		"\2\u00bf\u00c0\t\2\2\2\u00c0\37\3\2\2\2\u00c1\u00c2\7$\2\2\u00c2!\3\2"+
		"\2\2\u00c3\u00c4\b\22\1\2\u00c4\u00c5\7\6\2\2\u00c5\u00c6\5\"\22\2\u00c6"+
		"\u00c7\7\7\2\2\u00c7\u00cb\3\2\2\2\u00c8\u00cb\5 \21\2\u00c9\u00cb\7\""+
		"\2\2\u00ca\u00c3\3\2\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00c9\3\2\2\2\u00cb"+
		"\u00da\3\2\2\2\u00cc\u00cd\f\b\2\2\u00cd\u00ce\5(\25\2\u00ce\u00cf\5\""+
		"\22\t\u00cf\u00d9\3\2\2\2\u00d0\u00d1\f\7\2\2\u00d1\u00d2\5*\26\2\u00d2"+
		"\u00d3\5\"\22\b\u00d3\u00d9\3\2\2\2\u00d4\u00d5\f\6\2\2\u00d5\u00d6\5"+
		",\27\2\u00d6\u00d7\5\"\22\7\u00d7\u00d9\3\2\2\2\u00d8\u00cc\3\2\2\2\u00d8"+
		"\u00d0\3\2\2\2\u00d8\u00d4\3\2\2\2\u00d9\u00dc\3\2\2\2\u00da\u00d8\3\2"+
		"\2\2\u00da\u00db\3\2\2\2\u00db#\3\2\2\2\u00dc\u00da\3\2\2\2\u00dd\u00de"+
		"\t\3\2\2\u00de%\3\2\2\2\u00df\u00e0\t\4\2\2\u00e0\'\3\2\2\2\u00e1\u00e2"+
		"\7\30\2\2\u00e2)\3\2\2\2\u00e3\u00e4\t\5\2\2\u00e4+\3\2\2\2\u00e5\u00e6"+
		"\t\6\2\2\u00e6-\3\2\2\2\27\61\658=MYafu~\u0080\u0088\u0091\u009a\u009d"+
		"\u00a8\u00b8\u00bd\u00ca\u00d8\u00da";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}