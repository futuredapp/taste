package com.thefuntasty.taste.locale;

import android.app.Application;
import android.content.res.Configuration;

import java.util.Locale;

public class TasteLocale {
	private static String language = Locale.getDefault().getLanguage();
	private static String country = Locale.getDefault().getCountry();

	public static Locale getLocale() {
		return new Locale(language, country);
	}

	public static void init(Application application, String l, String c) {
		language = l;
		country = c;
		Locale.setDefault(getLocale());
		Configuration config = new Configuration();
		config.locale = getLocale();
		application.getResources().updateConfiguration(config, application.getResources().getDisplayMetrics());
	}
}
