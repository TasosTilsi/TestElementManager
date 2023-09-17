package prj.tasostilsi.colab.utils.elements;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import prj.tasostilsi.colab.utils.WebDriverFactory;
import prj.tasostilsi.colab.utils.common.Wait;

public class Input extends WebDriverFactory {
	
	private WebElement input;
	
	public Input(WebElement element) {
		this.input = element;
	}
	
	public void setText(String entryData) {
		if (entryData != null && !entryData.isEmpty()) {
			Wait.getInstance().forElement(input);
			input.clear();
			input.sendKeys(entryData);
		}
	}
	
	public void verifyText(String entryData) {
		if (entryData != null) {
			Wait.getInstance().forElement(input);
			Assert.assertEquals(input.getAttribute("value"), entryData,
					"Input area isn't equal to '" + entryData + "'.");
		}
	}
	
	public int getLength() {
		return input.getText().length();
	}
	
	public void clear() {
		input.sendKeys(Keys.CONTROL + "a");
		input.sendKeys(Keys.DELETE);
	}
	
	public boolean isInputEnabled() {
		return input.isEnabled();
	}
	
	public void verifyInvalidInputEntered(String color) {
		Wait.getInstance().sleep(0.3);
		Wait.getInstance().forElement(input);
		Assert.assertEquals(input.getCssValue("border-color"), color);
	}
}
