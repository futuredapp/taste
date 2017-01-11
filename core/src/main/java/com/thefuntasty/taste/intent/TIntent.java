package com.thefuntasty.taste.intent;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import java.io.File;

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
}