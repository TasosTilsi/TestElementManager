package prj.tasostilsi.colab.utils.elements;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import prj.tasostilsi.colab.utils.WebDriverFactory;
import prj.tasostilsi.colab.utils.common.Wait;

public class Label extends WebDriverFactory {

    private WebElement element;

    public Label(WebElement element){
        this.element = element;
    }

    public String getText() {
        Wait.getInstance().forElement(element);
        return element.getText();
    }

    public void verifyText(String entryData) {
        if (entryData != null) {
            Wait.getInstance().forElement(element);
            Assert.assertEquals(element.getText(), entryData,
                    "Label area isn't equal to '" + entryData + "'.");
        }
    }

    public int getLength() {
        return element.getText().length();
    }

    public boolean isDisplayed() {
        return element.isDisplayed();
    }
}
