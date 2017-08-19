package com.thefuntasty.taste;

import com.thefuntasty.taste.res.TRes;

/**
 * Static helper T
 */
public class T {

	private static TRes res;

	/**
	 * Returns TRes instance.
	 *
	 * @deprecated Use `new TRes(Context)` instead.
	 * @return TRes
	 */
	@Deprecated
	public static TRes res() {
		if (res == null) {
			res = new TRes(Taste.context());
		}
		return res;
	}
}
