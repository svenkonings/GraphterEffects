// Generated from C:/Users/Sven/Documents/GitHub/GraphterEffects/src/main/java/graafvis\Graafvis.g4 by ANTLR 4.6
package graafvis;
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
		EQ=8, NQ=9, GT=10, LT=11, GE=12, LE=13, COMMA=14, OR=15, AND=16, NOT=17, 
		PLUS=18, MINUS=19, MULT=20, DIV=21, POW=22, MOD=23, UNDERSCORE=24, TRUE=25, 
		FALSE=26, IMPORT_TOKEN=27, NODE_LABEL_TOKEN=28, EDGE_LABEL_TOKEN=29, RENAME_TOKEN=30, 
		STRING=31, NUMBER=32, ID=33, HID=34, WS=35, BLOCKCOMMENT=36, LINECOMMENT=37;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"LETTER_LO", "LETTER_HI", "ARROW", "COLON", "EOL", "PAR_OPEN", "PAR_CLOSE", 
		"BRACE_OPEN", "BRACE_CLOSE", "EQ", "NQ", "GT", "LT", "GE", "LE", "COMMA", 
		"OR", "AND", "NOT", "PLUS", "MINUS", "MULT", "DIV", "POW", "MOD", "UNDERSCORE", 
		"TRUE", "FALSE", "IMPORT_TOKEN", "NODE_LABEL_TOKEN", "EDGE_LABEL_TOKEN", 
		"RENAME_TOKEN", "STRING", "NUMBER", "ID", "HID", "WS", "BLOCKCOMMENT", 
		"LINECOMMENT"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\'\u00ff\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\3\2\3\2\3\3\3\3\3\4"+
		"\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13"+
		"\3\f\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21"+
		"\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3\26"+
		"\3\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\34"+
		"\3\34\3\34\3\35\3\35\3\35\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\36\3\36"+
		"\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37\3\37"+
		"\3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3 \3!\3!\3!\3\"\3\"\7\"\u00c0\n\"\f"+
		"\"\16\"\u00c3\13\"\3\"\3\"\3#\6#\u00c8\n#\r#\16#\u00c9\3$\3$\3$\3$\3$"+
		"\7$\u00d1\n$\f$\16$\u00d4\13$\3%\3%\3%\3%\3%\7%\u00db\n%\f%\16%\u00de"+
		"\13%\3&\6&\u00e1\n&\r&\16&\u00e2\3&\3&\3\'\3\'\3\'\3\'\7\'\u00eb\n\'\f"+
		"\'\16\'\u00ee\13\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\7(\u00f9\n(\f(\16("+
		"\u00fc\13(\3(\3(\3\u00ec\2)\3\2\5\2\7\3\t\4\13\5\r\6\17\7\21\b\23\t\25"+
		"\n\27\13\31\f\33\r\35\16\37\17!\20#\21%\22\'\23)\24+\25-\26/\27\61\30"+
		"\63\31\65\32\67\339\34;\35=\36?\37A C!E\"G#I$K%M&O\'\3\2\b\3\2c|\3\2C"+
		"\\\3\2$$\3\2\62;\5\2\13\f\17\17\"\"\4\2\f\f\17\17\u0109\2\7\3\2\2\2\2"+
		"\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2"+
		"\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2"+
		"\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2"+
		"\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2"+
		"\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2"+
		"\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O"+
		"\3\2\2\2\3Q\3\2\2\2\5S\3\2\2\2\7U\3\2\2\2\tX\3\2\2\2\13Z\3\2\2\2\r\\\3"+
		"\2\2\2\17^\3\2\2\2\21`\3\2\2\2\23b\3\2\2\2\25d\3\2\2\2\27g\3\2\2\2\31"+
		"j\3\2\2\2\33l\3\2\2\2\35n\3\2\2\2\37q\3\2\2\2!t\3\2\2\2#v\3\2\2\2%y\3"+
		"\2\2\2\'}\3\2\2\2)\u0081\3\2\2\2+\u0083\3\2\2\2-\u0085\3\2\2\2/\u0087"+
		"\3\2\2\2\61\u0089\3\2\2\2\63\u008b\3\2\2\2\65\u008d\3\2\2\2\67\u008f\3"+
		"\2\2\29\u0094\3\2\2\2;\u009a\3\2\2\2=\u00a2\3\2\2\2?\u00ae\3\2\2\2A\u00ba"+
		"\3\2\2\2C\u00bd\3\2\2\2E\u00c7\3\2\2\2G\u00cb\3\2\2\2I\u00d5\3\2\2\2K"+
		"\u00e0\3\2\2\2M\u00e6\3\2\2\2O\u00f4\3\2\2\2QR\t\2\2\2R\4\3\2\2\2ST\t"+
		"\3\2\2T\6\3\2\2\2UV\7/\2\2VW\7@\2\2W\b\3\2\2\2XY\7<\2\2Y\n\3\2\2\2Z[\7"+
		"\60\2\2[\f\3\2\2\2\\]\7*\2\2]\16\3\2\2\2^_\7+\2\2_\20\3\2\2\2`a\7}\2\2"+
		"a\22\3\2\2\2bc\7\177\2\2c\24\3\2\2\2de\7?\2\2ef\7?\2\2f\26\3\2\2\2gh\7"+
		"#\2\2hi\7?\2\2i\30\3\2\2\2jk\7@\2\2k\32\3\2\2\2lm\7>\2\2m\34\3\2\2\2n"+
		"o\7@\2\2op\7?\2\2p\36\3\2\2\2qr\7>\2\2rs\7?\2\2s \3\2\2\2tu\7.\2\2u\""+
		"\3\2\2\2vw\7q\2\2wx\7t\2\2x$\3\2\2\2yz\7c\2\2z{\7p\2\2{|\7f\2\2|&\3\2"+
		"\2\2}~\7p\2\2~\177\7q\2\2\177\u0080\7v\2\2\u0080(\3\2\2\2\u0081\u0082"+
		"\7-\2\2\u0082*\3\2\2\2\u0083\u0084\7/\2\2\u0084,\3\2\2\2\u0085\u0086\7"+
		",\2\2\u0086.\3\2\2\2\u0087\u0088\7\61\2\2\u0088\60\3\2\2\2\u0089\u008a"+
		"\7`\2\2\u008a\62\3\2\2\2\u008b\u008c\7\'\2\2\u008c\64\3\2\2\2\u008d\u008e"+
		"\7a\2\2\u008e\66\3\2\2\2\u008f\u0090\7v\2\2\u0090\u0091\7t\2\2\u0091\u0092"+
		"\7w\2\2\u0092\u0093\7g\2\2\u00938\3\2\2\2\u0094\u0095\7h\2\2\u0095\u0096"+
		"\7c\2\2\u0096\u0097\7n\2\2\u0097\u0098\7u\2\2\u0098\u0099\7g\2\2\u0099"+
		":\3\2\2\2\u009a\u009b\7e\2\2\u009b\u009c\7q\2\2\u009c\u009d\7p\2\2\u009d"+
		"\u009e\7u\2\2\u009e\u009f\7w\2\2\u009f\u00a0\7n\2\2\u00a0\u00a1\7v\2\2"+
		"\u00a1<\3\2\2\2\u00a2\u00a3\7p\2\2\u00a3\u00a4\7q\2\2\u00a4\u00a5\7f\2"+
		"\2\u00a5\u00a6\7g\2\2\u00a6\u00a7\7\"\2\2\u00a7\u00a8\7n\2\2\u00a8\u00a9"+
		"\7c\2\2\u00a9\u00aa\7d\2\2\u00aa\u00ab\7g\2\2\u00ab\u00ac\7n\2\2\u00ac"+
		"\u00ad\7u\2\2\u00ad>\3\2\2\2\u00ae\u00af\7g\2\2\u00af\u00b0\7f\2\2\u00b0"+
		"\u00b1\7i\2\2\u00b1\u00b2\7g\2\2\u00b2\u00b3\7\"\2\2\u00b3\u00b4\7n\2"+
		"\2\u00b4\u00b5\7c\2\2\u00b5\u00b6\7d\2\2\u00b6\u00b7\7g\2\2\u00b7\u00b8"+
		"\7n\2\2\u00b8\u00b9\7u\2\2\u00b9@\3\2\2\2\u00ba\u00bb\7c\2\2\u00bb\u00bc"+
		"\7u\2\2\u00bcB\3\2\2\2\u00bd\u00c1\7$\2\2\u00be\u00c0\n\4\2\2\u00bf\u00be"+
		"\3\2\2\2\u00c0\u00c3\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2"+
		"\u00c4\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c4\u00c5\7$\2\2\u00c5D\3\2\2\2\u00c6"+
		"\u00c8\t\5\2\2\u00c7\u00c6\3\2\2\2\u00c8\u00c9\3\2\2\2\u00c9\u00c7\3\2"+
		"\2\2\u00c9\u00ca\3\2\2\2\u00caF\3\2\2\2\u00cb\u00d2\5\3\2\2\u00cc\u00d1"+
		"\5\3\2\2\u00cd\u00d1\5\5\3\2\u00ce\u00d1\5E#\2\u00cf\u00d1\5\65\33\2\u00d0"+
		"\u00cc\3\2\2\2\u00d0\u00cd\3\2\2\2\u00d0\u00ce\3\2\2\2\u00d0\u00cf\3\2"+
		"\2\2\u00d1\u00d4\3\2\2\2\u00d2\u00d0\3\2\2\2\u00d2\u00d3\3\2\2\2\u00d3"+
		"H\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d5\u00dc\5\5\3\2\u00d6\u00db\5\3\2\2"+
		"\u00d7\u00db\5\5\3\2\u00d8\u00db\5E#\2\u00d9\u00db\5\65\33\2\u00da\u00d6"+
		"\3\2\2\2\u00da\u00d7\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00d9\3\2\2\2\u00db"+
		"\u00de\3\2\2\2\u00dc\u00da\3\2\2\2\u00dc\u00dd\3\2\2\2\u00ddJ\3\2\2\2"+
		"\u00de\u00dc\3\2\2\2\u00df\u00e1\t\6\2\2\u00e0\u00df\3\2\2\2\u00e1\u00e2"+
		"\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4"+
		"\u00e5\b&\2\2\u00e5L\3\2\2\2\u00e6\u00e7\7\61\2\2\u00e7\u00e8\7,\2\2\u00e8"+
		"\u00ec\3\2\2\2\u00e9\u00eb\13\2\2\2\u00ea\u00e9\3\2\2\2\u00eb\u00ee\3"+
		"\2\2\2\u00ec\u00ed\3\2\2\2\u00ec\u00ea\3\2\2\2\u00ed\u00ef\3\2\2\2\u00ee"+
		"\u00ec\3\2\2\2\u00ef\u00f0\7,\2\2\u00f0\u00f1\7\61\2\2\u00f1\u00f2\3\2"+
		"\2\2\u00f2\u00f3\b\'\2\2\u00f3N\3\2\2\2\u00f4\u00f5\7\61\2\2\u00f5\u00f6"+
		"\7\61\2\2\u00f6\u00fa\3\2\2\2\u00f7\u00f9\n\7\2\2\u00f8\u00f7\3\2\2\2"+
		"\u00f9\u00fc\3\2\2\2\u00fa\u00f8\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb\u00fd"+
		"\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fd\u00fe\b(\2\2\u00feP\3\2\2\2\f\2\u00c1"+
		"\u00c9\u00d0\u00d2\u00da\u00dc\u00e2\u00ec\u00fa\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}