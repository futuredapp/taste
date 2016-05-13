package com.thefuntasty.taste.infinity;

import android.support.annotation.IntDef;

public class Constant {
	public static final int IDLE = 0;
	public static final int LOADING = 1;
	public static final int FINISHED = 2;

	@IntDef({IDLE, LOADING, FINISHED})
	public @interface Status {}

	public static final int FIRST = 0;
	public static final int NEXT = 1;

	@IntDef({FIRST, NEXT})
	public @interface Part {}
}
