package com.thefuntasty.taste.bitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class TBitmap {

	public static String toBase64(Bitmap bm, Bitmap.CompressFormat format, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(format, quality, baos);

		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	public static Bitmap fromBase64(String s) {
		byte[] decodedString = Base64.decode(s, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
	}

	public static void saveBitmapToFile(Bitmap bitmap, File f, Bitmap.CompressFormat format, int quality) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			bitmap.compress(format, quality, fos);
		} catch (FileNotFoundException e) {
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
	}

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		return Math.min(options.outWidth / reqWidth, options.outHeight / reqHeight);
	}

	public static Bitmap getScaledBitmapFromPath(String path, int size) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		options.inSampleSize = calculateInSampleSize(options, size, size);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	public static Bitmap rotateImage(Bitmap source, float angle) {
		Bitmap retVal;

		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

		return retVal;
	}

	public static Bitmap getCroppedRectBitmap(Bitmap srcBmp) {
		Bitmap dstBmp;
		if (srcBmp.getWidth() >= srcBmp.getHeight()) {
			dstBmp = Bitmap.createBitmap(
					srcBmp,
					srcBmp.getWidth() / 2 - srcBmp.getHeight() / 2,
					0,
					srcBmp.getHeight(),
					srcBmp.getHeight()
			);
		} else {
			dstBmp = Bitmap.createBitmap(
					srcBmp,
					0,
					srcBmp.getHeight() / 2 - srcBmp.getWidth() / 2,
					srcBmp.getWidth(),
					srcBmp.getWidth()
			);
		}
		srcBmp.recycle();
		return dstBmp;
	}
}
