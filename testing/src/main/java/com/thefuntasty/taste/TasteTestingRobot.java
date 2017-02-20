package com.thefuntasty.taste;

import android.os.RemoteException;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.Until;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TasteTestingRobot {

	private UiDevice testDevice;
	private TasteTestingConfig config;

	public TasteTestingRobot(UiDevice testDevice, TasteTestingConfig config) {
		this.testDevice = testDevice;
		this.config = config;
	}

	// Actions
	public void tapById(String viewId) {
		try {
			testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			throw new TasteTestingException("View with id \"" + viewId + "\" not found", e);
		}
	}

	public void tapByText(String text) {
		try {
			testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			throw new TasteTestingException("View with text \"" + text + "\" not found", e);
		}
	}

	public void tapByContainedText(String text) {
		try {
			testDevice.wait(Until.findObject(By.textContains(text)), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			throw new TasteTestingException("View with text that contains \"" + text + "\" not found", e);
		}
	}

	public void tapByDescription(String contentDescription) {
		try {
			testDevice.wait(Until.findObject(By.desc(contentDescription)), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			throw new TasteTestingException("View with content description \"" + contentDescription + "\" not found", e);
		}
	}

	public void tapByContainedInDescription(String contentDescription) {
		try {
			testDevice.wait(Until.findObject(By.descContains(contentDescription)), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			throw new TasteTestingException("View with content description that contains \"" + contentDescription + "\" not found", e);
		}
	}

	public void writeByText(String findText, String writeText) {
		try {
			testDevice.wait(Until.findObject(By.text(findText)), config.getViewTimeout()).setText(writeText);
		} catch (NullPointerException e) {
			throw new TasteTestingException("View with text \"" + findText + "\" not found", e);
		}
	}

	public void writeById(String viewId, String writeText) {
		try {
			testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).setText(writeText);
		} catch (NullPointerException e) {
			throw new TasteTestingException("View with id \"" + viewId + "\" not found", e);
		}
	}

	public void allowPermission() {
		try {
			testDevice.wait(Until.findObject(By.res("com.android.packageinstaller", "permission_allow_button")), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			throw new TasteTestingException("Permission dialog not found", e);
		}
	}

	public void denyPermission() {
		try {
			testDevice.wait(Until.findObject(By.res("com.android.packageinstaller", "permission_deny_button")), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			throw new TasteTestingException("Permission dialog not found", e);
		}
	}

	public void scroll(@TasteDirection.DirectionType int directionType) {
		int screenWidth = testDevice.getDisplayWidth();
		int screenHeight = testDevice.getDisplayHeight();

		switch (directionType) {
			case TasteDirection.DOWN:
				testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, 0, config.getScrollSteps());
				break;
			case TasteDirection.UP:
				testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight, config.getScrollSteps());
				break;
			case TasteDirection.LEFT:
				testDevice.drag(screenWidth / 4, screenHeight / 2, screenWidth, screenHeight / 2, config.getScrollSteps());
				break;
			case TasteDirection.RIGHT:
				testDevice.drag(screenWidth - screenWidth / 4, screenHeight / 2, 0, screenHeight / 2, config.getScrollSteps());
				break;
			default:
				throw new TasteTestingException("Invalid scroll direction");
		}
	}

	public void halfScroll(@TasteDirection.DirectionType int directionType) {
		int screenWidth = testDevice.getDisplayWidth();
		int screenHeight = testDevice.getDisplayHeight();

		switch (directionType) {
			case TasteDirection.DOWN:
				testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight / 4, config.getScrollSteps());
				break;
			case TasteDirection.UP:
				testDevice.drag(screenWidth / 2, screenHeight / 2, screenWidth / 2, screenHeight / 4 * 3, config.getScrollSteps());
				break;
			case TasteDirection.LEFT:
				testDevice.drag(screenWidth / 4, screenHeight / 2, screenWidth / 2, screenHeight / 2, config.getScrollSteps());
				break;
			case TasteDirection.RIGHT:
				testDevice.drag(screenWidth - screenWidth / 4, screenHeight / 2, screenWidth / 2, screenHeight / 2, config.getScrollSteps());
				break;
			default:
				throw new TasteTestingException("Invalid scroll direction");
		}
	}

	public void scrollUntilId(@TasteDirection.DirectionType int directionType, String viewId) {
		int retry = 0;
		do {
			if (testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getScrollTimeout()) != null) {
				break;
			}
			halfScroll(directionType);
			retry++;
		}
		while (retry <= config.getScrollThreshold());
	}

	public void scrollUntilText(@TasteDirection.DirectionType int directionType, String text) {
		int retry = 0;
		do {
			if (testDevice.wait(Until.findObject(By.text(text)), config.getScrollTimeout()) != null) {
				break;
			}
			halfScroll(directionType);
			retry++;
		}
		while (retry <= config.getScrollThreshold());
	}

	public void dragViewById(@TasteDirection.DirectionType int directionType, String viewId) {
		int screenWidth = testDevice.getDisplayWidth();
		int screenHeight = testDevice.getDisplayHeight();
		int viewX = testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).getVisibleCenter().x;
		int viewY = testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).getVisibleCenter().y;

		switch (directionType) {
			case TasteDirection.DOWN:
				testDevice.drag(viewX, viewY, viewX, viewY - screenHeight, config.getScrollSteps());
				break;
			case TasteDirection.UP:
				testDevice.drag(viewX, viewY, viewX, viewY + screenHeight, config.getScrollSteps());
				break;
			case TasteDirection.LEFT:
				testDevice.drag(viewX, viewY, viewX + screenWidth, viewY, config.getScrollSteps());
				break;
			case TasteDirection.RIGHT:
				testDevice.drag(viewX, viewY, viewX - screenWidth, viewY, config.getScrollSteps());
				break;
			default:
				throw new TasteTestingException("Invalid drag direction");
		}
	}

	public void dragViewByText(@TasteDirection.DirectionType int directionType, String text) {
		int screenWidth = testDevice.getDisplayWidth();
		int screenHeight = testDevice.getDisplayHeight();
		int viewX = testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()).getVisibleCenter().x;
		int viewY = testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()).getVisibleCenter().y;

		switch (directionType) {
			case TasteDirection.DOWN:
				testDevice.drag(viewX, viewY, viewX, viewY - screenHeight, config.getScrollSteps());
				break;
			case TasteDirection.UP:
				testDevice.drag(viewX, viewY, viewX, viewY + screenHeight, config.getScrollSteps());
				break;
			case TasteDirection.LEFT:
				testDevice.drag(viewX, viewY, viewX + screenWidth, viewY, config.getScrollSteps());
				break;
			case TasteDirection.RIGHT:
				testDevice.drag(viewX, viewY, viewX - screenWidth, viewY, config.getScrollSteps());
				break;
			default:
				throw new TasteTestingException("Invalid drag direction");
		}
	}

	public void pressBack() {
		testDevice.pressBack();
	}

	public void pressHome() {
		testDevice.pressHome();
	}

	public void pressRecents() {
		try {
			testDevice.pressRecentApps();
		} catch (RemoteException e) {
			throw new TasteTestingException(e);
		}
	}

	// Assertions
	public void notPresentByText(String text) {
		assertNull("Text \"" + text + "\" should not be present", testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()));
	}

	public void notPresentById(String viewId) {
		assertNull("View with id \"" + viewId + "\" should not be present", testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()));
	}

	public void presentByText(String text) {
		assertNotNull("Text \"" + text + "\" is not present", testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()));
	}

	public void presentById(String viewId) {
		assertNotNull("View with id \"" + viewId + "\" is not present", testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()));
	}

	public void textInIdEquals(String viewId, String text) {
		assertTrue("Text in view with id \"" + viewId + "\" is not \"" + text + "\"", testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).getText().equals(text));
	}

	public void textInIdContains(String viewId, String text) {
		assertTrue("Text in view with id \"" + viewId + "\" does not contain \"" + text + "\"", testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).getText().contains(text));
	}

	public void textInIdDiffer(String viewId, String text) {
		assertFalse("Text in view with id \"" + viewId + "\" should not be \"" + text + "\"", testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).getText().equals(text));
	}

	public void enabledById(String viewId) {
		assertTrue("View with id \"" + viewId + "\" should not be enabled", testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).isEnabled());
	}

	public void disabledById(String viewId) {
		assertFalse("View with id \"" + viewId + "\" should not be disabled", testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).isEnabled());
	}

	public void checkedById(String viewId) {
		assertTrue("View with id \"" + viewId + "\" is not checked", testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).isChecked());
	}

	public void notCheckedById(String viewId) {
		assertFalse("View with id \"" + viewId + "\" should not be checked", testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).isChecked());
	}

	public void checkedByText(String text) {
		assertTrue("View with text \"" + text + "\" is not checked", testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()).isChecked());
	}

	public void notCheckedByText(String text) {
		assertFalse("View with text \"" + text + "\" should not be checked", testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()).isChecked());
	}

	public void selectedById(String viewId) {
		assertTrue("View with id \"" + viewId + "\" is not selected", testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).isSelected());
	}

	public void notSelectedById(String viewId) {
		assertFalse("View with id \"" + viewId + "\" should not be selected", testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).isSelected());
	}

	public void selectedByText(String text) {
		assertTrue("View with text \"" + text + "\" is not selected", testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()).isSelected());
	}

	public void notSelectedByText(String text) {
		assertFalse("View with text \"" + text + "\" should not be selected", testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()).isSelected());
	}

	// Misc

	public String getTextById(String viewId) {
		try {
			return testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).getText();
		} catch (NullPointerException e) {
			throw new TasteTestingException("View with id \"" + viewId + "\" not found", e);
		}
	}

	public void takeScreenshot(String name) {
		wait(config.getScreenshotWait());
		testDevice.takeScreenshot(TasteTestingSpoonWrapper.getScreenshotDirectory(name));
	}

	public void waitForId(String viewId) {
		if (testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()) == null) {
			throw new TasteTestingException("View with id \"" + viewId + "\" not found");
		}
	}

	public void waitForId(String viewId, int seconds) {
		if (testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), 1000 * seconds) == null) {
			throw new TasteTestingException("View with id \"" + viewId + "\" not found");
		}
	}

	public void waitForText(String text) {
		if (testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()) == null) {
			throw new TasteTestingException("View with text \"" + text + "\" not found");
		}
	}

	public void waitForText(String text, int seconds) {
		if (testDevice.wait(Until.findObject(By.text(text)), 1000 * seconds) == null) {
			throw new TasteTestingException("View with text \"" + text + "\" not found");
		}
	}

	public void wait(int seconds) {
		// This is ugly but it works
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
