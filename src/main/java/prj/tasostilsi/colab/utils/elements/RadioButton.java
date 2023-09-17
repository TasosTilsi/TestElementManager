package prj.tasostilsi.colab.utils.elements;

import org.openqa.selenium.WebElement;

public class RadioButton extends Button {
	private WebElement radioButton;
	
	public RadioButton(WebElement radioButton) {
		super(radioButton);
		this.radioButton = radioButton;
	}
	
	public void select() {
		if (!radioButton.isSelected()) {
			click();
		}
	}
	
	public boolean isSelected() {
		return radioButton.isSelected();
	}
}
