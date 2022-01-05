/*
 * 07/31/2009
 *
 * AbstractParser.java - A base implementation for parsers.
 *
 * This library is distributed under a modified BSD license.  See the included
 * LICENSE file for details.
 */
package jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.parser;

import jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.focusabletip.FocusableTip;

import java.net.URL;


/**
 * A base class for {@link jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.parser.Parser} implementations.  Most <code>Parser</code>s
 * should be able to extend this class.
 *
 * @author Robert Futrell
 * @version 1.0
 */
public abstract class AbstractParser implements Parser {

	/**
	 * Whether this parser is enabled.  If this is <code>false</code>, then
	 * this parser will not be run.
	 */
	private boolean enabled;

	/**
	 * Listens for events from
	 * {@link FocusableTip}s generated
	 * from this parser's notices.
	 */
	private jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.parser.ExtendedHyperlinkListener linkListener;


	/**
	 * Constructor.
	 */
	protected AbstractParser() {
		setEnabled(true);
	}


	@Override
	public jrcet.diycomponents.DiyJTextArea.ui.rsyntaxtextarea.parser.ExtendedHyperlinkListener getHyperlinkListener() {
		return linkListener;
	}


	/**
	 * Returns <code>null</code>.  Parsers that wish to show images in their
	 * tool tips should override this method to return the image base URL.
	 *
	 * @return <code>null</code> always.
	 */
	@Override
	public URL getImageBase() {
		return null;
	}


	@Override
	public boolean isEnabled() {
		return enabled;
	}


	/**
	 * Toggles whether this parser is enabled.
	 *
	 * @param enabled Whether this parser is enabled.
	 * @see #isEnabled()
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	/**
	 * Returns the listener for this parser.
	 *
	 * @param listener The new listener.
	 * @see #getHyperlinkListener()
	 */
	public void setHyperlinkListener(ExtendedHyperlinkListener listener) {
		linkListener = listener;
	}


}
