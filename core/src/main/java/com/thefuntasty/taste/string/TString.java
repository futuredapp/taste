package com.thefuntasty.taste.string;

import java.util.Iterator;
import java.util.Locale;

public class TString {

	public static String capitalize(String str) {
		if (str.isEmpty()) {
			return "";
		} else {
			return str.substring(0, 1).toUpperCase(Locale.getDefault()) + str.substring(1).toLowerCase(Locale.getDefault());
		}
	}

	/**
	 * Returns true if the string is null or 0-length.
	 *
	 * @param str the string to be examined
	 * @return true if str is null or zero length
	 */
	public static boolean isEmpty(CharSequence str) {
		return str == null || str.length() == 0;
	}

	/**
	 * Returns a string containing the tokens joined by delimiters.
	 *
	 * @param tokens an array objects to be joined. Strings will be formed from
	 *               the objects by calling object.toString().
	 */
	public static String join(CharSequence delimiter, Iterable tokens) {
		StringBuilder sb = new StringBuilder();
		Iterator<?> it = tokens.iterator();
		if (it.hasNext()) {
			sb.append(it.next());
			while (it.hasNext()) {
				sb.append(delimiter);
				sb.append(it.next());
			}
		}
		return sb.toString();
	}
}
