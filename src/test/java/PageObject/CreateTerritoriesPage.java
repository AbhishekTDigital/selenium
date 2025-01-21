package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CreateTerritoriesPage 
{
	WebDriver ldriver;

	//Constructor
	public CreateTerritoriesPage(WebDriver rDriver)
	{
		ldriver=rDriver;

		PageFactory.initElements(rDriver, this);
	}
	
	@FindBy(xpath="//i[@class='far fa-map nav-icon']")
	WebElement zipCodes;
	
	@FindBy(xpath="//button[text()='Select Zip Code(s)']")
	WebElement zipBtn;
	
	@FindBy(xpath="//a[@title='Draw a polygon']")
	WebElement polygonIcon;
	
	@FindBy(xpath="//div[text()='Assign']")
	WebElement assignBtn;
	
	@FindBy(xpath="//input[@placeholder='Enter territory name']")
	WebElement territoryName;
	
	@FindBy(id="datepicker")
	WebElement completionDate;
	
	@FindBy(xpath="//input[@class='agent-select selection']")
	WebElement assignAgent;
	
	@FindBy(id="save-poly-btn")
	WebElement saveBtn;
	
	@FindBy(xpath="(//div[text()='Territory Saved Successfully!'])[2]")
	WebElement crtTerriMsg;
	
	@FindBy(xpath="(//a[@class='nav-link active'])[2]")
	WebElement assignment;
	
	@FindBy(xpath="//button[text()='List']")
	WebElement mapBtn;
	
	public void clickOnZipCodes()
	{
		zipCodes.click();
	}
	
	public void clickOnZipCodeButton()
	{
		zipBtn.click();
	}
	
	public void clickOnPolygonIcon()
	{
		polygonIcon.click();
	}
	
	public void clickOnAssignButton()
	{
		assignBtn.click();
	}
	
	public void enterTerritoryName(String territName)
	{
		territoryName.sendKeys(territName);
	}
	
	public void enterCompletionDate(String compDate)
	{
		completionDate.sendKeys(compDate);
	}
	
	public void enterOnAssignAgentDropdown(String agentdropName)
	{
		assignAgent.sendKeys(agentdropName);
	}
	
	public void clickOnSaveButton()
	{
		saveBtn.click();
	}
	
	public String verifyCreateTerritoryMsg()
	{
		return crtTerriMsg.getText();
	}
	
	public void clickOnAssignment()
	{
		assignment.click();
	}
	
	public void clickOnMapButton()
	{
		mapBtn.click();
	}

}
