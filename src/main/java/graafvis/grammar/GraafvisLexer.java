// Generated from C:/Users/poesd_000/IdeaProjects/GraphterEffects/src/main/java/graafvis/grammar\Graafvis.g4 by ANTLR 4.6
package graafvis.grammar;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class GraafvisLexer extends Lexer {
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
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"LETTER_LO", "LETTER_HI", "ARROW", "COLON", "EOL", "PAR_OPEN", "PAR_CLOSE", 
		"BRACE_OPEN", "BRACE_CLOSE", "BRACKET_OPEN", "BRACKET_CLOSE", "VBAR", 
		"EQ", "NQ", "GT", "LT", "GE", "LE", "COMMA", "SEMICOLON", "OR", "AND", 
		"NOT", "PLUS", "MINUS", "MULT", "DIV", "POW", "MOD", "UNDERSCORE", "TRUE", 
		"FALSE", "IMPORT_TOKEN", "NODE_LABEL_TOKEN", "EDGE_LABEL_TOKEN", "RENAME_TOKEN", 
		"STRING", "NUMBER", "ID", "HID", "WS", "BLOCKCOMMENT", "LINECOMMENT"
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


	public GraafvisLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Graafvis.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2+\u0112\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\3\2\3\2\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3"+
		"\t\3\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20"+
		"\3\20\3\21\3\21\3\22\3\22\3\22\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\32\3\32"+
		"\3\33\3\33\3\34\3\34\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3 \3 \3!\3"+
		"!\3!\3!\3!\3!\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3#\3#\3#"+
		"\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3$\3%\3%\3%\3&\3&\7&\u00d0"+
		"\n&\f&\16&\u00d3\13&\3&\3&\3\'\5\'\u00d8\n\'\3\'\6\'\u00db\n\'\r\'\16"+
		"\'\u00dc\3(\3(\3(\3(\3(\7(\u00e4\n(\f(\16(\u00e7\13(\3)\3)\3)\3)\3)\7"+
		")\u00ee\n)\f)\16)\u00f1\13)\3*\6*\u00f4\n*\r*\16*\u00f5\3*\3*\3+\3+\3"+
		"+\3+\7+\u00fe\n+\f+\16+\u0101\13+\3+\3+\3+\3+\3+\3,\3,\3,\3,\7,\u010c"+
		"\n,\f,\16,\u010f\13,\3,\3,\3\u00ff\2-\3\2\5\2\7\3\t\4\13\5\r\6\17\7\21"+
		"\b\23\t\25\n\27\13\31\f\33\r\35\16\37\17!\20#\21%\22\'\23)\24+\25-\26"+
		"/\27\61\30\63\31\65\32\67\339\34;\35=\36?\37A C!E\"G#I$K%M&O\'Q(S)U*W"+
		"+\3\2\b\3\2c|\3\2C\\\3\2$$\3\2\62;\5\2\13\f\17\17\"\"\4\2\f\f\17\17\u011d"+
		"\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3"+
		"\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2"+
		"\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2"+
		"\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2"+
		"\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2"+
		"\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2"+
		"\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\3Y"+
		"\3\2\2\2\5[\3\2\2\2\7]\3\2\2\2\t`\3\2\2\2\13b\3\2\2\2\rd\3\2\2\2\17f\3"+
		"\2\2\2\21h\3\2\2\2\23j\3\2\2\2\25l\3\2\2\2\27n\3\2\2\2\31p\3\2\2\2\33"+
		"r\3\2\2\2\35u\3\2\2\2\37x\3\2\2\2!z\3\2\2\2#|\3\2\2\2%\177\3\2\2\2\'\u0082"+
		"\3\2\2\2)\u0084\3\2\2\2+\u0086\3\2\2\2-\u0089\3\2\2\2/\u008d\3\2\2\2\61"+
		"\u0091\3\2\2\2\63\u0093\3\2\2\2\65\u0095\3\2\2\2\67\u0097\3\2\2\29\u0099"+
		"\3\2\2\2;\u009b\3\2\2\2=\u009d\3\2\2\2?\u009f\3\2\2\2A\u00a4\3\2\2\2C"+
		"\u00aa\3\2\2\2E\u00b2\3\2\2\2G\u00be\3\2\2\2I\u00ca\3\2\2\2K\u00cd\3\2"+
		"\2\2M\u00d7\3\2\2\2O\u00de\3\2\2\2Q\u00e8\3\2\2\2S\u00f3\3\2\2\2U\u00f9"+
		"\3\2\2\2W\u0107\3\2\2\2YZ\t\2\2\2Z\4\3\2\2\2[\\\t\3\2\2\\\6\3\2\2\2]^"+
		"\7/\2\2^_\7@\2\2_\b\3\2\2\2`a\7<\2\2a\n\3\2\2\2bc\7\60\2\2c\f\3\2\2\2"+
		"de\7*\2\2e\16\3\2\2\2fg\7+\2\2g\20\3\2\2\2hi\7}\2\2i\22\3\2\2\2jk\7\177"+
		"\2\2k\24\3\2\2\2lm\7]\2\2m\26\3\2\2\2no\7_\2\2o\30\3\2\2\2pq\7~\2\2q\32"+
		"\3\2\2\2rs\7?\2\2st\7?\2\2t\34\3\2\2\2uv\7#\2\2vw\7?\2\2w\36\3\2\2\2x"+
		"y\7@\2\2y \3\2\2\2z{\7>\2\2{\"\3\2\2\2|}\7@\2\2}~\7?\2\2~$\3\2\2\2\177"+
		"\u0080\7>\2\2\u0080\u0081\7?\2\2\u0081&\3\2\2\2\u0082\u0083\7.\2\2\u0083"+
		"(\3\2\2\2\u0084\u0085\7=\2\2\u0085*\3\2\2\2\u0086\u0087\7q\2\2\u0087\u0088"+
		"\7t\2\2\u0088,\3\2\2\2\u0089\u008a\7c\2\2\u008a\u008b\7p\2\2\u008b\u008c"+
		"\7f\2\2\u008c.\3\2\2\2\u008d\u008e\7p\2\2\u008e\u008f\7q\2\2\u008f\u0090"+
		"\7v\2\2\u0090\60\3\2\2\2\u0091\u0092\7-\2\2\u0092\62\3\2\2\2\u0093\u0094"+
		"\7/\2\2\u0094\64\3\2\2\2\u0095\u0096\7,\2\2\u0096\66\3\2\2\2\u0097\u0098"+
		"\7\61\2\2\u00988\3\2\2\2\u0099\u009a\7`\2\2\u009a:\3\2\2\2\u009b\u009c"+
		"\7\'\2\2\u009c<\3\2\2\2\u009d\u009e\7a\2\2\u009e>\3\2\2\2\u009f\u00a0"+
		"\7v\2\2\u00a0\u00a1\7t\2\2\u00a1\u00a2\7w\2\2\u00a2\u00a3\7g\2\2\u00a3"+
		"@\3\2\2\2\u00a4\u00a5\7h\2\2\u00a5\u00a6\7c\2\2\u00a6\u00a7\7n\2\2\u00a7"+
		"\u00a8\7u\2\2\u00a8\u00a9\7g\2\2\u00a9B\3\2\2\2\u00aa\u00ab\7e\2\2\u00ab"+
		"\u00ac\7q\2\2\u00ac\u00ad\7p\2\2\u00ad\u00ae\7u\2\2\u00ae\u00af\7w\2\2"+
		"\u00af\u00b0\7n\2\2\u00b0\u00b1\7v\2\2\u00b1D\3\2\2\2\u00b2\u00b3\7p\2"+
		"\2\u00b3\u00b4\7q\2\2\u00b4\u00b5\7f\2\2\u00b5\u00b6\7g\2\2\u00b6\u00b7"+
		"\7\"\2\2\u00b7\u00b8\7n\2\2\u00b8\u00b9\7c\2\2\u00b9\u00ba\7d\2\2\u00ba"+
		"\u00bb\7g\2\2\u00bb\u00bc\7n\2\2\u00bc\u00bd\7u\2\2\u00bdF\3\2\2\2\u00be"+
		"\u00bf\7g\2\2\u00bf\u00c0\7f\2\2\u00c0\u00c1\7i\2\2\u00c1\u00c2\7g\2\2"+
		"\u00c2\u00c3\7\"\2\2\u00c3\u00c4\7n\2\2\u00c4\u00c5\7c\2\2\u00c5\u00c6"+
		"\7d\2\2\u00c6\u00c7\7g\2\2\u00c7\u00c8\7n\2\2\u00c8\u00c9\7u\2\2\u00c9"+
		"H\3\2\2\2\u00ca\u00cb\7c\2\2\u00cb\u00cc\7u\2\2\u00ccJ\3\2\2\2\u00cd\u00d1"+
		"\7$\2\2\u00ce\u00d0\n\4\2\2\u00cf\u00ce\3\2\2\2\u00d0\u00d3\3\2\2\2\u00d1"+
		"\u00cf\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d4\3\2\2\2\u00d3\u00d1\3\2"+
		"\2\2\u00d4\u00d5\7$\2\2\u00d5L\3\2\2\2\u00d6\u00d8\7/\2\2\u00d7\u00d6"+
		"\3\2\2\2\u00d7\u00d8\3\2\2\2\u00d8\u00da\3\2\2\2\u00d9\u00db\t\5\2\2\u00da"+
		"\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00da\3\2\2\2\u00dc\u00dd\3\2"+
		"\2\2\u00ddN\3\2\2\2\u00de\u00e5\5\3\2\2\u00df\u00e4\5\3\2\2\u00e0\u00e4"+
		"\5\5\3\2\u00e1\u00e4\5M\'\2\u00e2\u00e4\5=\37\2\u00e3\u00df\3\2\2\2\u00e3"+
		"\u00e0\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e3\u00e2\3\2\2\2\u00e4\u00e7\3\2"+
		"\2\2\u00e5\u00e3\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6P\3\2\2\2\u00e7\u00e5"+
		"\3\2\2\2\u00e8\u00ef\5\5\3\2\u00e9\u00ee\5\3\2\2\u00ea\u00ee\5\5\3\2\u00eb"+
		"\u00ee\5M\'\2\u00ec\u00ee\5=\37\2\u00ed\u00e9\3\2\2\2\u00ed\u00ea\3\2"+
		"\2\2\u00ed\u00eb\3\2\2\2\u00ed\u00ec\3\2\2\2\u00ee\u00f1\3\2\2\2\u00ef"+
		"\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0R\3\2\2\2\u00f1\u00ef\3\2\2\2"+
		"\u00f2\u00f4\t\6\2\2\u00f3\u00f2\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f3"+
		"\3\2\2\2\u00f5\u00f6\3\2\2\2\u00f6\u00f7\3\2\2\2\u00f7\u00f8\b*\2\2\u00f8"+
		"T\3\2\2\2\u00f9\u00fa\7\61\2\2\u00fa\u00fb\7,\2\2\u00fb\u00ff\3\2\2\2"+
		"\u00fc\u00fe\13\2\2\2\u00fd\u00fc\3\2\2\2\u00fe\u0101\3\2\2\2\u00ff\u0100"+
		"\3\2\2\2\u00ff\u00fd\3\2\2\2\u0100\u0102\3\2\2\2\u0101\u00ff\3\2\2\2\u0102"+
		"\u0103\7,\2\2\u0103\u0104\7\61\2\2\u0104\u0105\3\2\2\2\u0105\u0106\b+"+
		"\2\2\u0106V\3\2\2\2\u0107\u0108\7\61\2\2\u0108\u0109\7\61\2\2\u0109\u010d"+
		"\3\2\2\2\u010a\u010c\n\7\2\2\u010b\u010a\3\2\2\2\u010c\u010f\3\2\2\2\u010d"+
		"\u010b\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u0110\3\2\2\2\u010f\u010d\3\2"+
		"\2\2\u0110\u0111\b,\2\2\u0111X\3\2\2\2\r\2\u00d1\u00d7\u00dc\u00e3\u00e5"+
		"\u00ed\u00ef\u00f5\u00ff\u010d\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}