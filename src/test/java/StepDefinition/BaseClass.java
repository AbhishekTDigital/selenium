package StepDefinition;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import PageObject.AddAgentPage;
import PageObject.AgentLeadFilterPage;
import PageObject.AgentStreetSortingPage;
import PageObject.ChangeDispositionMapToTablePage;
import PageObject.CreateTerritoriesPage;
import PageObject.DragAndDropAgentDataPage;
import PageObject.GetZipCodesPage;
import PageObject.LeadDispositionTableToMapPage;
import PageObject.LoginPage;
import PageObject.SelectZipCodePage;
import Utilities.ReadConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.*;
import org.junit.Assert;

//Parent Class
public class BaseClass 
{
	public WebDriverWait wait;
	
	///Page class variable instantiation
	public static WebDriver driver;
	public LoginPage loginPg;
	public AddAgentPage addAgentPg;
	public CreateTerritoriesPage createTerritoriesPg;
	public ChangeDispositionMapToTablePage chngDisposMapToTabPg;
	public LeadDispositionTableToMapPage leadDisposTabToMapPg;
	public GetZipCodesPage getZipCodesPg;
	public SelectZipCodePage selectZipCodePg;
	public AgentStreetSortingPage agentStreetSortingPg;
	public DragAndDropAgentDataPage dragAndDropAgentDataPg;
	public AgentLeadFilterPage agentLeadFilterPg;

	public static Logger log;
	
	public ReadConfig readConfig;
	
	//dynamic WebDriverWait ///
		public WebElement dynamicWebDriverWait(By locator, int timeout)
		{
			try {
			wait=new WebDriverWait(driver,Duration.ofSeconds(timeout));
			return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			}catch(Exception e)
			{
				System.out.println("Error inspecting in dynamic wait:"+e.getMessage());
				return null;
			}
		}
		
		 public WebElement inspectLastElement(WebDriver driver, By locator) {
		        try {
		            // Find all matching elements
		            List<WebElement> elements = driver.findElements(locator);

		            // Check if the list is not empty
		            if (!elements.isEmpty()) {
		                WebElement lastElement = elements.get(elements.size() - 1); // Get the last element
		                System.out.println("Last Element Text: " + lastElement.getText());
		                System.out.println("Last Element Attribute (example): " + lastElement.getAttribute("attribute_name"));
		                return lastElement;
		            } else {
		                System.out.println("No elements found.");
		                return null;
		            }
		        } catch (Exception e) {
		            System.out.println("Error inspecting the last element: " + e.getMessage());
		            return null;
		        }
		    }
		 //// Read List of Dropdown /////
		 public boolean selectOptionByText(By locator, String text) {
		        try {
		            // Find all elements matching the locator
		            List<WebElement> elementList = driver.findElements(locator);

		            // Iterate through the options and select the desired one
		            for (WebElement option : elementList) {
		                System.out.println("List of dropdown: " + option.getText());
		                if (option.getText().equals(text)) {
		                    option.click(); // Click the desired option
		                    return true; // Return true if the option is found and clicked
		                }
		            }
		            System.out.println("Text '" + text + "' not found in the list.");
		            return false; // Return false if the option is not found
		        } catch (Exception e) {
		            System.out.println("Error while selecting option: " + e.getMessage());
		            return false; // Handle exceptions gracefully
		        }
		        
		    }
		 /// Switch to new Window
		 public boolean switchToNewWindow(String currentWindow) {
		        try {
		            // Get all window handles
		            Set<String> allWindows = driver.getWindowHandles();

		            // Iterate through the window handles
		            for (String window : allWindows) {
		                if (!window.equals(currentWindow)) {
		                    driver.switchTo().window(window); // Switch to the new window
		                    System.out.println("Switched to new window with handle: " + window.toString());
		                    return true; // Successful switch
		                }
		            }

		            System.out.println("No new window found.");
		            return false; // No new window found
		        } catch (Exception e) {
		            System.out.println("Error while switching to new window: " + e.getMessage());
		            return false; // Handle exception
		        }
		 
		 }
		 
