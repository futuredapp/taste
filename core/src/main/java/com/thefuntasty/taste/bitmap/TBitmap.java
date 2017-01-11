package com.thefuntasty.taste.bitmap;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.graphics.BitmapFactory.decodeFile;

public class TBitmap {

	// region Base64

	public static String toBase64(Bitmap sourceBitmap, Bitmap.CompressFormat format, int quality) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		sourceBitmap.compress(format, quality, baos);

		byte[] b = baos.toByteArray();
		return Base64.encodeToString(b, Base64.DEFAULT);
	}

	public static Bitmap fromBase64(String source) {
		byte[] decodedString = Base64.decode(source, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
	}

	// endregion

	// region Bitmap Transformations

	public static Bitmap rotateImage(Bitmap source, float angle) {
		Bitmap retVal;

		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		retVal = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

		return retVal;
	}

	public static Bitmap getCroppedRectBitmap(Bitmap sourceBitmap) {
		Bitmap dstBmp;
		if (sourceBitmap.getWidth() >= sourceBitmap.getHeight()) {
			dstBmp = Bitmap.createBitmap(
					sourceBitmap,
					sourceBitmap.getWidth() / 2 - sourceBitmap.getHeight() / 2,
					0,
					sourceBitmap.getHeight(),
					sourceBitmap.getHeight()
			);
		} else {
			dstBmp = Bitmap.createBitmap(
					sourceBitmap,
					0,
					sourceBitmap.getHeight() / 2 - sourceBitmap.getWidth() / 2,
					sourceBitmap.getWidth(),
					sourceBitmap.getWidth()
			);
		}
		return dstBmp;
	}

	// endregion

	// region Bitmap to/from File

	public static void saveBitmapToFile(Bitmap bitmap, File destinationFile, Bitmap.CompressFormat format, int quality) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(destinationFile);
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

	public static Bitmap getScaledBitmapFromPath(String path, int minimumEdgeSize) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		decodeFile(path, options);
		options.inSampleSize = calculateInSampleSize(options, minimumEdgeSize, minimumEdgeSize);
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		options.inJustDecodeBounds = false;
		Bitmap b = BitmapFactory.decodeFile(path, options);

		try {
			ExifInterface ei = new ExifInterface(path);
			int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					try {
						b = TBitmap.rotateImage(b, 90);
					} catch (OutOfMemoryError e) {
						e.printStackTrace();
					}
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					try {
						b = TBitmap.rotateImage(b, 180);
					} catch (OutOfMemoryError e) {
						e.printStackTrace();
					}
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					try {
						b = TBitmap.rotateImage(b, 270);
					} catch (OutOfMemoryError e) {
						e.printStackTrace();
					}
					break;
			}
		} catch (Exception e) {
			// Never mind
		}

		return b;
	}

	public static Bitmap getScaledBitmapFromFile(File destinationFile, int minimumEdgeSize) {
		return TBitmap.getScaledBitmapFromPath(destinationFile.getAbsolutePath(), minimumEdgeSize);
	}

	// region Bitmap from Library

	public static Bitmap getScaledBitmapFromLibrary(Context c, Uri uri, int minimumEdgeSize) {
		if (isGoogleAppsUri(uri)) {
			return decodeGooglePhotosStream(c, uri, minimumEdgeSize);
		}
		return TBitmap.getScaledBitmapFromPath(getPath(c, uri), minimumEdgeSize);
	}

	public static Bitmap getScaledBitmapFromLibrary(Context c, Intent intent, int minimumEdgeSize) {
		Uri uri = intent.getData();
		return getScaledBitmapFromLibrary(c, uri, minimumEdgeSize);
	}

	public static List<Uri> getUrisFromLibrary(Context c, Intent data) {
		List<Uri> imagesEncodedList = new ArrayList<>();

		if (data.getData() != null) {
			Uri fileUri = translateUriToFileUri(c, data.getData());
			if (fileUri != null) {
				imagesEncodedList.add(fileUri);
			} else {
				return null;
			}
		} else {
			if (data.getClipData() != null) {
				ClipData mClipData = data.getClipData();
				for (int i = 0; i < mClipData.getItemCount(); i++) {
					ClipData.Item item = mClipData.getItemAt(i);
					Uri uri = item.getUri();
					Uri fileUri = translateUriToFileUri(c, uri);
					if (fileUri != null) {
						imagesEncodedList.add(fileUri);
					}
				}
			}
		}

		return imagesEncodedList;
	}

	// endregion

	// region Bitmap Options manipulation

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		return Math.min(options.outWidth / reqWidth, options.outHeight / reqHeight);
	}

	// endregion

	// region File URI manipulation

	@Nullable private static Uri translateUriToFileUri(Context c, Uri uri) {
		if (isGoogleAppsUri(uri)) {
			return googlePhotosToFile(c, uri);
		}

		String path = getPath(c, uri);
		if (path != null) {
			return Uri.fromFile(new File(path));
		}

		return null;
	}

	private static Uri googlePhotosToFile(Context c, Uri uri) {
		if (uri.getAuthority() != null) {
			try {
				InputStream is = c.getContentResolver().openInputStream(uri);
				File tempFile = File.createTempFile("tmp", "jpeg", c.getCacheDir());
				FileOutputStream fos = new FileOutputStream(tempFile);

				int read;
				byte[] bytes = new byte[1024];

				while ((read = is.read(bytes)) != -1) {
					fos.write(bytes, 0, read);
				}

				return Uri.fromFile(tempFile);

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
	 * Get a file path from a Uri. This will get the the path for Storage Access
	 * Framework Documents, as well as the _data field for the MediaStore and
	 * other file-based ContentProviders.
	 *
	 * @param context The context.
	 * @param uri     The Uri to query.
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
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

	private static Bitmap decodeGooglePhotosStream(Context context, Uri uri, int size) {
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
				options.inSampleSize = TBitmap.calculateInSampleSize(o, size, size);
				options.inPreferredConfig = Bitmap.Config.RGB_565;

				Bitmap bmp = BitmapFactory.decodeStream(is2, null, options);
				is2.close();

				try {
					ExifInterface ei = new ExifInterface(getPath(context, uri));
					int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

					switch (orientation) {
						case ExifInterface.ORIENTATION_ROTATE_90:
							try {
								bmp = TBitmap.rotateImage(bmp, 90);
							} catch (OutOfMemoryError e) {
								e.printStackTrace();
							}
							break;
						case ExifInterface.ORIENTATION_ROTATE_180:
							try {
								bmp = TBitmap.rotateImage(bmp, 180);
							} catch (OutOfMemoryError e) {
								e.printStackTrace();
							}
							break;
						case ExifInterface.ORIENTATION_ROTATE_270:
							try {
								bmp = TBitmap.rotateImage(bmp, 270);
							} catch (OutOfMemoryError e) {
								e.printStackTrace();
							}
							break;
					}
				} catch (Exception e) {
					// Never mind
				}

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
	private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

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
	private static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	private static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	private static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	private static boolean isGoogleAppsUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority()) ||
				"com.google.android.apps.photos.contentprovider".equals(uri.getAuthority()) ||
				"com.google.android.apps.docs.storage".equals(uri.getAuthority()) ||
				"com.sec.android.gallery3d.provider".equals(uri.getAuthority());
	}

	// endregion
}
