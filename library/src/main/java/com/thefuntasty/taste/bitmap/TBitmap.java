package com.thefuntasty.taste.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TBitmap {
	public static String toBase64(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);

		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	public static Bitmap fromBase64(String s) {
		byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
	}

	public static File saveBitmapToFile(Bitmap bitmap, String filename) {
		FileOutputStream fos = null;
		try {
			String filePath = App.context().getCacheDir().getPath() + File.separator + filename;
			fos = new FileOutputStream(filePath);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

			return new File(filePath);
		} catch(FileNotFoundException e){
			// nothing to do
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// nothing to do
				}
			}
		}
		return null;
	}
}
