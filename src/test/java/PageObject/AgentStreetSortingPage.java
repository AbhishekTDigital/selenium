package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AgentStreetSortingPage 
{
	WebDriver ldriver;

	//Constructor
	public AgentStreetSortingPage(WebDriver rDriver)
	{
		ldriver=rDriver;

		PageFactory.initElements(rDriver, this);
	}
	
	@FindBy(xpath="//span[text()='Street Name']")
	WebElement streetName;
	
	@FindBy(xpath="(//*[name()='svg'][@class='MuiSvgIcon-root MuiSvgIcon-fontSizeLarge css-6flbmm'])[2]")
	WebElement arrowDrop;
	
	@FindBy(xpath="(//h6[@class='MuiTypography-root MuiTypography-h6 MuiTypography-noWrap css-3wafqk'])[15]")
	WebElement alocNumber;
	
	public void clickOnStreetNameForSorting()
	{
		streetName.click();
	}
	
	public void clickOnArrowDropdownButton()
	{
		arrowDrop.click();
	}
	
	public String getALOCNumber()
	{
		return alocNumber.getText();
	}

}
