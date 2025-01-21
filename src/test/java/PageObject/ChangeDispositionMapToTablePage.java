package PageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ChangeDispositionMapToTablePage 
{
	WebDriver ldriver;

	//Constructor
	public ChangeDispositionMapToTablePage(WebDriver rDriver)
	{
		ldriver=rDriver;

		PageFactory.initElements(rDriver, this);
	}
		
	
	
	@FindBy(xpath="(//td[@class='text-align-left'])[2]")
	WebElement audienceId;
	
	@FindBy(xpath="(//select)[1]")
	WebElement leadDisposition;
	
	@FindBy(xpath="//input[@type='text']")
	WebElement searchBox;
	
	@FindBy(xpath="(//div[@id='primary-disposition-nds'])[1]")
	WebElement leadDispose1;
	
	public String getAudienceId()
	{
		return audienceId.getText();
	}
	
	public void clickOnLeadDispositionCheckbox()
	{
		leadDisposition.click();
	}
	
	public void entersAudienceIdInSearchbox(String audId)
	{
		searchBox.sendKeys(audId);
	}
	
	public String getLeadDisposition1()
	{
		return leadDispose1.getText();
	}

}
