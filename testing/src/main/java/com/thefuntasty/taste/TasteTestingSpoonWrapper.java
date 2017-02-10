package com.thefuntasty.taste;

import android.os.Build;

import java.io.File;

public class TasteTestingSpoonWrapper {

	private TasteTestingSpoonWrapper() {
	}

	private static final String TEST_CASE_CLASS_JUNIT_3 = "android.test.InstrumentationTestCase";
	private static final String TEST_CASE_METHOD_JUNIT_3 = "runMethod";
	private static final String TEST_CASE_CLASS_JUNIT_4 = "org.junit.runners.model.FrameworkMethod$1";
	private static final String TEST_CASE_METHOD_JUNIT_4 = "runReflectiveCall";
	private static final String TEST_CASE_CLASS_CUCUMBER_JVM = "cucumber.runtime.model.CucumberFeature";
	private static final String TEST_CASE_METHOD_CUCUMBER_JVM = "run";
	private static final int MARSHMALLOW_API_LEVEL = 23;
	private static boolean deleted = false;

	// Spoon screenshots
	public static File getScreenshotDirectory(String screenshotTitle) {
		File directory = new File("/sdcard/app_spoon-screenshots");

		if (!deleted) {
			deletePath(directory, false);
			deleted = true;
		}

		StackTraceElement testClass = findTestClassTraceElement(Thread.currentThread().getStackTrace());
		String className = testClass.getClassName().replaceAll("[^A-Za-z0-9._-]", "_");
		String methodName = testClass.getMethodName();

		File dirClass = new File(directory, className);
		File dirMethod = new File(dirClass, methodName);
		createDir(dirMethod);

		File screenshotDirectory = dirMethod;
		String screenshotName = System.currentTimeMillis() + "_" + screenshotTitle + ".png";

		File screenshotFile = new File(screenshotDirectory, screenshotName);
		return screenshotFile;
	}

	private static void createDir(File dir) {
		File parent = dir.getParentFile();
		if (!parent.exists()) {
			createDir(parent);
		}
		if (!dir.exists() && !dir.mkdirs()) {
			//throw new IllegalAccessException("Unable to create output dir: " + dir.getAbsolutePath());
		}
		//chmodPlusRWX(dir);
	}

	static StackTraceElement findTestClassTraceElement(StackTraceElement[] trace) {
		for (int i = trace.length - 1; i >= 0; i--) {
			StackTraceElement element = trace[i];
			if (TEST_CASE_CLASS_JUNIT_3.equals(element.getClassName()) //
					&& TEST_CASE_METHOD_JUNIT_3.equals(element.getMethodName())) {
				return extractStackElement(trace, i);
			}

			if (TEST_CASE_CLASS_JUNIT_4.equals(element.getClassName()) //
					&& TEST_CASE_METHOD_JUNIT_4.equals(element.getMethodName())) {
				return extractStackElement(trace, i);
			}
			if (TEST_CASE_CLASS_CUCUMBER_JVM.equals(element.getClassName()) //
					&& TEST_CASE_METHOD_CUCUMBER_JVM.equals(element.getMethodName())) {
				return extractStackElement(trace, i);
			}
		}

		throw new IllegalArgumentException("Could not find test class!");
	}

	private static StackTraceElement extractStackElement(StackTraceElement[] trace, int i) {
		//Stacktrace length changed in M
		int testClassTraceIndex = Build.VERSION.SDK_INT >= MARSHMALLOW_API_LEVEL ? (i - 2) : (i - 3);
		return trace[testClassTraceIndex];
	}

	public static void deletePath(File path, boolean inclusive) {
		if (path.isDirectory()) {
			File[] children = path.listFiles();
			if (children != null) {
				for (File child : children) {
					deletePath(child, true);
				}
			}
		}
		if (inclusive) {
			path.delete();
		}
	}
}
