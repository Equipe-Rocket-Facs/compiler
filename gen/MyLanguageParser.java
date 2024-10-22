// Generated from /home/diego/repos/java/compiler/src/main/antlr4/com/equiperocket/compiler/MyLanguage.g4 by ANTLR 4.13.2
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class MyLanguageParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, T__23=24, 
		T__24=25, T__25=26, T__26=27, T__27=28, T__28=29, T__29=30, T__30=31, 
		T__31=32, ID=33, NUM=34, TEXT=35, BOOL=36, WS=37, COMMENT=38, COMMENT_MULTILINE=39;
	public static final int
		RULE_prog = 0, RULE_decls = 1, RULE_decl = 2, RULE_type = 3, RULE_declItemList = 4, 
		RULE_declItem = 5, RULE_commands = 6, RULE_command = 7, RULE_readInput = 8, 
		RULE_writeOutput = 9, RULE_attribution = 10, RULE_ifStmt = 11, RULE_whileStmt = 12, 
		RULE_forStmt = 13, RULE_block = 14, RULE_condition = 15, RULE_boolExpr = 16, 
		RULE_expr = 17, RULE_relOp = 18;
	private static String[] makeRuleNames() {
		return new String[] {
			"prog", "decls", "decl", "type", "declItemList", "declItem", "commands", 
			"command", "readInput", "writeOutput", "attribution", "ifStmt", "whileStmt", 
			"forStmt", "block", "condition", "boolExpr", "expr", "relOp"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'programa'", "'fimprog'", "'numero'", "'texto'", "'bool'", "','", 
			"'leia'", "'('", "')'", "'escreva'", "'+'", "'='", "'if'", "'if else'", 
			"'else'", "'while'", "'for'", "';'", "'{'", "'}'", "'NAO'", "'E'", "'OU'", 
			"'*'", "'/'", "'-'", "'<'", "'>'", "'<='", "'>='", "'=='", "'!='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, "ID", "NUM", "TEXT", 
			"BOOL", "WS", "COMMENT", "COMMENT_MULTILINE"
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
	public String getGrammarFileName() { return "MyLanguage.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MyLanguageParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgContext extends ParserRuleContext {
		public DeclsContext decls() {
			return getRuleContext(DeclsContext.class,0);
		}
		public CommandsContext commands() {
			return getRuleContext(CommandsContext.class,0);
		}
		public ProgContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prog; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterProg(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitProg(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitProg(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ProgContext prog() throws RecognitionException {
		ProgContext _localctx = new ProgContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_prog);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38);
			match(T__0);
			setState(39);
			decls();
			setState(40);
			commands();
			setState(41);
			match(T__1);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclsContext extends ParserRuleContext {
		public List<DeclContext> decl() {
			return getRuleContexts(DeclContext.class);
		}
		public DeclContext decl(int i) {
			return getRuleContext(DeclContext.class,i);
		}
		public DeclsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decls; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterDecls(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitDecls(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitDecls(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclsContext decls() throws RecognitionException {
		DeclsContext _localctx = new DeclsContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_decls);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 56L) != 0)) {
				{
				{
				setState(43);
				decl();
				}
				}
				setState(48);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public DeclItemListContext declItemList() {
			return getRuleContext(DeclItemListContext.class,0);
		}
		public DeclContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_decl; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterDecl(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitDecl(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitDecl(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclContext decl() throws RecognitionException {
		DeclContext _localctx = new DeclContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_decl);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			type();
			setState(50);
			declItemList();
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

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 56L) != 0)) ) {
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclItemListContext extends ParserRuleContext {
		public List<DeclItemContext> declItem() {
			return getRuleContexts(DeclItemContext.class);
		}
		public DeclItemContext declItem(int i) {
			return getRuleContext(DeclItemContext.class,i);
		}
		public DeclItemListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declItemList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterDeclItemList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitDeclItemList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitDeclItemList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclItemListContext declItemList() throws RecognitionException {
		DeclItemListContext _localctx = new DeclItemListContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_declItemList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			declItem();
			setState(59);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__5) {
				{
				{
				setState(55);
				match(T__5);
				setState(56);
				declItem();
				}
				}
				setState(61);
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

	@SuppressWarnings("CheckReturnValue")
	public static class DeclItemContext extends ParserRuleContext {
		public AttributionContext attribution() {
			return getRuleContext(AttributionContext.class,0);
		}
		public TerminalNode ID() { return getToken(MyLanguageParser.ID, 0); }
		public DeclItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterDeclItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitDeclItem(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitDeclItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclItemContext declItem() throws RecognitionException {
		DeclItemContext _localctx = new DeclItemContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_declItem);
		try {
			setState(64);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(62);
				attribution();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(63);
				match(ID);
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

	@SuppressWarnings("CheckReturnValue")
	public static class CommandsContext extends ParserRuleContext {
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public CommandsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commands; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterCommands(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitCommands(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitCommands(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandsContext commands() throws RecognitionException {
		CommandsContext _localctx = new CommandsContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_commands);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 8590140544L) != 0)) {
				{
				{
				setState(66);
				command();
				}
				}
				setState(71);
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

	@SuppressWarnings("CheckReturnValue")
	public static class CommandContext extends ParserRuleContext {
		public ReadInputContext readInput() {
			return getRuleContext(ReadInputContext.class,0);
		}
		public WriteOutputContext writeOutput() {
			return getRuleContext(WriteOutputContext.class,0);
		}
		public AttributionContext attribution() {
			return getRuleContext(AttributionContext.class,0);
		}
		public IfStmtContext ifStmt() {
			return getRuleContext(IfStmtContext.class,0);
		}
		public WhileStmtContext whileStmt() {
			return getRuleContext(WhileStmtContext.class,0);
		}
		public ForStmtContext forStmt() {
			return getRuleContext(ForStmtContext.class,0);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_command);
		try {
			setState(78);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__6:
				enterOuterAlt(_localctx, 1);
				{
				setState(72);
				readInput();
				}
				break;
			case T__9:
				enterOuterAlt(_localctx, 2);
				{
				setState(73);
				writeOutput();
				}
				break;
			case ID:
				enterOuterAlt(_localctx, 3);
				{
				setState(74);
				attribution();
				}
				break;
			case T__12:
				enterOuterAlt(_localctx, 4);
				{
				setState(75);
				ifStmt();
				}
				break;
			case T__15:
				enterOuterAlt(_localctx, 5);
				{
				setState(76);
				whileStmt();
				}
				break;
			case T__16:
				enterOuterAlt(_localctx, 6);
				{
				setState(77);
				forStmt();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ReadInputContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MyLanguageParser.ID, 0); }
		public ReadInputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_readInput; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterReadInput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitReadInput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitReadInput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReadInputContext readInput() throws RecognitionException {
		ReadInputContext _localctx = new ReadInputContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_readInput);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(T__6);
			setState(81);
			match(T__7);
			setState(82);
			match(ID);
			setState(83);
			match(T__8);
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

	@SuppressWarnings("CheckReturnValue")
	public static class WriteOutputContext extends ParserRuleContext {
		public List<TerminalNode> TEXT() { return getTokens(MyLanguageParser.TEXT); }
		public TerminalNode TEXT(int i) {
			return getToken(MyLanguageParser.TEXT, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> BOOL() { return getTokens(MyLanguageParser.BOOL); }
		public TerminalNode BOOL(int i) {
			return getToken(MyLanguageParser.BOOL, i);
		}
		public WriteOutputContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_writeOutput; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterWriteOutput(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitWriteOutput(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitWriteOutput(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WriteOutputContext writeOutput() throws RecognitionException {
		WriteOutputContext _localctx = new WriteOutputContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_writeOutput);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(85);
			match(T__9);
			setState(86);
			match(T__7);
			setState(90);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TEXT:
				{
				setState(87);
				match(TEXT);
				}
				break;
			case T__7:
			case ID:
			case NUM:
				{
				setState(88);
				expr(0);
				}
				break;
			case BOOL:
				{
				setState(89);
				match(BOOL);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(100);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__10) {
				{
				{
				setState(92);
				match(T__10);
				setState(96);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case TEXT:
					{
					setState(93);
					match(TEXT);
					}
					break;
				case T__7:
				case ID:
				case NUM:
					{
					setState(94);
					expr(0);
					}
					break;
				case BOOL:
					{
					setState(95);
					match(BOOL);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				}
				setState(102);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(103);
			match(T__8);
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

	@SuppressWarnings("CheckReturnValue")
	public static class AttributionContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(MyLanguageParser.ID, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public TerminalNode TEXT() { return getToken(MyLanguageParser.TEXT, 0); }
		public AttributionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribution; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterAttribution(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitAttribution(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitAttribution(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributionContext attribution() throws RecognitionException {
		AttributionContext _localctx = new AttributionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_attribution);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(105);
			match(ID);
			setState(106);
			match(T__11);
			setState(110);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(107);
				expr(0);
				}
				break;
			case 2:
				{
				setState(108);
				boolExpr(0);
				}
				break;
			case 3:
				{
				setState(109);
				match(TEXT);
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

	@SuppressWarnings("CheckReturnValue")
	public static class IfStmtContext extends ParserRuleContext {
		public List<ConditionContext> condition() {
			return getRuleContexts(ConditionContext.class);
		}
		public ConditionContext condition(int i) {
			return getRuleContext(ConditionContext.class,i);
		}
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public IfStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterIfStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitIfStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitIfStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IfStmtContext ifStmt() throws RecognitionException {
		IfStmtContext _localctx = new IfStmtContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_ifStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(112);
			match(T__12);
			setState(113);
			match(T__7);
			setState(114);
			condition();
			setState(115);
			match(T__8);
			setState(116);
			block();
			setState(130);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__13 || _la==T__14) {
				{
				setState(125);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__13) {
					{
					{
					setState(117);
					match(T__13);
					setState(118);
					match(T__7);
					setState(119);
					condition();
					setState(120);
					match(T__8);
					setState(121);
					block();
					}
					}
					setState(127);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(128);
				match(T__14);
				setState(129);
				block();
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

	@SuppressWarnings("CheckReturnValue")
	public static class WhileStmtContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public WhileStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whileStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterWhileStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitWhileStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitWhileStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhileStmtContext whileStmt() throws RecognitionException {
		WhileStmtContext _localctx = new WhileStmtContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_whileStmt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			match(T__15);
			setState(133);
			match(T__7);
			setState(134);
			condition();
			setState(135);
			match(T__8);
			setState(136);
			block();
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

	@SuppressWarnings("CheckReturnValue")
	public static class ForStmtContext extends ParserRuleContext {
		public ConditionContext condition() {
			return getRuleContext(ConditionContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public DeclContext decl() {
			return getRuleContext(DeclContext.class,0);
		}
		public List<AttributionContext> attribution() {
			return getRuleContexts(AttributionContext.class);
		}
		public AttributionContext attribution(int i) {
			return getRuleContext(AttributionContext.class,i);
		}
		public ForStmtContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forStmt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterForStmt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitForStmt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitForStmt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ForStmtContext forStmt() throws RecognitionException {
		ForStmtContext _localctx = new ForStmtContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_forStmt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(138);
			match(T__16);
			setState(139);
			match(T__7);
			setState(142);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
			case T__3:
			case T__4:
				{
				setState(140);
				decl();
				}
				break;
			case ID:
				{
				setState(141);
				attribution();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(144);
			match(T__17);
			setState(145);
			condition();
			setState(148);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__17) {
				{
				setState(146);
				match(T__17);
				setState(147);
				attribution();
				}
			}

			setState(150);
			match(T__8);
			setState(151);
			block();
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

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(153);
			match(T__18);
			setState(155); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(154);
				command();
				}
				}
				setState(157); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & 8590140544L) != 0) );
			setState(159);
			match(T__19);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ConditionContext extends ParserRuleContext {
		public BoolExprContext boolExpr() {
			return getRuleContext(BoolExprContext.class,0);
		}
		public ConditionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_condition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterCondition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitCondition(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitCondition(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ConditionContext condition() throws RecognitionException {
		ConditionContext _localctx = new ConditionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_condition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(161);
			boolExpr(0);
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

	@SuppressWarnings("CheckReturnValue")
	public static class BoolExprContext extends ParserRuleContext {
		public List<BoolExprContext> boolExpr() {
			return getRuleContexts(BoolExprContext.class);
		}
		public BoolExprContext boolExpr(int i) {
			return getRuleContext(BoolExprContext.class,i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public RelOpContext relOp() {
			return getRuleContext(RelOpContext.class,0);
		}
		public TerminalNode BOOL() { return getToken(MyLanguageParser.BOOL, 0); }
		public BoolExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boolExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterBoolExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitBoolExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitBoolExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolExprContext boolExpr() throws RecognitionException {
		return boolExpr(0);
	}

	private BoolExprContext boolExpr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		BoolExprContext _localctx = new BoolExprContext(_ctx, _parentState);
		BoolExprContext _prevctx = _localctx;
		int _startState = 32;
		enterRecursionRule(_localctx, 32, RULE_boolExpr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(164);
				match(T__20);
				setState(165);
				boolExpr(6);
				}
				break;
			case 2:
				{
				setState(166);
				match(T__7);
				setState(167);
				boolExpr(0);
				setState(168);
				match(T__8);
				}
				break;
			case 3:
				{
				setState(170);
				expr(0);
				setState(171);
				relOp();
				setState(172);
				expr(0);
				}
				break;
			case 4:
				{
				setState(174);
				match(BOOL);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(185);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(183);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
					case 1:
						{
						_localctx = new BoolExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolExpr);
						setState(177);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(178);
						match(T__21);
						setState(179);
						boolExpr(6);
						}
						break;
					case 2:
						{
						_localctx = new BoolExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_boolExpr);
						setState(180);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(181);
						match(T__22);
						setState(182);
						boolExpr(5);
						}
						break;
					}
					} 
				}
				setState(187);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,16,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public TerminalNode NUM() { return getToken(MyLanguageParser.NUM, 0); }
		public TerminalNode ID() { return getToken(MyLanguageParser.ID, 0); }
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 34;
		enterRecursionRule(_localctx, 34, RULE_expr, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__7:
				{
				setState(189);
				match(T__7);
				setState(190);
				expr(0);
				setState(191);
				match(T__8);
				}
				break;
			case NUM:
				{
				setState(193);
				match(NUM);
				}
				break;
			case ID:
				{
				setState(194);
				match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(205);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(203);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(197);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(198);
						_la = _input.LA(1);
						if ( !(_la==T__23 || _la==T__24) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(199);
						expr(5);
						}
						break;
					case 2:
						{
						_localctx = new ExprContext(_parentctx, _parentState);
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(200);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(201);
						_la = _input.LA(1);
						if ( !(_la==T__10 || _la==T__25) ) {
						_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(202);
						expr(4);
						}
						break;
					}
					} 
				}
				setState(207);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
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

	@SuppressWarnings("CheckReturnValue")
	public static class RelOpContext extends ParserRuleContext {
		public RelOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_relOp; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).enterRelOp(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof MyLanguageListener ) ((MyLanguageListener)listener).exitRelOp(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof MyLanguageVisitor ) return ((MyLanguageVisitor<? extends T>)visitor).visitRelOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RelOpContext relOp() throws RecognitionException {
		RelOpContext _localctx = new RelOpContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_relOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 8455716864L) != 0)) ) {
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
		case 16:
			return boolExpr_sempred((BoolExprContext)_localctx, predIndex);
		case 17:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean boolExpr_sempred(BoolExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 5);
		case 1:
			return precpred(_ctx, 4);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 2:
			return precpred(_ctx, 4);
		case 3:
			return precpred(_ctx, 3);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\'\u00d3\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0005\u0001-\b\u0001\n\u0001\f\u00010\t\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0005\u0004:\b\u0004\n\u0004\f\u0004=\t\u0004\u0001\u0005\u0001\u0005"+
		"\u0003\u0005A\b\u0005\u0001\u0006\u0005\u0006D\b\u0006\n\u0006\f\u0006"+
		"G\t\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0003\u0007O\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003\t[\b\t\u0001\t\u0001"+
		"\t\u0001\t\u0001\t\u0003\ta\b\t\u0005\tc\b\t\n\t\f\tf\t\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0003\no\b\n\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b|\b\u000b\n\u000b"+
		"\f\u000b\u007f\t\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0083\b\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0003\r\u008f\b\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u0095"+
		"\b\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0004\u000e\u009c"+
		"\b\u000e\u000b\u000e\f\u000e\u009d\u0001\u000e\u0001\u000e\u0001\u000f"+
		"\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0003\u0010\u00b0\b\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u00b8\b\u0010\n\u0010"+
		"\f\u0010\u00bb\t\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0003\u0011\u00c4\b\u0011\u0001\u0011"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0005\u0011"+
		"\u00cc\b\u0011\n\u0011\f\u0011\u00cf\t\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0000\u0002 \"\u0013\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$\u0000\u0004\u0001\u0000"+
		"\u0003\u0005\u0001\u0000\u0018\u0019\u0002\u0000\u000b\u000b\u001a\u001a"+
		"\u0001\u0000\u001b \u00dd\u0000&\u0001\u0000\u0000\u0000\u0002.\u0001"+
		"\u0000\u0000\u0000\u00041\u0001\u0000\u0000\u0000\u00064\u0001\u0000\u0000"+
		"\u0000\b6\u0001\u0000\u0000\u0000\n@\u0001\u0000\u0000\u0000\fE\u0001"+
		"\u0000\u0000\u0000\u000eN\u0001\u0000\u0000\u0000\u0010P\u0001\u0000\u0000"+
		"\u0000\u0012U\u0001\u0000\u0000\u0000\u0014i\u0001\u0000\u0000\u0000\u0016"+
		"p\u0001\u0000\u0000\u0000\u0018\u0084\u0001\u0000\u0000\u0000\u001a\u008a"+
		"\u0001\u0000\u0000\u0000\u001c\u0099\u0001\u0000\u0000\u0000\u001e\u00a1"+
		"\u0001\u0000\u0000\u0000 \u00af\u0001\u0000\u0000\u0000\"\u00c3\u0001"+
		"\u0000\u0000\u0000$\u00d0\u0001\u0000\u0000\u0000&\'\u0005\u0001\u0000"+
		"\u0000\'(\u0003\u0002\u0001\u0000()\u0003\f\u0006\u0000)*\u0005\u0002"+
		"\u0000\u0000*\u0001\u0001\u0000\u0000\u0000+-\u0003\u0004\u0002\u0000"+
		",+\u0001\u0000\u0000\u0000-0\u0001\u0000\u0000\u0000.,\u0001\u0000\u0000"+
		"\u0000./\u0001\u0000\u0000\u0000/\u0003\u0001\u0000\u0000\u00000.\u0001"+
		"\u0000\u0000\u000012\u0003\u0006\u0003\u000023\u0003\b\u0004\u00003\u0005"+
		"\u0001\u0000\u0000\u000045\u0007\u0000\u0000\u00005\u0007\u0001\u0000"+
		"\u0000\u00006;\u0003\n\u0005\u000078\u0005\u0006\u0000\u00008:\u0003\n"+
		"\u0005\u000097\u0001\u0000\u0000\u0000:=\u0001\u0000\u0000\u0000;9\u0001"+
		"\u0000\u0000\u0000;<\u0001\u0000\u0000\u0000<\t\u0001\u0000\u0000\u0000"+
		"=;\u0001\u0000\u0000\u0000>A\u0003\u0014\n\u0000?A\u0005!\u0000\u0000"+
		"@>\u0001\u0000\u0000\u0000@?\u0001\u0000\u0000\u0000A\u000b\u0001\u0000"+
		"\u0000\u0000BD\u0003\u000e\u0007\u0000CB\u0001\u0000\u0000\u0000DG\u0001"+
		"\u0000\u0000\u0000EC\u0001\u0000\u0000\u0000EF\u0001\u0000\u0000\u0000"+
		"F\r\u0001\u0000\u0000\u0000GE\u0001\u0000\u0000\u0000HO\u0003\u0010\b"+
		"\u0000IO\u0003\u0012\t\u0000JO\u0003\u0014\n\u0000KO\u0003\u0016\u000b"+
		"\u0000LO\u0003\u0018\f\u0000MO\u0003\u001a\r\u0000NH\u0001\u0000\u0000"+
		"\u0000NI\u0001\u0000\u0000\u0000NJ\u0001\u0000\u0000\u0000NK\u0001\u0000"+
		"\u0000\u0000NL\u0001\u0000\u0000\u0000NM\u0001\u0000\u0000\u0000O\u000f"+
		"\u0001\u0000\u0000\u0000PQ\u0005\u0007\u0000\u0000QR\u0005\b\u0000\u0000"+
		"RS\u0005!\u0000\u0000ST\u0005\t\u0000\u0000T\u0011\u0001\u0000\u0000\u0000"+
		"UV\u0005\n\u0000\u0000VZ\u0005\b\u0000\u0000W[\u0005#\u0000\u0000X[\u0003"+
		"\"\u0011\u0000Y[\u0005$\u0000\u0000ZW\u0001\u0000\u0000\u0000ZX\u0001"+
		"\u0000\u0000\u0000ZY\u0001\u0000\u0000\u0000[d\u0001\u0000\u0000\u0000"+
		"\\`\u0005\u000b\u0000\u0000]a\u0005#\u0000\u0000^a\u0003\"\u0011\u0000"+
		"_a\u0005$\u0000\u0000`]\u0001\u0000\u0000\u0000`^\u0001\u0000\u0000\u0000"+
		"`_\u0001\u0000\u0000\u0000ac\u0001\u0000\u0000\u0000b\\\u0001\u0000\u0000"+
		"\u0000cf\u0001\u0000\u0000\u0000db\u0001\u0000\u0000\u0000de\u0001\u0000"+
		"\u0000\u0000eg\u0001\u0000\u0000\u0000fd\u0001\u0000\u0000\u0000gh\u0005"+
		"\t\u0000\u0000h\u0013\u0001\u0000\u0000\u0000ij\u0005!\u0000\u0000jn\u0005"+
		"\f\u0000\u0000ko\u0003\"\u0011\u0000lo\u0003 \u0010\u0000mo\u0005#\u0000"+
		"\u0000nk\u0001\u0000\u0000\u0000nl\u0001\u0000\u0000\u0000nm\u0001\u0000"+
		"\u0000\u0000o\u0015\u0001\u0000\u0000\u0000pq\u0005\r\u0000\u0000qr\u0005"+
		"\b\u0000\u0000rs\u0003\u001e\u000f\u0000st\u0005\t\u0000\u0000t\u0082"+
		"\u0003\u001c\u000e\u0000uv\u0005\u000e\u0000\u0000vw\u0005\b\u0000\u0000"+
		"wx\u0003\u001e\u000f\u0000xy\u0005\t\u0000\u0000yz\u0003\u001c\u000e\u0000"+
		"z|\u0001\u0000\u0000\u0000{u\u0001\u0000\u0000\u0000|\u007f\u0001\u0000"+
		"\u0000\u0000}{\u0001\u0000\u0000\u0000}~\u0001\u0000\u0000\u0000~\u0080"+
		"\u0001\u0000\u0000\u0000\u007f}\u0001\u0000\u0000\u0000\u0080\u0081\u0005"+
		"\u000f\u0000\u0000\u0081\u0083\u0003\u001c\u000e\u0000\u0082}\u0001\u0000"+
		"\u0000\u0000\u0082\u0083\u0001\u0000\u0000\u0000\u0083\u0017\u0001\u0000"+
		"\u0000\u0000\u0084\u0085\u0005\u0010\u0000\u0000\u0085\u0086\u0005\b\u0000"+
		"\u0000\u0086\u0087\u0003\u001e\u000f\u0000\u0087\u0088\u0005\t\u0000\u0000"+
		"\u0088\u0089\u0003\u001c\u000e\u0000\u0089\u0019\u0001\u0000\u0000\u0000"+
		"\u008a\u008b\u0005\u0011\u0000\u0000\u008b\u008e\u0005\b\u0000\u0000\u008c"+
		"\u008f\u0003\u0004\u0002\u0000\u008d\u008f\u0003\u0014\n\u0000\u008e\u008c"+
		"\u0001\u0000\u0000\u0000\u008e\u008d\u0001\u0000\u0000\u0000\u008f\u0090"+
		"\u0001\u0000\u0000\u0000\u0090\u0091\u0005\u0012\u0000\u0000\u0091\u0094"+
		"\u0003\u001e\u000f\u0000\u0092\u0093\u0005\u0012\u0000\u0000\u0093\u0095"+
		"\u0003\u0014\n\u0000\u0094\u0092\u0001\u0000\u0000\u0000\u0094\u0095\u0001"+
		"\u0000\u0000\u0000\u0095\u0096\u0001\u0000\u0000\u0000\u0096\u0097\u0005"+
		"\t\u0000\u0000\u0097\u0098\u0003\u001c\u000e\u0000\u0098\u001b\u0001\u0000"+
		"\u0000\u0000\u0099\u009b\u0005\u0013\u0000\u0000\u009a\u009c\u0003\u000e"+
		"\u0007\u0000\u009b\u009a\u0001\u0000\u0000\u0000\u009c\u009d\u0001\u0000"+
		"\u0000\u0000\u009d\u009b\u0001\u0000\u0000\u0000\u009d\u009e\u0001\u0000"+
		"\u0000\u0000\u009e\u009f\u0001\u0000\u0000\u0000\u009f\u00a0\u0005\u0014"+
		"\u0000\u0000\u00a0\u001d\u0001\u0000\u0000\u0000\u00a1\u00a2\u0003 \u0010"+
		"\u0000\u00a2\u001f\u0001\u0000\u0000\u0000\u00a3\u00a4\u0006\u0010\uffff"+
		"\uffff\u0000\u00a4\u00a5\u0005\u0015\u0000\u0000\u00a5\u00b0\u0003 \u0010"+
		"\u0006\u00a6\u00a7\u0005\b\u0000\u0000\u00a7\u00a8\u0003 \u0010\u0000"+
		"\u00a8\u00a9\u0005\t\u0000\u0000\u00a9\u00b0\u0001\u0000\u0000\u0000\u00aa"+
		"\u00ab\u0003\"\u0011\u0000\u00ab\u00ac\u0003$\u0012\u0000\u00ac\u00ad"+
		"\u0003\"\u0011\u0000\u00ad\u00b0\u0001\u0000\u0000\u0000\u00ae\u00b0\u0005"+
		"$\u0000\u0000\u00af\u00a3\u0001\u0000\u0000\u0000\u00af\u00a6\u0001\u0000"+
		"\u0000\u0000\u00af\u00aa\u0001\u0000\u0000\u0000\u00af\u00ae\u0001\u0000"+
		"\u0000\u0000\u00b0\u00b9\u0001\u0000\u0000\u0000\u00b1\u00b2\n\u0005\u0000"+
		"\u0000\u00b2\u00b3\u0005\u0016\u0000\u0000\u00b3\u00b8\u0003 \u0010\u0006"+
		"\u00b4\u00b5\n\u0004\u0000\u0000\u00b5\u00b6\u0005\u0017\u0000\u0000\u00b6"+
		"\u00b8\u0003 \u0010\u0005\u00b7\u00b1\u0001\u0000\u0000\u0000\u00b7\u00b4"+
		"\u0001\u0000\u0000\u0000\u00b8\u00bb\u0001\u0000\u0000\u0000\u00b9\u00b7"+
		"\u0001\u0000\u0000\u0000\u00b9\u00ba\u0001\u0000\u0000\u0000\u00ba!\u0001"+
		"\u0000\u0000\u0000\u00bb\u00b9\u0001\u0000\u0000\u0000\u00bc\u00bd\u0006"+
		"\u0011\uffff\uffff\u0000\u00bd\u00be\u0005\b\u0000\u0000\u00be\u00bf\u0003"+
		"\"\u0011\u0000\u00bf\u00c0\u0005\t\u0000\u0000\u00c0\u00c4\u0001\u0000"+
		"\u0000\u0000\u00c1\u00c4\u0005\"\u0000\u0000\u00c2\u00c4\u0005!\u0000"+
		"\u0000\u00c3\u00bc\u0001\u0000\u0000\u0000\u00c3\u00c1\u0001\u0000\u0000"+
		"\u0000\u00c3\u00c2\u0001\u0000\u0000\u0000\u00c4\u00cd\u0001\u0000\u0000"+
		"\u0000\u00c5\u00c6\n\u0004\u0000\u0000\u00c6\u00c7\u0007\u0001\u0000\u0000"+
		"\u00c7\u00cc\u0003\"\u0011\u0005\u00c8\u00c9\n\u0003\u0000\u0000\u00c9"+
		"\u00ca\u0007\u0002\u0000\u0000\u00ca\u00cc\u0003\"\u0011\u0004\u00cb\u00c5"+
		"\u0001\u0000\u0000\u0000\u00cb\u00c8\u0001\u0000\u0000\u0000\u00cc\u00cf"+
		"\u0001\u0000\u0000\u0000\u00cd\u00cb\u0001\u0000\u0000\u0000\u00cd\u00ce"+
		"\u0001\u0000\u0000\u0000\u00ce#\u0001\u0000\u0000\u0000\u00cf\u00cd\u0001"+
		"\u0000\u0000\u0000\u00d0\u00d1\u0007\u0003\u0000\u0000\u00d1%\u0001\u0000"+
		"\u0000\u0000\u0014.;@ENZ`dn}\u0082\u008e\u0094\u009d\u00af\u00b7\u00b9"+
		"\u00c3\u00cb\u00cd";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}