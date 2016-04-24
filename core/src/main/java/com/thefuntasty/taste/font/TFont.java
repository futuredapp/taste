package com.thefuntasty.taste.font;

import android.graphics.Typeface;
import android.support.v4.util.SimpleArrayMap;

import com.thefuntasty.taste.Taste;

public class TFont {

	private static final SimpleArrayMap<String, Typeface> cache = new SimpleArrayMap<>();

	public static Typeface get(String name) {
		synchronized (cache) {
			if (!cache.containsKey(name)) {
				Typeface t = Typeface.createFromAsset(Taste.context().getAssets(), String.format("fonts/%s.ttf", name));
				cache.put(name, t);
				return t;
			}
			return cache.get(name);
		}
	}
}