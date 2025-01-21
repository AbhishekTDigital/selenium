package PageObject;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage 
{
	WebDriver ldriver;
	
	//Constructor
	public LoginPage(WebDriver rDriver)
	{
		ldriver=rDriver;
		
		PageFactory.initElements(rDriver, this);
	}
	
	@FindBy(xpath="//input[@id='username']")
	WebElement username;
	
	@FindBy(xpath="//input[@id='password']")
	WebElement atntPass;
	
	@FindBy(id="Login")
	WebElement LoginBtn;
	
	@FindBy(xpath="//input[@title='Allow']")
	WebElement allowAccessBtn;
	
	@FindBy(xpath="//button[@class='MuiButtonBase-root MuiIconButton-root MuiIconButton-sizeSmall css-i29csa']")
	WebElement userIcon;
	
	@FindBy(xpath="//button[text()='Sign Out']")
	WebElement LogoutBtn;
	
	public void enterUserName(String userName)
	{
		username.sendKeys(userName);
	}
	
	public void enterATNTPassword(String Pass)
	{
		atntPass.sendKeys(Pass);
	}
	
	public void clickOnLoginButton()
	{
		LoginBtn.click();
	}
	
	public void clickOnAllowAccessButton()
	{
		allowAccessBtn.click();
	}
	
	public void clickOnUserIcon()
	{
		userIcon.click();
	}
	
	public void clickOnLogoutButton()
	{
		LogoutBtn.click();
	}
	
	
}
