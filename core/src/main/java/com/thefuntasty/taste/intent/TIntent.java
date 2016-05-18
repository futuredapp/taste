package com.thefuntasty.taste.intent;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.thefuntasty.taste.bitmap.TBitmap;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class TIntent {
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
	public static Intent createLibraryIntent(boolean allowMultiple) {
		Intent intent = new Intent();
		intent.setType("image/*");
		if (allowMultiple && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
		}
		intent.setAction(Intent.ACTION_GET_CONTENT);
		return intent;
	}

	public static Intent createCameraIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}

	public static Bitmap getBitmapFromLibrary(Context c, Intent intent, int size) {
		Uri uri = intent.getData();
		if (isGoogleAppsUri(uri)) {
			return decodeGooglePhotosStream(c, uri);
		}
		return TBitmap.getScaledBitmapFromPath(getPath(c, uri), size);
	}

	public static Bitmap getBitmapFromCamera(File f, int size) {
		Bitmap b = TBitmap.getScaledBitmapFromPath(f.getAbsolutePath(), size);
		try {
			ExifInterface ei = new ExifInterface(f.getAbsolutePath());
			int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					b = TBitmap.rotateImage(b, 90);
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					b = TBitmap.rotateImage(b, 180);
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					b = TBitmap.rotateImage(b, 270);
					break;
			}
		} catch (IOException ioe) {
			// Never mind
		}

		return b;
	}

	/**
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri     The Uri to query.
	 */
	public static String getPath(final Context context, final Uri uri) {

		final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

		if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
			if (isExternalStorageDocument(uri)) { // ExternalStorageProvider
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}

			} else if (isDownloadsDocument(uri)) { // DownloadsProvider

				final String id = DocumentsContract.getDocumentId(uri);
				final Uri contentUri = ContentUris.withAppendedId(
						Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

				return getDataColumn(context, contentUri, null, null);
			} else if (isMediaDocument(uri)) { // MediaProvider
				final String docId = DocumentsContract.getDocumentId(uri);
				final String[] split = docId.split(":");
				final String type = split[0];

				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}

				final String selection = "_id=?";
				final String[] selectionArgs = new String[]{
						split[1]
				};

				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		} else if ("content".equalsIgnoreCase(uri.getScheme())) { // MediaStore (and general)
			return getDataColumn(context, uri, null, null);
		} else if ("file".equalsIgnoreCase(uri.getScheme())) { // File
			return uri.getPath();
		}

		return null;
	}

	public static Bitmap decodeGooglePhotosStream(Context context, Uri uri) {
		InputStream is;
		InputStream is2;
		if (uri.getAuthority() != null) {
			try {
				is = context.getContentResolver().openInputStream(uri);
				is2 = context.getContentResolver().openInputStream(uri);

				BitmapFactory.Options o = new BitmapFactory.Options();
				o.inJustDecodeBounds = true;
				BitmapFactory.decodeStream(is, null, o);

				is.close();

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inSampleSize = TBitmap.calculateInSampleSize(o, 800, 800);

				Bitmap bmp = BitmapFactory.decodeStream(is2, null, options);
				is2.close();

				return bmp;
			} catch (IOException e) {
				// nothing to do
				return null;
			} catch (NullPointerException e) {
				// nothing to do
				return null;
			}
		}
		return null;
	}

	/**
	 * Get the value of the data column for this Uri. This is useful for
	 * MediaStore Uris, and other file-based ContentProviders.
	 *
	 * @param context       The context.
	 * @param uri           The Uri to query.
	 * @param selection     (Optional) Filter used in the query.
	 * @param selectionArgs (Optional) Selection arguments used in the query.
	 * @return The value of the _data column, which is typically a file path.
	 */
	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

		Cursor cursor = null;
		final String column = "_data";
		final String[] projection = {
				column
		};

		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				final int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null) {
				cursor.close();
			}
		}
		return null;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGoogleAppsUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority()) ||
				"com.google.android.apps.photos.contentprovider".equals(uri.getAuthority()) ||
				"com.google.android.apps.docs.storage".equals(uri.getAuthority());
	}
}