package prj.tasostilsi.colab.testSuites.TS_UI_DEMO;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;
import prj.tasostilsi.colab.utils.BaseTest;
import prj.tasostilsi.colab.utils.common.BrowserControls;
import prj.tasostilsi.colab.utils.common.Wait;
import prj.tasostilsi.colab.utils.elements.Button;
import prj.tasostilsi.colab.utils.elements.Input;
import prj.tasostilsi.colab.utils.elements.Label;

import java.lang.reflect.Field;


public class TC_UI_DEMO_001 extends BaseTest {

	Logger LOGGER = Logger.getLogger(TC_UI_DEMO_001.class);

	@CacheLookup
	@FindBy(xpath = "//h5[contains(.,'Elements')]")
	private WebElement elementsHeaderElement;

	@CacheLookup
	@FindBy(xpath = "//li[contains(.,'Text Box')]")
	private WebElement textBoxListItemElement;

	@CacheLookup
	@FindBy(xpath = "//div[@class='main-header']")
	private WebElement mainHeaderElement;

	@CacheLookup
	@FindBy(id = "userName")
	private WebElement fullNameInputFieldElement;

	@CacheLookup
	@FindBy(id = "userEmail")
	private WebElement emailInputFieldElement;

	@CacheLookup
	@FindBy(id = "currentAddress")
	private WebElement currAddressInputFieldElement;

	@CacheLookup
	@FindBy(id = "permanentAddress")
	private WebElement permAddressInputFieldElement;

	@CacheLookup
	@FindBy(id = "submit")
	private WebElement submitButtonElement;

	private final String MAIN_HEADER_TEXT = "Elements";


	WebDriver driver;
	Button elementsCard;
	Button textBoxListItem;
	Label mainHeaderField;
	Input fullNameInputField;
	Input emailInputField;
	Input currAddressInputField;
	Input permAddressInputField;
	Button submitButton;

	@BeforeTest
	public void setup() {
		driver = getDriver();
		PageFactory.initElements(driver, this);
		elementsCard = new Button(elementsHeaderElement);
		textBoxListItem = new Button(textBoxListItemElement);
		mainHeaderField = new Label(mainHeaderElement);
		fullNameInputField = new Input(fullNameInputFieldElement);
		emailInputField = new Input(emailInputFieldElement);
		currAddressInputField = new Input(currAddressInputFieldElement);
		permAddressInputField = new Input(permAddressInputFieldElement);
		submitButton = new Button(submitButtonElement);
	}

	@Test(description = "Navigate to Elements page")
	public void navigateToElementsPage() {
		LOGGER.info("Navigate to Elements page");

		LOGGER.info("\tClicking Elements card");
		elementsCard.click();
		LOGGER.info("Verifying main header text");
		mainHeaderField.verifyText(MAIN_HEADER_TEXT);
	}

	@Test(description = "Navigate to TextBox page", dependsOnMethods = "navigateToElementsPage")
	public void navigateToTextBoxPage() {
		LOGGER.info("Verify textbox is enabled");
		Assert.assertTrue(textBoxListItem.isButtonEnabled(),"Button is not enabled");

		LOGGER.info("Clicking textbox");
		textBoxListItem.click();
	}

	@Test(description = "Verify input fields are enabled", dependsOnMethods = "navigateToTextBoxPage")
	public void fieldsAreEnabled() {
		LOGGER.info("Asserting input fields are enabled");
		Assert.assertTrue(fullNameInputField.isInputEnabled() &&
				emailInputField.isInputEnabled() &&
				currAddressInputField.isInputEnabled() &&
				permAddressInputField.isInputEnabled() &&
				submitButton.isButtonEnabled(),"Input fields are not enabled");
	}

	@Test(description = "Input data in input fields", dependsOnMethods = "fieldsAreEnabled")
	public void inputTextInTextBoxes() {

		LOGGER.info("Entering data in input fields");
		fullNameInputField.setText("Fullname");
		emailInputField.setText("Email@example.com");
		currAddressInputField.setText("Test Street 1234, PostCode 12345, Somewhere, Greece");
		permAddressInputField.setText("Test Street 5678, PostCode 56789, Somewhere, Greece");

		LOGGER.info("Clicking submit button");
		submitButton.click();
	}

	@Test(description = "Verify output", dependsOnMethods = "inputTextInTextBoxes")
	public void verifyTextEntered() {
		LOGGER.info("Verifying input fields");
		fullNameInputField.verifyText("Fullname");
		emailInputField.verifyText("Email@example.com");
		currAddressInputField.verifyText("Test Street 1234, PostCode 12345, Somewhere, Greece");
		permAddressInputField.verifyText("Test Street 5678, PostCode 56789, Somewhere, Greece");
	}

	@Test(description = "Clicking submit button", dependsOnMethods = "verifyTextEntered")
	public void clickSubmitAndVerifyOutput() {
		LOGGER.info("Clicking submit button");
		submitButton.click();
		LOGGER.info("Verifying output");
		WebElement outputDiv = driver.findElement(By.id("output"));
		Assert.assertTrue(outputDiv.isDisplayed());
		Assert.assertTrue(outputDiv.getText().contains("Fullname"));
		Assert.assertTrue(outputDiv.getText().contains("Email@example.com"));
		Assert.assertTrue(outputDiv.getText().contains("Test Street 1234, PostCode 12345, Somewhere, Greece"));
		Assert.assertTrue(outputDiv.getText().contains("Test Street 5678, PostCode 56789, Somewhere, Greece"));
	}

	@Test(description = "Changing email to something invalid", dependsOnMethods = "clickSubmitAndVerifyOutput")
	public void verifyErrorInEmail() {
		LOGGER.info("Changing email to something invalid");
		emailInputField.setText("test");
		LOGGER.info("Clicking submit button");
		submitButton.click();
		LOGGER.info("Verifying output is not appeared and the email input field is marked red");
		WebElement outputDiv = driver.findElement(By.id("output"));

		emailInputField.verifyInvalidInputEntered("rgb(255, 0, 0)");

	}
}
