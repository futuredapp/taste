package com.thefuntasty.taste;

import android.content.Context;
import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.StaleObjectException;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject2;
import android.support.test.uiautomator.Until;

import com.github.javafaker.Faker;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TasteTestingRobot {

	private Context context;
	private UiDevice testDevice;
	private TasteTestingConfig config;
	private Faker faker = new Faker();

	public TasteTestingRobot(UiDevice testDevice, TasteTestingConfig config) {
		this.context = InstrumentationRegistry.getTargetContext();
		this.testDevice = testDevice;
		this.config = config;
	}

	// Actions
	public void tapById(String viewId) {
		try {
			testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			takeScreenshot("exception");
			throw new TasteTestingException("View with id \"" + viewId + "\" not found", e);
		} catch (StaleObjectException e) {
			tapById(viewId);
		}
	}

	public void tapByText(String text) {
		try {
			testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			takeScreenshot("exception");
			throw new TasteTestingException("View with text \"" + text + "\" not found", e);
		} catch (StaleObjectException e) {
			tapByText(text);
		}
	}

	public void tapByContainedText(String text) {
		try {
			testDevice.wait(Until.findObject(By.textContains(text)), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			takeScreenshot("exception");
			throw new TasteTestingException("View with text that contains \"" + text + "\" not found", e);
		} catch (StaleObjectException e) {
			tapByContainedText(text);
		}
	}

	public void tapByDescription(String contentDescription) {
		try {
			testDevice.wait(Until.findObject(By.desc(contentDescription)), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			takeScreenshot("exception");
			throw new TasteTestingException("View with content description \"" + contentDescription + "\" not found", e);
		} catch (StaleObjectException e) {
			tapByDescription(contentDescription);
		}
	}

	public void tapByContainedInDescription(String contentDescription) {
		try {
			testDevice.wait(Until.findObject(By.descContains(contentDescription)), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			takeScreenshot("exception");
			throw new TasteTestingException("View with content description that contains \"" + contentDescription + "\" not found", e);
		} catch (StaleObjectException e) {
			tapByContainedInDescription(contentDescription);
		}
	}

	public void writeByText(String findText, String writeText) {
		try {
			testDevice.wait(Until.findObject(By.text(findText).clazz("android.widget.EditText")), config.getViewTimeout()).setText(writeText);
		} catch (NullPointerException e) {
			takeScreenshot("exception");
			throw new TasteTestingException("View with text \"" + findText + "\" not found", e);
		} catch (StaleObjectException e) {
			writeByText(findText, writeText);
		}
	}

	public void writeById(String viewId, String writeText) {
		try {
			testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId).clazz("android.widget.EditText")), config.getViewTimeout()).setText(writeText);
		} catch (NullPointerException e) {
			takeScreenshot("exception");
			throw new TasteTestingException("View with id \"" + viewId + "\" not found", e);
		} catch (StaleObjectException e) {
			writeById(viewId, writeText);
		}
	}

	public void writeByDescription(String contentDescription, String writeText) {
		try {
			testDevice.wait(Until.findObject(By.desc(contentDescription).clazz("android.widget.EditText")), config.getViewTimeout()).setText(writeText);
		} catch (NullPointerException e) {
			takeScreenshot("exception");
			throw new TasteTestingException("View with content description \"" + contentDescription + "\" not found", e);
		} catch (StaleObjectException e) {
			writeByText(contentDescription, writeText);
		}
	}

	public void allowPermission() {
		try {
			testDevice.wait(Until.findObject(By.res("com.android.packageinstaller", "permission_allow_button")), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			takeScreenshot("exception");
			throw new TasteTestingException("Permission dialog not found", e);
		}
	}

	public void denyPermission() {
		try {
			testDevice.wait(Until.findObject(By.res("com.android.packageinstaller", "permission_deny_button")), config.getViewTimeout()).click();
		} catch (NullPointerException e) {
			takeScreenshot("exception");
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
				return;
			}
			halfScroll(directionType);
			retry++;
		}
		while (retry <= config.getScrollThreshold());
		takeScreenshot("exception");
		throw new TasteTestingException("View with id \"" + viewId + "\" not found");
	}

	public void scrollUntilText(@TasteDirection.DirectionType int directionType, String text) {
		int retry = 0;
		do {
			if (testDevice.wait(Until.findObject(By.text(text)), config.getScrollTimeout()) != null) {
				return;
			}
			halfScroll(directionType);
			retry++;
		}
		while (retry <= config.getScrollThreshold());
		takeScreenshot("exception");
		throw new TasteTestingException("View with text \"" + text + "\" not found");
	}

	public void dragViewById(@TasteDirection.DirectionType int directionType, String viewId) {
		int screenWidth = testDevice.getDisplayWidth();
		int screenHeight = testDevice.getDisplayHeight();
		int viewX = waitForId(viewId).getVisibleCenter().x;
		int viewY = waitForId(viewId).getVisibleCenter().y;

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
		int viewX = waitForText(text).getVisibleCenter().x;
		int viewY = waitForText(text).getVisibleCenter().y;

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

	public void closeKeyboard() {
		testDevice.pressBack();
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
		try {
			assertNull("Text \"" + text + "\" should not be present", waitForTextOrNull(text));
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			notPresentByText(text);
		}
	}

	public void notPresentById(String viewId) {
		try {
			assertNull("View with id \"" + viewId + "\" should not be present", waitForIdOrNull(viewId));
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			notPresentById(viewId);
		}
	}

	public void presentByText(String text) {
		try {
			assertNotNull("Text \"" + text + "\" is not present", waitForTextOrNull(text));
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			presentByText(text);
		}
	}

	public void presentById(String viewId) {
		try {
			assertNotNull("View with id \"" + viewId + "\" is not present", waitForIdOrNull(viewId));
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			presentById(viewId);
		}
	}

	public void textInIdEquals(String viewId, String text) {
		try {
			assertTrue("Text in view with id \"" + viewId + "\" is not \"" + text + "\"", waitForId(viewId).getText().equals(text));
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			textInIdEquals(viewId, text);
		}
	}

	public void textInIdEqualsCaseInsensitive(String viewId, String text) {
		try {
			assertTrue("Text in view with id \"" + viewId + "\" is not \"" + text + "\"", waitForId(viewId).getText().equalsIgnoreCase(text));
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			textInIdEqualsCaseInsensitive(viewId, text);
		}
	}

	public void textInIdContains(String viewId, String text) {
		try {
			assertTrue("Text in view with id \"" + viewId + "\" does not contain \"" + text + "\"", waitForId(viewId).getText().contains(text));
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			textInIdContains(viewId, text);
		}
	}

	public void textInIdDiffer(String viewId, String text) {
		try {
			assertFalse("Text in view with id \"" + viewId + "\" should not be \"" + text + "\"", waitForId(viewId).getText().equals(text));
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			textInIdDiffer(viewId, text);
		}
	}

	public void enabledById(String viewId) {
		try {
			assertTrue("View with id \"" + viewId + "\" should not be enabled", waitForId(viewId).isEnabled());
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			enabledById(viewId);
		}
	}

	public void disabledById(String viewId) {
		try {
			assertFalse("View with id \"" + viewId + "\" should not be disabled", waitForId(viewId).isEnabled());
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			disabledById(viewId);
		}
	}

	public void checkedById(String viewId) {
		try {
			assertTrue("View with id \"" + viewId + "\" is not checked", waitForId(viewId).isChecked());
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			checkedById(viewId);
		}
	}

	public void notCheckedById(String viewId) {
		try {
			assertFalse("View with id \"" + viewId + "\" should not be checked", waitForId(viewId).isChecked());
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			notCheckedById(viewId);
		}
	}

	public void checkedByText(String text) {
		try {
			assertTrue("View with text \"" + text + "\" is not checked", waitForText(text).isChecked());
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			checkedByText(text);
		}
	}

	public void notCheckedByText(String text) {
		try {
			assertFalse("View with text \"" + text + "\" should not be checked", waitForText(text).isChecked());
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			notCheckedByText(text);
		}
	}

	public void selectedById(String viewId) {
		try {
			assertTrue("View with id \"" + viewId + "\" is not selected", waitForId(viewId).isSelected());
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			selectedById(viewId);
		}
	}

	public void notSelectedById(String viewId) {
		try {
			assertFalse("View with id \"" + viewId + "\" should not be selected", waitForId(viewId).isSelected());
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			notSelectedById(viewId);
		}
	}

	public void selectedByText(String text) {
		try {
			assertTrue("View with text \"" + text + "\" is not selected", waitForText(text).isSelected());
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			selectedByText(text);
		}
	}

	public void notSelectedByText(String text) {
		try {
			assertFalse("View with text \"" + text + "\" should not be selected", waitForText(text).isSelected());
		} catch (AssertionError e) {
			takeScreenshot("exception");
			throw new TasteTestingException(e);
		} catch (StaleObjectException e) {
			notSelectedByText(text);
		}
	}

	// Fake data

	public String getFirstName() {
		return faker.name().firstName();
	}

	public String getLastName() {
		return faker.name().lastName();
	}

	public String getFullName() {
		return getFirstName() + " " + getLastName();
	}

	public String getEmail() {
		return faker.internet().safeEmailAddress();
	}

	public String getRandomString(int length) {
		return faker.lorem().characters(length);
	}

	public String getRandomNumber(int min, int max) {
		return String.valueOf(faker.number().numberBetween(min, max));
	}

	// Misc

	public String getTextById(String viewId) {
		return waitForId(viewId).getText();
	}

	public void takeScreenshot(String name) {
		testDevice.takeScreenshot(TasteTestingSpoonWrapper.getScreenshotDirectory(name));
	}

	public UiObject2 waitForId(String viewId) {
		return waitForId(viewId, config.getViewTimeout());
	}

	public UiObject2 waitForId(String viewId, int milliseconds) {
		UiObject2 view = testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), milliseconds);
		if (view == null) {
			takeScreenshot("exception");
			throw new TasteTestingException("View with id \"" + viewId + "\" not found");
		} else {
			return view;
		}
	}

	public UiObject2 waitForIdOrNull(String viewId) {
		return testDevice.wait(Until.findObject(By.res(config.getPackageName(), viewId)), config.getViewTimeout());
	}

	public UiObject2 waitForText(String text) {
		return waitForText(text, config.getViewTimeout());
	}

	public UiObject2 waitForText(String text, int milliseconds) {
		UiObject2 view = testDevice.wait(Until.findObject(By.text(text)), milliseconds);
		if (view == null) {
			takeScreenshot("exception");
			throw new TasteTestingException("View with text \"" + text + "\" not found");
		} else {
			return view;
		}
	}

	public UiObject2 waitForTextOrNull(String text) {
		return testDevice.wait(Until.findObject(By.text(text)), config.getViewTimeout());
	}

	public void wait(int seconds) {
		// This is ugly but it works
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void waitForNextActivity() {
		testDevice.waitForWindowUpdate(null, config.getViewTimeout());
	}

	public String getString(int resourceId) {
		return context.getString(resourceId);
	}

	public String getViewId(int resourceId) {
		return context.getResources().getResourceEntryName(resourceId);
	}
}
