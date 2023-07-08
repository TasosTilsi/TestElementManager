package prj.tasostilsi.colab.utils.elements;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import prj.tasostilsi.colab.utils.common.Wait;

public class Input {

    private WebElement entryField;

    public Input(WebElement element){
        this.entryField = element;
    }

    public void setText(String entryData) {
        if (entryData != null && !entryData.isEmpty()) {
            Wait.getInstance().forElement(entryField);
            entryField.clear();
            entryField.sendKeys(entryData);
        }
    }

    public void verifyText(String entryData) {
        if (entryData != null) {
            Wait.getInstance().forElement(entryField);
            Assert.assertEquals(entryField.getAttribute("value"), entryData,
                    "Input area isn't equal to '" + entryData + "'.");
        }
    }

    public int getLength() {
        return entryField.getText().length();
    }

    public void clear() {
        entryField.sendKeys(Keys.CONTROL + "a");
        entryField.sendKeys(Keys.DELETE);
    }

    public boolean isInputEnabled() {
        return entryField.isEnabled();
    }
}
