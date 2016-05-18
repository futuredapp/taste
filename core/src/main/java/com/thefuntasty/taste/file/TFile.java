package com.thefuntasty.taste.file;

import android.os.Environment;

import java.io.File;
import java.util.UUID;

public class TFile {

	public static File temp() {
		File dir = Environment.getExternalStorageDirectory();
		dir.mkdirs();
		return new File(dir, UUID.randomUUID().toString());
	}
}
