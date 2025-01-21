package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DragAndDropAgentDataPage 
{
	WebDriver ldriver;
	
	//constructor
	public DragAndDropAgentDataPage(WebDriver rDriver)
	{
		ldriver= rDriver;
		
		PageFactory.initElements(rDriver ,this);
	}
	
	@FindBy(xpath="//i[@class='fa fa-sort']")
	WebElement sortIcon;
	
	public void clickOnSortIcon()
	{
		sortIcon.click();
	}

}
