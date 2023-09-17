package prj.tasostilsi.colab.utils.elements;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class Table {
	private WebElement table;
	
	public Table(WebElement table) {
		this.table = table;
	}
	
	public int getRowCount() {
		return table.findElements(By.tagName("tr")).size();
	}
	
	public int getColumnCount() {
		return table.findElements(By.tagName("th")).size();
	}
	
	public String getCellValue(int row, int column) {
		return table.findElement(By.xpath("//tr[" + row + "]/td[" + column + "]")).getText();
	}
	
	public List<String> getRowValues(int row) {
		List<String> rowValues = new ArrayList<>();
		for (int i = 1; i <= getColumnCount(); i++) {
			rowValues.add(getCellValue(row, i));
		}
		return rowValues;
	}
	
	public List<List<String>> getAllRowValues() {
		List<List<String>> allRowValues = new ArrayList<>();
		for (int i = 1; i <= getRowCount(); i++) {
			allRowValues.add(getRowValues(i));
		}
		return allRowValues;
	}
}
