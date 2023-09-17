package prj.tasostilsi.colab.utils.elements;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import prj.tasostilsi.colab.utils.WebDriverFactory;
import prj.tasostilsi.colab.utils.common.Wait;

public class Label extends WebDriverFactory {
	
	private WebElement label;
	
	public Label(WebElement label) {
		this.label = label;
	}
	
	public String getText() {
		Wait.getInstance().forElement(label);
		return label.getText();
	}
	
	public void verifyText(String entryData) {
		if (entryData != null) {
			Wait.getInstance().forElement(label);
			Assert.assertEquals(label.getText(), entryData,
					"Label area isn't equal to '" + entryData + "'.");
		}
	}
	
	public int getLength() {
		return label.getText().length();
	}
	
	public boolean isDisplayed() {
		return label.isDisplayed();
	}
}
