package com.thefuntasty.taste.file;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.util.UUID;

public class TFile {

	public static File temp(Context context) {
		return new File(context.getExternalCacheDir(), UUID.randomUUID().toString());
	}
}
