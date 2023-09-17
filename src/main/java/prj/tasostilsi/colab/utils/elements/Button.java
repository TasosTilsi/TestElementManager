package prj.tasostilsi.colab.utils.elements;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.log4testng.Logger;
import prj.tasostilsi.colab.utils.WebDriverFactory;
import prj.tasostilsi.colab.utils.common.Wait;


public class Button extends WebDriverFactory {
	
	Logger LOGGER = Logger.getLogger(Button.class);
	
	private WebElement button;
	
	public Button(WebElement button) {
		this.button = button;
	}
	
	public void click() {
		if (button != null) {
			Wait.getInstance().forElement(button);
			button.click();
		} else {
			LOGGER.error("ERROR Element ID is NOT clickable.");
		}
		Wait.getInstance().forPageToLoad();
	}
	
	public void clickAction() {
		if (button != null) {
			Wait.getInstance().forElement(button);
			Actions action = new Actions(getDriver());
			action
					.click(button)
					.build()
					.perform();
		} else {
			LOGGER.error("ERROR Element ID is NOT clickable.");
		}
		
		try {
			Wait.getInstance().forPageToLoad();
		} catch (UnhandledAlertException f) {
			try {
			} catch (NoAlertPresentException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void clickJS() {
		if (button != null) {
			Wait.getInstance().forElement(button);
			((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", button);
			
		} else {
			LOGGER.error("ERROR Element ID is NOT clickable.");
		}
		Wait.getInstance().forPageToLoad();
	}
	
	public void doubleClickAction() {
		if (button != null) {
			Wait.getInstance().forElement(button);
			Actions action = new Actions(getDriver());
			action
					.moveToElement(button)
					.doubleClick()
					.build()
					.perform();
		} else {
			LOGGER.error("ERROR Element ID is NOT clickable.");
		}
		Wait.getInstance().forPageToLoad();
	}
	
	public boolean isEnabled() {
		return button.isEnabled();
	}
	
	public String getText() {
		return button.getText();
	}
}
