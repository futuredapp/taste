package com.thefuntasty.taste.file;

import android.os.Environment;

import java.io.File;
import java.util.UUID;

public class TFile {

	public static File temp() {
		return new File(Environment.getExternalStorageDirectory(), UUID.randomUUID().toString());
	}
}
