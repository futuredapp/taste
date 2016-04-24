package com.thefuntasty.taste.string;

public class TString {
	public static String ucfirst(String subject) {
		return Character.toUpperCase(subject.charAt(0)) + subject.substring(1);
	}
}

