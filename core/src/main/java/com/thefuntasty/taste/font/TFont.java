package com.thefuntasty.taste.font;

import android.graphics.Typeface;
import android.support.annotation.StringDef;
import android.support.v4.util.SimpleArrayMap;

import com.thefuntasty.taste.Taste;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class TFont {

	private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap<>();

	public static final String TTF = ".ttf";
	public static final String OTF = ".otf";

	@StringDef({TTF, OTF})
	@Retention(RetentionPolicy.SOURCE)
	public @interface FontType {}

	/**
	 * Return TTF font, placed in assets at fonts folder.
	 *
	 * @param name Name of font.
	 * @return Typeface font.
	 */
	public static Typeface get(String name) {
		return get(name, TTF);
	}

	/**
	 * Return TTF or OTF font, placed in assets at fonts folder.
	 *
	 * @param name     Name of font.
	 * @param fontType Specify font type TTF or OTF.
	 * @return Typeface font.
	 */
	public static Typeface get(String name, @FontType String fontType) {
		synchronized (cache) {
			if (!cache.containsKey(name)) {
				Typeface t = Typeface.createFromAsset(Taste.context().getAssets(), "fonts/" + name + fontType);
				cache.put(name, t);
				return t;
			}
			return cache.get(name);
		}
	}
}