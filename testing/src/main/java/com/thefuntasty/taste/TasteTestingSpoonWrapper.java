package com.thefuntasty.taste;

import android.os.Build;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

	public static File getScreenshotDirectory(String screenshotTitle) {
		File directory = new File("/sdcard/app_spoon-screenshots");

		StackTraceElement testClass = findTestClassTraceElement(Thread.currentThread().getStackTrace());
		String className = testClass.getClassName().replaceAll("[^A-Za-z0-9._-]", "_");
		String methodName = testClass.getMethodName();

		File dirClass = new File(directory, className);
		File dirMethod = new File(dirClass, methodName);
		createDir(dirMethod);

		long currentTimeMillis = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy-HH-mm-ss", Locale.getDefault());
		Date date = new Date(currentTimeMillis);

		String screenshotName = sdf.format(date) + "_" + screenshotTitle + ".png";
		return new File(dirMethod, screenshotName);
	}

	private static void createDir(File dir) {
		File parent = dir.getParentFile();
		if (!parent.exists()) {
			createDir(parent);
		}
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
}
