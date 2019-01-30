// Generated from /homes/bc3717/wacc_11/src/main/antlr/BasicLexer.g4 by ANTLR 4.7.2
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BasicLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WHITESPACE=1, COMMENT=2, PLUS=3, MINUS=4, MULT=5, DIV=6, MOD=7, GT=8, 
		GTE=9, LT=10, LTE=11, EQ=12, NOTEQ=13, AND=14, OR=15, NOT=16, NEG=17, 
		LEN=18, ORD=19, CHR=20, OPEN_PARENTHESES=21, CLOSE_PARENTHESES=22, OPEN_SQ_BRACKETS=23, 
		CLOSE_SQ_BRACKETS=24, INT_SIGN=25, CHARACTER=26, BEGIN_S=27, END_S=28, 
		IS_S=29, SKIP_S=30, FREE_S=31, READ_S=32, RETURN_S=33, EXIT_S=34, PRINT_S=35, 
		PRINTLN_S=36, IF_S=37, THEN_S=38, ELSE_S=39, FI_S=40, WHILE_S=41, DO_S=42, 
		DONE_S=43, INT_T=44, BOOL_T=45, CHAR_T=46, STRING_T=47, SEMICOL=48, COMMA=49, 
		ASSIGN=50, FST=51, SND=52, PAIR=53, NEWPAIR=54, CALL=55, INT_LITER=56, 
		BOOL_LITER=57, CHAR_LITER=58, STR_LITER=59, PAIR_LITER=60, IDENT=61;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"WHITESPACE", "COMMENT", "PLUS", "MINUS", "MULT", "DIV", "MOD", "GT", 
			"GTE", "LT", "LTE", "EQ", "NOTEQ", "AND", "OR", "NOT", "NEG", "LEN", 
			"ORD", "CHR", "OPEN_PARENTHESES", "CLOSE_PARENTHESES", "OPEN_SQ_BRACKETS", 
			"CLOSE_SQ_BRACKETS", "DIGIT", "INT_SIGN", "UPPERCASE", "LOWERCASE", "CHARACTER", 
			"BEGIN_S", "END_S", "IS_S", "SKIP_S", "FREE_S", "READ_S", "RETURN_S", 
			"EXIT_S", "PRINT_S", "PRINTLN_S", "IF_S", "THEN_S", "ELSE_S", "FI_S", 
			"WHILE_S", "DO_S", "DONE_S", "INT_T", "BOOL_T", "CHAR_T", "STRING_T", 
			"SEMICOL", "COMMA", "ASSIGN", "FST", "SND", "PAIR", "NEWPAIR", "CALL", 
			"INT_LITER", "BOOL_LITER", "CHAR_LITER", "STR_LITER", "PAIR_LITER", "IDENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, "'+'", null, "'*'", "'/'", "'%'", "'>'", "'>='", "'<'", 
			"'<='", "'=='", "'!='", "'&&'", "'||'", "'!'", null, "'len'", "'ord'", 
			"'chr'", "'('", "')'", "'['", "']'", null, null, "'begin'", "'end'", 
			"'is'", "'skip'", "'free'", "'read'", "'return'", "'exit'", "'print'", 
			"'println'", "'if'", "'then'", "'else'", "'fi'", "'while'", "'do'", "'done'", 
			"'int'", "'bool'", "'char'", "'string'", "';'", "','", "'='", "'fst'", 
			"'snd'", "'pair'", "'newpair'", "'call'", null, null, null, null, "'null'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "WHITESPACE", "COMMENT", "PLUS", "MINUS", "MULT", "DIV", "MOD", 
			"GT", "GTE", "LT", "LTE", "EQ", "NOTEQ", "AND", "OR", "NOT", "NEG", "LEN", 
			"ORD", "CHR", "OPEN_PARENTHESES", "CLOSE_PARENTHESES", "OPEN_SQ_BRACKETS", 
			"CLOSE_SQ_BRACKETS", "INT_SIGN", "CHARACTER", "BEGIN_S", "END_S", "IS_S", 
			"SKIP_S", "FREE_S", "READ_S", "RETURN_S", "EXIT_S", "PRINT_S", "PRINTLN_S", 
			"IF_S", "THEN_S", "ELSE_S", "FI_S", "WHILE_S", "DO_S", "DONE_S", "INT_T", 
			"BOOL_T", "CHAR_T", "STRING_T", "SEMICOL", "COMMA", "ASSIGN", "FST", 
			"SND", "PAIR", "NEWPAIR", "CALL", "INT_LITER", "BOOL_LITER", "CHAR_LITER", 
			"STR_LITER", "PAIR_LITER", "IDENT"
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


	public BasicLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BasicLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2?\u0193\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\3\2\6\2\u0085\n\2\r\2\16\2\u0086\3\2\3\2\3\3"+
		"\3\3\7\3\u008d\n\3\f\3\16\3\u0090\13\3\3\3\3\3\3\3\3\3\3\4\3\4\3\5\3\5"+
		"\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\16\3\16\3\16\3\17\3\17\3\17\3\20\3\20\3\20\3\21\3\21\3\22\3"+
		"\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3"+
		"\26\3\27\3\27\3\30\3\30\3\31\3\31\3\32\3\32\3\33\3\33\3\34\3\34\3\35\3"+
		"\35\3\36\3\36\3\37\3\37\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3!\3\"\3"+
		"\"\3\"\3\"\3\"\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3%\3%\3%\3%\3%\3%\3%\3&\3"+
		"&\3&\3&\3&\3\'\3\'\3\'\3\'\3\'\3\'\3(\3(\3(\3(\3(\3(\3(\3(\3)\3)\3)\3"+
		"*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3,\3,\3,\3-\3-\3-\3-\3-\3-\3.\3.\3.\3/\3"+
		"/\3/\3/\3/\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62"+
		"\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\63\3\63\3\64\3\64\3\65\3\65\3\66"+
		"\3\66\3\67\3\67\3\67\3\67\38\38\38\38\39\39\39\39\39\3:\3:\3:\3:\3:\3"+
		":\3:\3:\3;\3;\3;\3;\3;\3<\5<\u0162\n<\3<\6<\u0165\n<\r<\16<\u0166\3=\3"+
		"=\3=\3=\3=\3=\3=\3=\3=\5=\u0172\n=\3>\3>\3>\3>\3?\3?\7?\u017a\n?\f?\16"+
		"?\u017d\13?\3?\3?\3@\3@\3@\3@\3@\3A\3A\3A\5A\u0189\nA\3A\3A\3A\3A\7A\u018f"+
		"\nA\fA\16A\u0192\13A\2\2B\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25"+
		"\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32"+
		"\63\2\65\33\67\29\2;\34=\35?\36A\37C E!G\"I#K$M%O&Q\'S(U)W*Y+[,]-_.a/"+
		"c\60e\61g\62i\63k\64m\65o\66q\67s8u9w:y;{<}=\177>\u0081?\3\2\6\4\2\13"+
		"\f\"\"\3\2\f\f\4\2--//\b\2\n\f\17\17$$))^^hh\2\u019b\2\3\3\2\2\2\2\5\3"+
		"\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2"+
		"\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3"+
		"\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'"+
		"\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\65"+
		"\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2"+
		"\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2"+
		"\2S\3\2\2\2\2U\3\2\2\2\2W\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_"+
		"\3\2\2\2\2a\3\2\2\2\2c\3\2\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2"+
		"\2\2\2m\3\2\2\2\2o\3\2\2\2\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2"+
		"\2y\3\2\2\2\2{\3\2\2\2\2}\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\3\u0084"+
		"\3\2\2\2\5\u008a\3\2\2\2\7\u0095\3\2\2\2\t\u0097\3\2\2\2\13\u0099\3\2"+
		"\2\2\r\u009b\3\2\2\2\17\u009d\3\2\2\2\21\u009f\3\2\2\2\23\u00a1\3\2\2"+
		"\2\25\u00a4\3\2\2\2\27\u00a6\3\2\2\2\31\u00a9\3\2\2\2\33\u00ac\3\2\2\2"+
		"\35\u00af\3\2\2\2\37\u00b2\3\2\2\2!\u00b5\3\2\2\2#\u00b7\3\2\2\2%\u00b9"+
		"\3\2\2\2\'\u00bd\3\2\2\2)\u00c1\3\2\2\2+\u00c5\3\2\2\2-\u00c7\3\2\2\2"+
		"/\u00c9\3\2\2\2\61\u00cb\3\2\2\2\63\u00cd\3\2\2\2\65\u00cf\3\2\2\2\67"+
		"\u00d1\3\2\2\29\u00d3\3\2\2\2;\u00d5\3\2\2\2=\u00d7\3\2\2\2?\u00dd\3\2"+
		"\2\2A\u00e1\3\2\2\2C\u00e4\3\2\2\2E\u00e9\3\2\2\2G\u00ee\3\2\2\2I\u00f3"+
		"\3\2\2\2K\u00fa\3\2\2\2M\u00ff\3\2\2\2O\u0105\3\2\2\2Q\u010d\3\2\2\2S"+
		"\u0110\3\2\2\2U\u0115\3\2\2\2W\u011a\3\2\2\2Y\u011d\3\2\2\2[\u0123\3\2"+
		"\2\2]\u0126\3\2\2\2_\u012b\3\2\2\2a\u012f\3\2\2\2c\u0134\3\2\2\2e\u0139"+
		"\3\2\2\2g\u0140\3\2\2\2i\u0142\3\2\2\2k\u0144\3\2\2\2m\u0146\3\2\2\2o"+
		"\u014a\3\2\2\2q\u014e\3\2\2\2s\u0153\3\2\2\2u\u015b\3\2\2\2w\u0161\3\2"+
		"\2\2y\u0171\3\2\2\2{\u0173\3\2\2\2}\u0177\3\2\2\2\177\u0180\3\2\2\2\u0081"+
		"\u0188\3\2\2\2\u0083\u0085\t\2\2\2\u0084\u0083\3\2\2\2\u0085\u0086\3\2"+
		"\2\2\u0086\u0084\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\3\2\2\2\u0088"+
		"\u0089\b\2\2\2\u0089\4\3\2\2\2\u008a\u008e\7%\2\2\u008b\u008d\n\3\2\2"+
		"\u008c\u008b\3\2\2\2\u008d\u0090\3\2\2\2\u008e\u008c\3\2\2\2\u008e\u008f"+
		"\3\2\2\2\u008f\u0091\3\2\2\2\u0090\u008e\3\2\2\2\u0091\u0092\7\f\2\2\u0092"+
		"\u0093\3\2\2\2\u0093\u0094\b\3\2\2\u0094\6\3\2\2\2\u0095\u0096\7-\2\2"+
		"\u0096\b\3\2\2\2\u0097\u0098\7/\2\2\u0098\n\3\2\2\2\u0099\u009a\7,\2\2"+
		"\u009a\f\3\2\2\2\u009b\u009c\7\61\2\2\u009c\16\3\2\2\2\u009d\u009e\7\'"+
		"\2\2\u009e\20\3\2\2\2\u009f\u00a0\7@\2\2\u00a0\22\3\2\2\2\u00a1\u00a2"+
		"\7@\2\2\u00a2\u00a3\7?\2\2\u00a3\24\3\2\2\2\u00a4\u00a5\7>\2\2\u00a5\26"+
		"\3\2\2\2\u00a6\u00a7\7>\2\2\u00a7\u00a8\7?\2\2\u00a8\30\3\2\2\2\u00a9"+
		"\u00aa\7?\2\2\u00aa\u00ab\7?\2\2\u00ab\32\3\2\2\2\u00ac\u00ad\7#\2\2\u00ad"+
		"\u00ae\7?\2\2\u00ae\34\3\2\2\2\u00af\u00b0\7(\2\2\u00b0\u00b1\7(\2\2\u00b1"+
		"\36\3\2\2\2\u00b2\u00b3\7~\2\2\u00b3\u00b4\7~\2\2\u00b4 \3\2\2\2\u00b5"+
		"\u00b6\7#\2\2\u00b6\"\3\2\2\2\u00b7\u00b8\7/\2\2\u00b8$\3\2\2\2\u00b9"+
		"\u00ba\7n\2\2\u00ba\u00bb\7g\2\2\u00bb\u00bc\7p\2\2\u00bc&\3\2\2\2\u00bd"+
		"\u00be\7q\2\2\u00be\u00bf\7t\2\2\u00bf\u00c0\7f\2\2\u00c0(\3\2\2\2\u00c1"+
		"\u00c2\7e\2\2\u00c2\u00c3\7j\2\2\u00c3\u00c4\7t\2\2\u00c4*\3\2\2\2\u00c5"+
		"\u00c6\7*\2\2\u00c6,\3\2\2\2\u00c7\u00c8\7+\2\2\u00c8.\3\2\2\2\u00c9\u00ca"+
		"\7]\2\2\u00ca\60\3\2\2\2\u00cb\u00cc\7_\2\2\u00cc\62\3\2\2\2\u00cd\u00ce"+
		"\4\62;\2\u00ce\64\3\2\2\2\u00cf\u00d0\t\4\2\2\u00d0\66\3\2\2\2\u00d1\u00d2"+
		"\4C\\\2\u00d28\3\2\2\2\u00d3\u00d4\4c|\2\u00d4:\3\2\2\2\u00d5\u00d6\n"+
		"\5\2\2\u00d6<\3\2\2\2\u00d7\u00d8\7d\2\2\u00d8\u00d9\7g\2\2\u00d9\u00da"+
		"\7i\2\2\u00da\u00db\7k\2\2\u00db\u00dc\7p\2\2\u00dc>\3\2\2\2\u00dd\u00de"+
		"\7g\2\2\u00de\u00df\7p\2\2\u00df\u00e0\7f\2\2\u00e0@\3\2\2\2\u00e1\u00e2"+
		"\7k\2\2\u00e2\u00e3\7u\2\2\u00e3B\3\2\2\2\u00e4\u00e5\7u\2\2\u00e5\u00e6"+
		"\7m\2\2\u00e6\u00e7\7k\2\2\u00e7\u00e8\7r\2\2\u00e8D\3\2\2\2\u00e9\u00ea"+
		"\7h\2\2\u00ea\u00eb\7t\2\2\u00eb\u00ec\7g\2\2\u00ec\u00ed\7g\2\2\u00ed"+
		"F\3\2\2\2\u00ee\u00ef\7t\2\2\u00ef\u00f0\7g\2\2\u00f0\u00f1\7c\2\2\u00f1"+
		"\u00f2\7f\2\2\u00f2H\3\2\2\2\u00f3\u00f4\7t\2\2\u00f4\u00f5\7g\2\2\u00f5"+
		"\u00f6\7v\2\2\u00f6\u00f7\7w\2\2\u00f7\u00f8\7t\2\2\u00f8\u00f9\7p\2\2"+
		"\u00f9J\3\2\2\2\u00fa\u00fb\7g\2\2\u00fb\u00fc\7z\2\2\u00fc\u00fd\7k\2"+
		"\2\u00fd\u00fe\7v\2\2\u00feL\3\2\2\2\u00ff\u0100\7r\2\2\u0100\u0101\7"+
		"t\2\2\u0101\u0102\7k\2\2\u0102\u0103\7p\2\2\u0103\u0104\7v\2\2\u0104N"+
		"\3\2\2\2\u0105\u0106\7r\2\2\u0106\u0107\7t\2\2\u0107\u0108\7k\2\2\u0108"+
		"\u0109\7p\2\2\u0109\u010a\7v\2\2\u010a\u010b\7n\2\2\u010b\u010c\7p\2\2"+
		"\u010cP\3\2\2\2\u010d\u010e\7k\2\2\u010e\u010f\7h\2\2\u010fR\3\2\2\2\u0110"+
		"\u0111\7v\2\2\u0111\u0112\7j\2\2\u0112\u0113\7g\2\2\u0113\u0114\7p\2\2"+
		"\u0114T\3\2\2\2\u0115\u0116\7g\2\2\u0116\u0117\7n\2\2\u0117\u0118\7u\2"+
		"\2\u0118\u0119\7g\2\2\u0119V\3\2\2\2\u011a\u011b\7h\2\2\u011b\u011c\7"+
		"k\2\2\u011cX\3\2\2\2\u011d\u011e\7y\2\2\u011e\u011f\7j\2\2\u011f\u0120"+
		"\7k\2\2\u0120\u0121\7n\2\2\u0121\u0122\7g\2\2\u0122Z\3\2\2\2\u0123\u0124"+
		"\7f\2\2\u0124\u0125\7q\2\2\u0125\\\3\2\2\2\u0126\u0127\7f\2\2\u0127\u0128"+
		"\7q\2\2\u0128\u0129\7p\2\2\u0129\u012a\7g\2\2\u012a^\3\2\2\2\u012b\u012c"+
		"\7k\2\2\u012c\u012d\7p\2\2\u012d\u012e\7v\2\2\u012e`\3\2\2\2\u012f\u0130"+
		"\7d\2\2\u0130\u0131\7q\2\2\u0131\u0132\7q\2\2\u0132\u0133\7n\2\2\u0133"+
		"b\3\2\2\2\u0134\u0135\7e\2\2\u0135\u0136\7j\2\2\u0136\u0137\7c\2\2\u0137"+
		"\u0138\7t\2\2\u0138d\3\2\2\2\u0139\u013a\7u\2\2\u013a\u013b\7v\2\2\u013b"+
		"\u013c\7t\2\2\u013c\u013d\7k\2\2\u013d\u013e\7p\2\2\u013e\u013f\7i\2\2"+
		"\u013ff\3\2\2\2\u0140\u0141\7=\2\2\u0141h\3\2\2\2\u0142\u0143\7.\2\2\u0143"+
		"j\3\2\2\2\u0144\u0145\7?\2\2\u0145l\3\2\2\2\u0146\u0147\7h\2\2\u0147\u0148"+
		"\7u\2\2\u0148\u0149\7v\2\2\u0149n\3\2\2\2\u014a\u014b\7u\2\2\u014b\u014c"+
		"\7p\2\2\u014c\u014d\7f\2\2\u014dp\3\2\2\2\u014e\u014f\7r\2\2\u014f\u0150"+
		"\7c\2\2\u0150\u0151\7k\2\2\u0151\u0152\7t\2\2\u0152r\3\2\2\2\u0153\u0154"+
		"\7p\2\2\u0154\u0155\7g\2\2\u0155\u0156\7y\2\2\u0156\u0157\7r\2\2\u0157"+
		"\u0158\7c\2\2\u0158\u0159\7k\2\2\u0159\u015a\7t\2\2\u015at\3\2\2\2\u015b"+
		"\u015c\7e\2\2\u015c\u015d\7c\2\2\u015d\u015e\7n\2\2\u015e\u015f\7n\2\2"+
		"\u015fv\3\2\2\2\u0160\u0162\5\65\33\2\u0161\u0160\3\2\2\2\u0161\u0162"+
		"\3\2\2\2\u0162\u0164\3\2\2\2\u0163\u0165\5\63\32\2\u0164\u0163\3\2\2\2"+
		"\u0165\u0166\3\2\2\2\u0166\u0164\3\2\2\2\u0166\u0167\3\2\2\2\u0167x\3"+
		"\2\2\2\u0168\u0169\7v\2\2\u0169\u016a\7t\2\2\u016a\u016b\7w\2\2\u016b"+
		"\u0172\7g\2\2\u016c\u016d\7h\2\2\u016d\u016e\7c\2\2\u016e\u016f\7n\2\2"+
		"\u016f\u0170\7u\2\2\u0170\u0172\7g\2\2\u0171\u0168\3\2\2\2\u0171\u016c"+
		"\3\2\2\2\u0172z\3\2\2\2\u0173\u0174\7)\2\2\u0174\u0175\5;\36\2\u0175\u0176"+
		"\7)\2\2\u0176|\3\2\2\2\u0177\u017b\7$\2\2\u0178\u017a\5;\36\2\u0179\u0178"+
		"\3\2\2\2\u017a\u017d\3\2\2\2\u017b\u0179\3\2\2\2\u017b\u017c\3\2\2\2\u017c"+
		"\u017e\3\2\2\2\u017d\u017b\3\2\2\2\u017e\u017f\7$\2\2\u017f~\3\2\2\2\u0180"+
		"\u0181\7p\2\2\u0181\u0182\7w\2\2\u0182\u0183\7n\2\2\u0183\u0184\7n\2\2"+
		"\u0184\u0080\3\2\2\2\u0185\u0189\7a\2\2\u0186\u0189\5\67\34\2\u0187\u0189"+
		"\59\35\2\u0188\u0185\3\2\2\2\u0188\u0186\3\2\2\2\u0188\u0187\3\2\2\2\u0189"+
		"\u0190\3\2\2\2\u018a\u018f\7a\2\2\u018b\u018f\5\67\34\2\u018c\u018f\5"+
		"9\35\2\u018d\u018f\5\63\32\2\u018e\u018a\3\2\2\2\u018e\u018b\3\2\2\2\u018e"+
		"\u018c\3\2\2\2\u018e\u018d\3\2\2\2\u018f\u0192\3\2\2\2\u0190\u018e\3\2"+
		"\2\2\u0190\u0191\3\2\2\2\u0191\u0082\3\2\2\2\u0192\u0190\3\2\2\2\f\2\u0086"+
		"\u008e\u0161\u0166\u0171\u017b\u0188\u018e\u0190\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}