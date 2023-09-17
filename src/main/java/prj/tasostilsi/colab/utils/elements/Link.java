package prj.tasostilsi.colab.utils.elements;

import org.openqa.selenium.WebElement;

public class Link extends Button {
	
	private WebElement link;
	
	public Link(WebElement link) {
		super(link);
		this.link = link;
	}
	
	public String getHref() {
		return link.getAttribute("href");
	}
}
