package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AddAgentPage 
{
	WebDriver ldriver;

	//Constructor
	public AddAgentPage(WebDriver rDriver)
	{
		ldriver=rDriver;

		PageFactory.initElements(rDriver, this);
	}
	
	@FindBy(xpath="//i[@class='fa fa-user-plus nav-icon']")
	WebElement addAgentBtn;
	
	@FindBy(id="firstName")
	WebElement firstName;
	
	@FindBy(id="lastName")
	WebElement lastName;
	
	@FindBy(id="contact")
	WebElement phoneNumber;
	
	@FindBy(id="email")
	WebElement email;
	
	@FindBy(className="create-button")
	WebElement createBtn;
	
	@FindBy(xpath="//div[text()='Data submitted for adding Agent']")
	WebElement verifymsg;
	
	
	public void clickOnAddAgentButton()
	{
		addAgentBtn.click();
	}
	
	public void enterFirstName(String fName)
	{
		firstName.sendKeys(fName);
	}
	
	public void enterLastName(String lName)
	{
		lastName.sendKeys(lName);
	}
	
	public void enterPhoneNumber(String pNumber)
	{
		phoneNumber.sendKeys(pNumber);
	}
	
	public void enterEmail(String emailId)
	{
		email.sendKeys(emailId);
	}
	
	public void clickOnCreateButton()
	{
		createBtn.click();
	}
	
	public String clickOnVerifyMsg()
	{
		return verifymsg.getText();
	}

}
