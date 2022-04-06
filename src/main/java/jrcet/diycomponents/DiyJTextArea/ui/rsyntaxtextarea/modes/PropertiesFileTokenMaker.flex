/*
 * 03/21/2005
 *
 * PropertiesFileTokenMaker.java - Scanner for properties files.
 * 
 * This library is distributed under a modified BSD license.  See the included
 * LICENSE file for details.
 */
package jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.modes;

import java.io.*;
import javax.swing.text.Segment;

import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.*;


/**
 * This class splits up text into tokens representing a Java properties file.<p>
 *
 * This implementation was created using
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.1; however, the generated file
 * was modified for performance.  Memory allocation needs to be almost
 * completely removed to be competitive with the handwritten lexers (subclasses
 * of <code>AbstractTokenMaker</code>, so this class has been modified so that
 * Strings are never allocated (via yytext()), and the scanner never has to
 * worry about refilling its buffer (needlessly copying chars around).
 * We can achieve this because RText always scans exactly 1 line of tokens at a
 * time, and hands the scanner this line as an array of characters (a Segment
 * really).  Since tokens contain pointers to char arrays instead of Strings
 * holding their contents, there is no need for allocating new memory for
 * Strings.<p>
 *
 * The actual algorithm generated for scanning has, of course, not been
 * modified.<p>
 *
 * If you wish to regenerate this file yourself, keep in mind the following:
 * <ul>
 *   <li>The generated <code>PropertiesFileTokenMaker.java</code> file will
 *       contain two definitions of both <code>zzRefill</code> and
 *       <code>yyreset</code>.  You should hand-delete the second of each
 *       definition (the ones generated by the lexer), as these generated
 *       methods modify the input buffer, which we'll never have to do.
 *   <li>You should also change the declaration/definition of zzBuffer to NOT
 *       be initialized.  This is a needless memory allocation for us since we
 *       will be pointing the array somewhere else anyway.
 *   <li>You should NOT call <code>yylex()</code> on the generated scanner
 *       directly; rather, you should use <code>getTokenList</code> as you would
 *       with any other <code>TokenMaker</code> instance.
 * </ul>
 *
 * @author Robert Futrell
 * @version 0.4
 *
 */
%%

%public
%class PropertiesFileTokenMaker
%extends AbstractJFlexTokenMaker
%unicode
%type jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.Token


%{


	/**
	 * Constructor.  This must be here because JFlex does not generate a
	 * no-parameter constructor.
	 */
	public PropertiesFileTokenMaker() {
		super();
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int tokenType) {
		addToken(zzStartRead, zzMarkedPos-1, tokenType);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param tokenType The token's type.
	 */
	private void addToken(int start, int end, int tokenType) {
		int so = start + offsetShift;
		addToken(zzBuffer, start,end, tokenType, so);
	}


	/**
	 * Adds the token specified to the current linked list of tokens.
	 *
	 * @param array The character array.
	 * @param start The starting offset in the array.
	 * @param end The ending offset in the array.
	 * @param tokenType The token's type.
	 * @param startOffset The offset in the document at which this token
	 *                    occurs.
	 */
	@Override
	public void addToken(char[] array, int start, int end, int tokenType, int startOffset) {
		super.addToken(array, start,end, tokenType, startOffset);
		zzStartRead = zzMarkedPos;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String[] getLineCommentStartAndEnd(int languageIndex) {
		return new String[] { "#", null };
	}


	/**
	 * Returns the first token in the linked list of tokens generated
	 * from <code>text</code>.  This method must be implemented by
	 * subclasses so they can correctly implement syntax highlighting.
	 *
	 * @param text The text from which to get tokens.
	 * @param initialTokenType The token type we should start with.
	 * @param startOffset The offset into the document at which
	 *        <code>text</code> starts.
	 * @return The first <code>Token</code> in a linked list representing
	 *         the syntax highlighted text.
	 */
	public Token getTokenList(Segment text, int initialTokenType, int startOffset) {

		resetTokenList();
		this.offsetShift = -text.offset + startOffset;

		// Start off in the proper state.
		int state = Token.NULL;
		switch (initialTokenType) {
			case Token.LITERAL_STRING_DOUBLE_QUOTE:
				state = VALUE;
				start = text.offset;
				break;
			default:
				state = Token.NULL;
		}

		s = text;
		try {
			yyreset(zzReader);
			yybegin(state);
			return yylex();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return new TokenImpl();
		}

	}


	/**
	 * Refills the input buffer.
	 *
	 * @return      <code>true</code> if EOF was reached, otherwise
	 *              <code>false</code>.
	 * @exception   IOException  if any I/O-Error occurs.
	 */
	private boolean zzRefill() {
		return zzCurrentPos>=s.offset+s.count;
	}


	/**
	 * Resets the scanner to read from a new input stream.
	 * Does not close the old reader.
	 *
	 * All internal variables are reset, the old input stream 
	 * <b>cannot</b> be reused (internal buffer is discarded and lost).
	 * Lexical state is set to <tt>YY_INITIAL</tt>.
	 *
	 * @param reader   the new input stream 
	 */
	public final void yyreset(java.io.Reader reader) {
		// 's' has been updated.
		zzBuffer = s.array;
		/*
		 * We replaced the line below with the two below it because zzRefill
		 * no longer "refills" the buffer (since the way we do it, it's always
		 * "full" the first time through, since it points to the segment's
		 * array).  So, we assign zzEndRead here.
		 */
		//zzStartRead = zzEndRead = s.offset;
		zzStartRead = s.offset;
		zzEndRead = zzStartRead + s.count - 1;
		zzCurrentPos = zzMarkedPos = zzPushbackPos = s.offset;
		zzLexicalState = YYINITIAL;
		zzReader = reader;
		zzAtBOL  = true;
		zzAtEOF  = false;
	}


%}

Equals				= ([=\:])
Name					= ([^=\: \t\n#!]*)
Whitespace			= ([ \t]+)
Comment				= ([#!].*)
SingleQuote			= (')

%state VALUE

%%

<YYINITIAL> {
	{Name}			{ addToken(Token.RESERVED_WORD); }
	{Equals}			{ start = zzMarkedPos; addToken(Token.OPERATOR); yybegin(VALUE); }
	{Whitespace}		{ addToken(Token.WHITESPACE); }
	{Comment}			{ addToken(Token.COMMENT_EOL); }
	<<EOF>>			{ addNullToken(); return firstToken; }
}

<VALUE> {
	{SingleQuote}[^']*{SingleQuote}?	{ addToken(start, zzMarkedPos-1, Token.LITERAL_STRING_DOUBLE_QUOTE); start = zzMarkedPos; }
	[^'\{\\]+						{}
	"{"[^\}]*"}"?					{ int temp=zzStartRead; addToken(start, zzStartRead-1, Token.LITERAL_STRING_DOUBLE_QUOTE); addToken(temp, zzMarkedPos-1, Token.VARIABLE); start = zzMarkedPos; }
	[\\].							{}
	[\\]							{ addToken(start, zzEndRead, Token.LITERAL_STRING_DOUBLE_QUOTE); return firstToken; }
	<<EOF>>							{ addToken(start,zzStartRead-1, Token.LITERAL_STRING_DOUBLE_QUOTE); addNullToken(); return firstToken; }
}
