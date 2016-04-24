package com.thefuntasty.taste.color;

import android.graphics.Color;

public class TColor {
	public static int gradient(int color1, int color2, float progress) {
		float a = Color.alpha(color1) + progress * Math.abs(Color.alpha(color1) - Color.alpha(color2));
		float r = Color.red(color1) + progress * Math.abs(Color.red(color1) - Color.red(color2));
		float b = Color.blue(color1) + progress * Math.abs(Color.blue(color1) - Color.blue(color2));
		float g = Color.green(color1) + progress * Math.abs(Color.green(color1) - Color.green(color2));

		return Color.argb((int) a, (int) r, (int) g, (int) b);
	}
}
