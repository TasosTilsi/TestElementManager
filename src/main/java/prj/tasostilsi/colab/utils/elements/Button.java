package prj.tasostilsi.colab.utils.elements;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import prj.tasostilsi.colab.utils.WebDriverFactory;
import prj.tasostilsi.colab.utils.common.Wait;


public class Button extends WebDriverFactory {

    protected WebElement element;

    public Button(WebElement element){
        this.element = element;
    }

    public void click() {
        if (element != null) {
            Wait.getInstance().forElement(element);
            element.click();
        } else {
            System.out.println("ERROR Element ID is NOT clickable.");
        }
        Wait.getInstance().forPageToLoad();
    }

    public void clickAction() {
        if (element != null) {
            Wait.getInstance().forElement(element);
            Actions action = new Actions(getDriver());
            action
                    .click(element)
                    .perform();
        } else {
            System.out.println("ERROR Element ID is NOT clickable.");
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
        if (element != null) {
            Wait.getInstance().forElement(element);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);

        } else {
            System.out.println("ERROR Element ID is NOT clickable.");
        }
        Wait.getInstance().forPageToLoad();
    }

    public void doubleClickAction() {
        if (element != null) {
            Wait.getInstance().forElement(element);
            Actions action = new Actions(getDriver());
            action
                    .moveToElement(element)
                    .doubleClick()
                    .build()
                    .perform();
        } else {
            System.out.println("ERROR Element ID is NOT clickable.");
        }
        Wait.getInstance().forPageToLoad();
    }

    public boolean isButtonEnabled() {
        return element.isEnabled();
    }
}
