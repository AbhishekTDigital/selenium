package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AgentLeadFilterPage 
{
	WebDriver ldriver;

	//Constructor
	public AgentLeadFilterPage(WebDriver rDriver)
	{
		ldriver=rDriver;

		PageFactory.initElements(rDriver, this);
	}
	
	@FindBy(xpath="(//*[name()='svg'][@class='MuiSvgIcon-root MuiSvgIcon-fontSizeLarge css-6flbmm'])[2]")
	WebElement filterIcon;
	
	@FindBy(xpath="(//div[@id='lead-type-select'])[2]")
	WebElement leadTypeDrop;
	
	@FindBy(xpath="(//div[@id='disposition-select'])[2]")
	WebElement dispositiondrop;
	
	@FindBy(xpath="(//button[text()='Apply'])[2]")
	WebElement applyBtn;
	
	@FindBy(xpath="//p[@class='MuiTablePagination-displayedRows css-1chpzqh']")
	WebElement countLeadOnList;
	
	@FindBy(xpath="//span[contains(text(), 'out of')]")
	WebElement getLeadOnCards;
	
	public void clickOnFilterIcon()
	{
		filterIcon.click();
	}
	
	public void clickOnLeadTypeDropdown()
	{
		leadTypeDrop.click();
	}
	
	public void clickOnDispositionDropdown()
	{
		dispositiondrop.click();
	}
	
	public void clickOnApplyButton()
	{
		applyBtn.click();
	}
	
	public String getLeadsOnList()
	{
		return countLeadOnList.getText();
	}
	
	public String getLeadsOnCard()
	{
		return getLeadOnCards.getText();
	}
}
