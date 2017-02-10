package com.thefuntasty.taste;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TasteDirection {

	private TasteDirection() {

	}

	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int UP = 2;
	public static final int DOWN = 3;

	@Retention(RetentionPolicy.SOURCE)
	@IntDef({LEFT, RIGHT, UP, DOWN})
	public @interface DirectionType {}
}
