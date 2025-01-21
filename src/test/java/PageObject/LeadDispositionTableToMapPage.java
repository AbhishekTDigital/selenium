package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LeadDispositionTableToMapPage 
{
	WebDriver ldriver;

	//Constructor
	public LeadDispositionTableToMapPage(WebDriver rDriver)
	{
		ldriver=rDriver;

		PageFactory.initElements(rDriver, this);
	}
	
	@FindBy(xpath="(//*[name()='svg'][contains(@class,'MuiSvgIcon-root MuiSvgIcon-fontSizeLarge css-6flbmm')])[1]")
	WebElement detailDropdown;
	
	@FindBy(xpath="//div[@id='primary-disposition-nds']")
	WebElement primarydrop;
	
	@FindBy(xpath="//div[@id='secondary-disposition-nds']")
	WebElement secondarydrop;
	
	@FindBy(xpath="//button[text()='Map']")
	WebElement mapBtn;
	
	@FindBy(xpath="")
	WebElement MapAudId;
	
	public void clickOnArrowDropdown()
	{
		detailDropdown.click();
	}
	
	public void clickonPrimaryLeadDropdown()
	{
		primarydrop.click();
	}
	
	public void clickOnSecondaryLeadDropdown()
	{
		secondarydrop.click();
	}
	
	public void clickOnMapButton()
	{
		mapBtn.click();
	}
	
	public void getAudienceId()
	{
		
	}


}
