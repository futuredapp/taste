package com.thefuntasty.taste.locale;

import android.content.res.Configuration;

import com.thefuntasty.taste.Taste;

import java.util.Locale;

public class TLocale {
	private static String language = Locale.getDefault().getLanguage();
	private static String country = Locale.getDefault().getCountry();

	public static Locale getLocale() {
		return new Locale(language, country);
	}

	public static void set(String l, String c) {
		language = l;
		country = c;
		Locale.setDefault(getLocale());
		Configuration config = new Configuration();
		config.locale = getLocale();
		Taste.context().getResources().updateConfiguration(config, Taste.context().getResources().getDisplayMetrics());
	}
}
