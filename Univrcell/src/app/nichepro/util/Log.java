/*******************************************************************************
 * Copyright (c) 2013 Nichepro Technologies and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Nichepro technologies
 *
 *******************************************************************************/

package app.nichepro.util;



// Class used to conditionally enable and disable logging in
// in android app
/**
 * The Class Log.
 */
public class Log {
	
	/** The Constant LOG. */
	static final boolean LOG = true;

	/**
	 * I. Information
	 *
	 * @param tag the tag
	 * @param string the string
	 */
	public static void i(String tag, String string) {
		if (LOG)
			android.util.Log.i(tag, string);
	}

	/**
	 * E. Error
	 *
	 * @param tag the tag
	 * @param string the string
	 */
	public static void e(String tag, String string) {
		if (LOG)
			android.util.Log.e(tag, string);
	}

	/**
	 * D. Debug
	 *
	 * @param tag the tag
	 * @param e the string
	 */
	public static void d(String tag, String e) {
		if (LOG)
			android.util.Log.d(tag, e);
	}

	/**
	 * V. Verbose
	 *
	 * @param tag the tag
	 * @param string the string
	 */
	public static void v(String tag, String string) {
		if (LOG)
			android.util.Log.v(tag, string);
	}

	/**
	 * W. Warning
	 *
	 * @param tag the tag
	 * @param string the string
	 */
	public static void w(String tag, String string) {
		if (LOG)
			android.util.Log.w(tag, string);
	}
}