		 public void staticWait(int timeout)
		 {
			 try {
				Thread.sleep(timeout);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		 
		 public boolean selectRadioButtonByValue(By locator, String valueToSelect) {
		        try {
		            // Find all radio buttons matching the locator
		            List<WebElement> radioButtons = driver.findElements(locator);
		            int Size=radioButtons.size();
		            System.out.println("All radio Buttons:"+Size);
		            

		            for (WebElement radioButton : radioButtons) {
		            	System.out.println("radio Buttons Name:"+radioButton.getText());
		                // Check if the radio button has the desired value
		                if (radioButton.getText().equals(valueToSelect)) {
		                    if (!radioButton.isSelected()) {
		                        radioButton.click(); // Select the radio button
		                        System.out.println("Radio button with value '" + valueToSelect + "' is selected.");
		                    }
		                    return true; // Return true if successfully selected
		                }
		            }
		            System.out.println("Radio button with value '" + valueToSelect + "' not found.");
		            return false; // Return false if the value is not found
		        } catch (Exception e) {
		            System.out.println("Error selecting radio button: " + e.getMessage());
		            return false; // Handle exception
		        }
		    }
		 
		 public boolean switchToWindow(String fromWindow, String toWindow) {
			    try {
			        // Get all window handles
			        Set<String> allWindows = driver.getWindowHandles();
			        
			        // Ensure both windows exist in the set of handles
			        if (!allWindows.contains(fromWindow) || !allWindows.contains(toWindow)) {
			            System.out.println("One or both of the specified windows are not found.");
			            return false;
			        }

			        // Switch to the 'from' window if needed (optional, usually we're already on it)
			        driver.switchTo().window(fromWindow);
			        System.out.println("Switched to window with handle: " + fromWindow);

			        // Switch to the target 'to' window
			        driver.switchTo().window(toWindow);
			        System.out.println("Switched to window with handle: " + toWindow);
			        
			        return true; // Successfully switched to the target window
			    } catch (Exception e) {
			        System.out.println("Error while switching windows: " + e.getMessage());
			        return false; // Handle exception
			    }
			}
		 
		 ////check element visible or not
		 public void elementVisibility(By Locator, boolean isVisible) {
		        JavascriptExecutor js = (JavascriptExecutor) driver;
		        if (isVisible) {
		            // Show the button
		            js.executeScript("arguments[0].style.display = 'block';", driver.findElement(Locator));
		        } else {
		            // Hide the button
		            js.executeScript("arguments[0].style.display = 'none';", driver.findElement(Locator));
		        }
		    }
		 
		 public boolean isElementVisible(By locator) {
		        try {
		            WebElement element = driver.findElement(locator);
		            return element.isDisplayed();  // Returns true if element is visible, otherwise false
		        } catch (Exception e) {
		            // If the element is not found, return false
		            return false;
		        }
		    }
		 
		 public void selectCheckboxByVisibleText(String visibleText, By checkboxLocator) {
		        try {
		            // Find all checkboxes within the group
		            for (WebElement checkbox : driver.findElements(checkboxLocator)) {
		            	System.out.println("List of checkbox:"+checkbox.toString());
		                // Check if the visible text matches
		                if (checkbox.getText().equals(visibleText)) {
//		                    if (!checkbox.isSelected()) { // Ensure the checkbox is not already selected
		                        checkbox.click();
		                    }
		                    break;
		                }
//		            }
		        } catch (Exception e) {
		            System.out.println("Error selecting checkbox: " + e.getMessage());
		        }
		    }
		 
		 /////////check box//////////
		 public void clickCheckboxByText(String checkboxText) {
		        // Locate all checkboxes
		        List<WebElement> checkboxes = driver.findElements(By.xpath("//input[@type='checkbox']"));

		        for (WebElement checkbox : checkboxes) {
		            // Find the parent element of the checkbox
		            WebElement parent = checkbox.findElement(By.xpath("./..")); // Navigate to the parent <span>

		            // Get the visible text associated with the checkbox
		            String text = parent.getText().trim();

		            // Print the checkbox value and associated text for debugging
		            System.out.println("Checkbox value: " + checkbox.getAttribute("value") + ", Text: " + text);

		            // Click the checkbox if the text matches
		            if (text.equals(checkboxText)) {
		                if (!checkbox.isSelected()) {
		                    checkbox.click();
		                }
		                return; // Exit the method once the desired checkbox is clicked
		            }
		        }

		        // Throw an exception if no matching checkbox is found
		        throw new RuntimeException("Checkbox with text '" + checkboxText + "' not found.");
		    }
		 
		 public int getTableRowCount(String tableClass) {
		        // Locate the table body
		        WebElement tableBody = driver.findElement(By.xpath("//table[@class='" + tableClass + "']/tbody"));
		        
		        // Find all rows inside the table body
		        List<WebElement> rows = tableBody.findElements(By.tagName("tr"));
		        
		        // Return the row count
		        return rows.size();
		    }

		    
		    public void verifyTableRowCount(String tableClass, int expectedRows) {
		        int actualRows = getTableRowCount(tableClass);
		        if (actualRows == expectedRows) {
		            System.out.println("Row count verification passed. Row count: " + actualRows);
		        } else {
		            throw new RuntimeException("Row count verification failed. Expected: " + expectedRows + ", but got: " + actualRows);
		        }
		    }
		    
		    public int getNumberInsideBrackets(By elementLocator) {
		        // Locate the element
		        WebElement element = driver.findElement(elementLocator);

		        // Get the text of the element
		        String text = element.getText();

		        // Define the regex pattern to match numbers inside square brackets
		        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
		        Matcher matcher = pattern.matcher(text);

		        // Extract and return the number if found
		        if (matcher.find()) {
		            return Integer.parseInt(matcher.group(1));
		        } else {
		            System.out.println("No number found inside brackets in text: " + text);
		            return -1; // Return -1 if no number is found
		        }
		    }
		    
		    // Method to extract the number after 'of' keyword
		    public int getNumberAfterOf(By locator) {
		        try {
		            WebElement paginationElement = driver.findElement(locator);
		            String paginationText = paginationElement.getText();
		            String[] parts = paginationText.split("of");
		            if (parts.length == 2) {
		                return Integer.parseInt(parts[1].trim());  // Return the integer after 'of'
		            } else {
		                System.out.println("Pagination text format incorrect.");
		                return -1; // Return -1 or throw an exception based on your error handling policy
		            }
		        } catch (Exception e) {
		            System.out.println("An error occurred while extracting number after 'of': " + e.getMessage());
		            return -1;  // Return -1 or throw an exception based on your error handling policy
		        }
		    }
		
		/////////Zoom in /////////
		    // Locate the map element
/*		    public WebElement zoomInOnMap(By elementLocator , int number)
		    {
	        WebElement mapElement = driver.findElement(By.cssSelector("CSS_SELECTOR_FOR_MAP"));

	        // Perform mouse scroll to zoom in
	        Actions actions = new Actions(driver);
	        actions.moveToElement(mapElement)
	               .clickAndHold() // Optional: for some maps
	               .sendKeys("+") // Send zoom-in keys or emulate wheel action
	               .perform();
	        
	        // Locate the "+" zoom in button
	        WebElement zoomInButton = driver.findElement(By.cssSelector("button[aria-label='Zoom in']"));

	        // Number of times to zoom in
	        int zoomTimes = 5;

	        // Click the "+" button repeatedly
	        for (int i = 0; i < zoomTimes; i++) {
	            zoomInButton.click();
	            try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Wait 1 second between clicks to allow the map to render
	        }
	        
	        
		    }  */
		    
		    public void zoomInMapUsingKeyboard(int times)  {
		        // Create an Actions instance
		        Actions actions = new Actions(driver);

		        // Simulate pressing the '+' key the given number of times
		        for (int i = 0; i < times; i++) {
		            actions.sendKeys("+").perform();
		            try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // Pause to allow the map to render after each zoom
		        }
		    }
		    
		    

		    
		    public void zoomOutMapUsingKeyboard(int times)  {
		        // Create an Actions instance
		        Actions actions = new Actions(driver);

		        // Simulate pressing the '+' key the given number of times
		        for (int i = 0; i < times; i++) {
		            actions.sendKeys("-").perform();
		            try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // Pause to allow the map to render after each zoom
		        }
		    }
		    
		    public void zoomInMapUsingKeyboard(By mapSelector, int times)  {
		        // Locate the map element
		        WebElement mapElement = driver.findElement(mapSelector);

		        // Click on the map to ensure focus
		        mapElement.click();

		        // Create an Actions instance
		        Actions actions = new Actions(driver);

		        // Perform zoom-in using the '+' key
		        for (int i = 0; i < times; i++) {
		            actions.sendKeys("+").perform();
		            try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} // Pause to allow the map to render after each zoom
		        }
		    }
		    
		    public List<List<String>> getTableData(By tableXPath) {
		        // Locate the table
		        WebElement table = driver.findElement(tableXPath);

		        // Get all rows of the table
		        List<WebElement> rows = table.findElements(By.tagName("tr"));
		        List<List<String>> tableData = new ArrayList<>();

		        // Iterate through each row
		        for (WebElement row : rows) {
		            // Get all cells (columns) of the row
		            List<WebElement> cells = row.findElements(By.tagName("td"));
		            List<String> rowData = new ArrayList<>();
		            
		            for (WebElement cell : cells) {
		                // Add the cell text to rowData
		                rowData.add(cell.getText().trim());
		            }
		            // Add the rowData to tableData
		            tableData.add(rowData);
		        }

		        return tableData;
		    }
	
		 // Method to select a dropdown option by visible text
		    public void selectDropdownByVisibleText(By dropdownXPath, String visibleText) {
		        WebElement dropdown = driver.findElement(dropdownXPath);
		        Select select = new Select(dropdown);
		        select.selectByVisibleText(visibleText);
		    }
		    
		    public void verifyDropdownOptions(String dropdownXPath, List<String> expectedOptions) {
		        // Locate the dropdown element
		        WebElement dropdown = driver.findElement(By.xpath(dropdownXPath));

		        // Create a Select object for interacting with the dropdown
		        Select select = new Select(dropdown);

		        // Get all the options from the dropdown
		        List<WebElement> options = select.getOptions();

		        // Verify the size of options
		        Assert.assertEquals("The number of options in the dropdown does not match the expected", expectedOptions.size(), options.size());

		        // Compare each option's visible text with the expected options
		        for (int i = 0; i < options.size(); i++) {
		            String actualOption = options.get(i).getText().trim();
		            String expectedOption = expectedOptions.get(i).trim();
		           Assert.assertEquals("Dropdown option mismatch at index " + i, expectedOption, actualOption);
		        }
		    }
		    
		    public boolean validateDropdownOptions(String dropdownLocator, List<String> expectedOptions) {
		        try {
		            WebElement dropdownElement = driver.findElement(By.xpath(dropdownLocator));
		            Select dropdown = new Select(dropdownElement);

		            // Get all options in the dropdown
		            List<WebElement> actualOptions = dropdown.getOptions();

		            // Loop through each expected option and check if it is present in the dropdown
		            for (String expectedOption : expectedOptions) {
		                boolean isOptionFound = false;
		                for (WebElement option : actualOptions) {
		                    if (option.getText().equals(expectedOption)) {
		                        isOptionFound = true;
		                        break;
		                    }
		                }
		                if (!isOptionFound) {
		                    return false; // If any expected option is missing, return false
		                }
		            }
		            return true; // All expected options were found
		        } catch (Exception e) {
		            e.printStackTrace();
		            return false; // In case of any exception, return false
		        }
		    }
		    
		 // Method to validate the selected value in a dropdown
		    public boolean validateSelectedDropdownValue(By dropdownLocator, String expectedSelectedValue) {
		        try {
		            WebElement dropdownElement = driver.findElement(dropdownLocator);
		            Select dropdown = new Select(dropdownElement);

		            // Get the currently selected option in the dropdown
		            WebElement selectedOption = dropdown.getFirstSelectedOption();

		            // Compare the selected value with the expected value
		            if (selectedOption.getText().equals(expectedSelectedValue)) {
		                return true; // If the selected value matches the expected value
		            } else {
		                return false; // If the selected value does not match the expected value
		            }
		        } catch (Exception e) {
		            e.printStackTrace();
		            return false; // In case of any exception, return false
		        }
		    }
		    
		    public void checkdropdownByText(By dis ,String dropdownText) {
		        // Locate all checkboxes
		        List<WebElement> dropdown = driver.findElements(dis);

		        for (WebElement drop : dropdown) {
		            // Find the parent element of the checkbox
		            WebElement parent = drop.findElement(By.xpath("./..")); // Navigate to the parent <span>

		            // Get the visible text associated with the checkbox
		            String text = parent.getText().trim();

		            // Print the checkbox value and associated text for debugging
		            System.out.println("dropdown value: " + drop.getAttribute("value") + ", Text: " + text);

		            // Click the checkbox if the text matches
		            if (text.equals(dropdownText)) {
		                System.out.println("validate Success...");
		                return; // Exit the method once the desired checkbox is clicked
		            }else {
		            	System.out.println("Not validate....");
		            }
		        }
		    }
		    
		    public boolean isTextVisibleInDropdown(String dropdownXpath, String textToFind) {
		        try {
		            WebElement dropdown = driver.findElement(By.xpath(dropdownXpath));
		            Select select = new Select(dropdown);
		            List<WebElement> options = select.getOptions();
		            for (WebElement option : options) {
		                if (option.getText().equals(textToFind) && option.isDisplayed()) {
		                    return true;
		                }
		            }
		        } catch (Exception e) {
		            System.out.println("Error finding visible text in dropdown: " + e.getMessage());
		        }
		        return false;
		    }
		    
		    public boolean isTextPresentOnScreen(String textToFind) {
		        try {
		            WebElement textElement = driver.findElement(By.xpath("//option[text(),'" + textToFind + "']"));
		            return textElement.isDisplayed();
		        } catch (Exception e) {
		            System.out.println("Text not found on screen: " + e.getMessage());
		            return false;
		        }
		    }
		    
		    public void validateCheckboxByName(List<String> expectedNames) {//input[@type='checkbox']/parent::span
		        // Locate all checkboxes
		        List<WebElement> checkboxes = driver.findElements(By.xpath("//input[contains(@value,'01')]"));

		        // Iterate over all checkboxes
		        for (WebElement checkbox : checkboxes) {
		            // Get the name attribute of the checkbox
		            String checkboxName = checkbox.getAttribute("value");

		            // Print the name of each checkbox for debugging
		            System.out.println("Checkbox name: " + checkboxName);

		            // Check if the checkbox name matches any of the expected names
		            if (expectedNames.contains(checkboxName)) {
		                // Validate if the checkbox is displayed
		                if (checkbox.isDisplayed()) {
		                    System.out.println("Checkbox with name '" + checkboxName + "' is visible.");
		                } else {
		                    System.out.println("Checkbox with name '" + checkboxName + "' is not visible.");
		                }
		                
		             

		                // Validate if the checkbox is selected
/*		                if (checkbox.isSelected()) {
		                    System.out.println("Checkbox with name '" + checkboxName + "' is selected.");
		                } else {
		                    System.out.println("Checkbox with name '" + checkboxName + "' is not selected.");
		                }  */

		                // Break the loop once the desired checkbox is found and validated
		                break;
		            }
		        }
		    }
	
}
